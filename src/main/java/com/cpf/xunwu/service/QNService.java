package com.cpf.xunwu.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
public interface QNService {
    /**
     *
     * @param file
     * @return
     * @throws QiniuException
     */
    Response uploadFile(File file) throws QiniuException;

    /**
     *
     * @param inputStream
     * @return
     */
    Response uploadFile(InputStream inputStream) throws QiniuException;

    /**
     *
     * @param key
     * @return
     */
    Response deleteFile(String key) throws QiniuException;


}
