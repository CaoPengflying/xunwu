package com.cpf.xunwu;

import com.cpf.xunwu.service.QNService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author caopengflying
 * @time 2020/1/26
 */
public class QNServiceTest extends XunwuApplicationTests {
    @Resource
    private QNService qnService;
    @Test
    public void testUploadFile() throws QiniuException {
        File file = new File("E:\\IdeaWorkSpace\\xunwu\\temp\\微信图片_20190326213752.jpg");
        Response response = qnService.uploadFile(file);
        Assert.assertTrue(response.isOK());
    }
}
