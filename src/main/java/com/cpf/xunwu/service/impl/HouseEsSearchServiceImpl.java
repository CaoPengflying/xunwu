package com.cpf.xunwu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cpf.xunwu.base.BusinessException;
import com.cpf.xunwu.entity.House;
import com.cpf.xunwu.entity.HouseDetail;
import com.cpf.xunwu.entity.HouseTag;
import com.cpf.xunwu.search.HouseIndexTemplate;
import com.cpf.xunwu.service.HouseDetailService;
import com.cpf.xunwu.service.HouseEsSearchService;
import com.cpf.xunwu.service.HouseService;
import com.cpf.xunwu.service.HouseTagService;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author caopengflying
 * @time 2020/3/1
 */
@Service
public class HouseEsSearchServiceImpl implements HouseEsSearchService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(HouseEsSearchServiceImpl.class);
    private static final String HOUSE_INDEX = "xunwu_index";
    @Resource
    private HouseService houseService;
    @Resource
    private HouseDetailService houseDetailService;
    @Resource
    private ModelMapper modelMapper;
    @Resource
    private HouseTagService houseTagService;
    @Resource
    private RestHighLevelClient restClient;

    @Override
    public void index(Long houseId) {
        House byId = houseService.getById(houseId);
        if (null == byId) {
            return;
        }
        HouseIndexTemplate houseIndexTemplate = new HouseIndexTemplate();
        modelMapper.map(byId, houseIndexTemplate);
        QueryWrapper<HouseDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HouseDetail::getHouseId, houseId);
        HouseDetail one = houseDetailService.getOne(queryWrapper);
        if (null != one){
            modelMapper.map(one, houseIndexTemplate);
        }
        QueryWrapper<HouseTag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.lambda().eq(HouseTag::getHouseId, houseId);
        List<HouseTag> list = houseTagService.list(tagQueryWrapper);
        if (CollectionUtils.isNotEmpty(list)){
            List<String> tagNameList = list.parallelStream().map(HouseTag::getName).collect(Collectors.toList());
            houseIndexTemplate.setTags(tagNameList);
        }
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestionBuilder termSuggestionBuilder =
                SuggestBuilders.termSuggestion("houseId").text(houseIndexTemplate.getHouseId().toString());
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("house_index", termSuggestionBuilder);
        searchSourceBuilder.suggest(suggestBuilder);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse response = restClient.search(searchRequest, RequestOptions.DEFAULT);
            if (response.getHits().getTotalHits().value == 0){
                create(houseIndexTemplate);
            }else if (response.getHits().getTotalHits().value == 1){
                update(houseIndexTemplate);
            }else {
                deleteAndCreate(houseIndexTemplate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        create(houseIndexTemplate);
//        update(houseIndexTemplate);
//        delete(houseIndexTemplate);
    }

    /**
     * 删除并新增
     * @param houseIndexTemplate
     * @return
     */
    public void deleteAndCreate(HouseIndexTemplate houseIndexTemplate) {
        DeleteRequest request = new DeleteRequest(HOUSE_INDEX, houseIndexTemplate.getHouseId().toString());
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(HOUSE_INDEX);
        deleteByQueryRequest.setQuery(new TermQueryBuilder("houseId", houseIndexTemplate.getHouseId().toString()) {
        });
        try {
            restClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
            create(houseIndexTemplate);
        } catch (IOException e) {
            logger.error("删除文档失败", e);
        }
    }

    public Boolean update(HouseIndexTemplate houseIndexTemplate) {
        UpdateRequest request = new UpdateRequest(HOUSE_INDEX, houseIndexTemplate.getHouseId().toString());
        request.doc(JSONObject.toJSONString(houseIndexTemplate), XContentType.JSON);
        try {
            restClient.update(request, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            logger.error("修改文档失败", e);
            return false;
        }
    }

    public HouseIndexTemplate get(String id) {
        GetRequest getRequest = new GetRequest(HOUSE_INDEX, id);
        try {
            GetResponse getResponse = restClient.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                String sourceAsString = getResponse.getSourceAsString();
                return JSONObject.parseObject(sourceAsString, HouseIndexTemplate.class);
            } else {
                throw new BusinessException("该房屋不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("获取失败", e);
        }
        return null;
    }

    /**
     * 新增索引
     *
     * @param houseIndexTemplate
     */
    private void create(HouseIndexTemplate houseIndexTemplate) {
        IndexRequest request = new IndexRequest(HOUSE_INDEX);
        request.id(houseIndexTemplate.getHouseId().toString());
        request.source(JSONObject.toJSONString(houseIndexTemplate), XContentType.JSON);
        try {
            restClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            logger.error("保存es失败", e);
        }
    }

    @Override
    public void delete(Long houseId) {

    }
}
