package com.cpf.xunwu;

import com.cpf.xunwu.search.HouseIndexTemplate;
import com.cpf.xunwu.service.impl.HouseEsSearchServiceImpl;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caopengflying
 * @time 2020/3/1
 */
public class HouseEsSearchServiceImplTest extends XunwuApplicationTests {
    @Resource
    private HouseEsSearchServiceImpl houseEsSearchService;

    @Test
    public void testIndex() {
        houseEsSearchService.index(15L);
    }

    @Test
    public void testGet() {
        HouseIndexTemplate houseIndexTemplate = houseEsSearchService.get("15");
        System.out.println(houseIndexTemplate);
    }

    @Test
    public void index() {
//        IndexRequest request = new IndexRequest("xunwu_index");
//        request.id("2");
//        String jsonString = "{" +
//                "\"houseId\":\"2\"," +
//                "\"title\":\"测试2\"," +
//                "\"price\":\"1000\"" +
//                "}";
//        request.source(jsonString, XContentType.JSON);
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("houseId", "3");
        jsonMap.put("title", "测试3");
        jsonMap.put("price", "3000");
        IndexRequest indexRequest = new IndexRequest("xunwu_index")
                .id("3").source(jsonMap);
    }

}