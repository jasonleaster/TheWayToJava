package org.jasonleaster.springtutorial.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: jasonleaster
 * Date  : 2017/4/23
 * Email : jasonleaster@gmail.com
 *
 * Demo for restful controller
 */

public class RESTfulController {

    /**
     * 根据请求内容的类型，返回一本“书”的不同展现形式。
     *
     * RESTful，同一资源的不同展现形式的转换
     *
     * HTTP 请求头中的 contentType和accept决定资源的展现形式
     */
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    @ResponseBody
    public String book(HttpServletRequest request)
    {
        String contentType = request.getContentType();
        if(contentType == null){
            return "book.default";
        }else if (contentType.equals("txt")){
            return "book.txt";
        }else if (contentType.equals("html")){
            return  "book.html";
        }
        return "book.default";
    }

    @RequestMapping(value = "/subject/{subjectId}", method = RequestMethod.GET)
    @ResponseBody
    public String subjectGet(@PathVariable("subjectId") int subjectId) {
        return "This is a get method, subjectId:" + subjectId;
    }

    @RequestMapping(value = "/subject/{subjectId}", method = RequestMethod.POST)
    @ResponseBody
    public String subjectPost(@PathVariable("subjectId") int subjectId) {
        return "This is a post method, subjectId:" + subjectId;
    }

    @RequestMapping(value = "/subject/{subjectId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String subjectDELETE(@PathVariable("subjectId") int subjectId) {
        return "This is a delete method, subjectId:" + subjectId;
    }

    @RequestMapping(value = "/subject/{subjectId}", method = RequestMethod.PUT)
    @ResponseBody
    public String subjectPut(@PathVariable("subjectId") int subjectId) {
        return "This is a put method, subjectId:" + subjectId;
    }
}
