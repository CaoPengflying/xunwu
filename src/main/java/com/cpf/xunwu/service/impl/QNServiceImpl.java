package com.cpf.xunwu.service.impl;

import com.cpf.xunwu.base.ApplicationConstants;
import com.cpf.xunwu.service.QNService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
@Service
public class QNServiceImpl implements QNService, InitializingBean {
    @Resource
    private UploadManager uploadManager;
    @Resource
    private BucketManager bucketManager;
    @Resource
    private Auth auth;
    @Value("${qiniu.bucket}")
    private String bucket;

    private StringMap putPolicy;

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Override
    public Response uploadFile(File file) throws QiniuException {
        Response response = this.uploadManager.put(file,null, getUploadToken());
        int retry = 0;
        while (retry < ApplicationConstants.QN_RETRY_TIMES && response.needRetry()) {
            response = this.uploadManager.put(file, null, getUploadToken());
            retry++;
        }
        return response;
    }

    @Override
    public Response uploadFile(InputStream inputStream) throws QiniuException {
        Response response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
        int retry = 0;
        while (retry < ApplicationConstants.QN_RETRY_TIMES && response.needRetry()) {
            response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
            retry++;
        }
        return response;
    }

    @Override
    public Response deleteFile(String key) throws QiniuException {
        Response response = bucketManager.delete(bucket, key);
        int retry = 0;
        while (retry < ApplicationConstants.QN_RETRY_TIMES && response.needRetry()) {
            response = bucketManager.delete(bucket, key);
            retry++;
        }
        return response;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width),\"height\":$(imageInfo.height)}");
    }


    public String getUploadToken() {
        return this.auth.uploadToken(bucket, null, 3600, putPolicy);
    }
}
