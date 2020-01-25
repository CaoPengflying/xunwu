package com.cpf.xunwu.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author caopengflying
 * @time 2020/1/25
 */
@Controller
public class AppErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @Autowired
    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * 返回错误页面
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorPageHandler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        int status = httpServletResponse.getStatus();
        switch (status) {
            case 403:
                return "403";
            case 404:
                return "404";
            case 500:
                return "500";
            default:
        }
        return "index";
    }

    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public Result errorApiHandler(HttpServletRequest request) {
        ServletWebRequest requestAttributes = new ServletWebRequest(request);
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status == null) {
            status = 500;
        }
        return ErrorConstant.getErrorResult(status.toString(), String.valueOf(attr.getOrDefault("message", "error")));


    }
}
