package org.jasonleaster.springtutorial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.jasonleaster.bookstore.util.URLs;
import org.jasonleaster.springtutorial.model.POJO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: jasonleaster
 * Date  : 2017/4/20
 * Email : jasonleaster@gmail.com
 */

// 避免被Login filter过滤掉请求, URL第一级设置为URLs.PATH_TUTORIAL
@Controller
@RequestMapping(value = URLs.PATH_TUTORIAL, produces = MediaType.APPLICATION_JSON_VALUE)
public class TutorialController {

    /**
     * 加上ResponseBody注解是必须的，否则返回值会被错误的当做视图名
     *
     * You can't return a primitive type (or a primitive wrapper type) and
     * get JSON object as a response.You must return some object, for instance
     * a Map or custom domain object.
     */
    @RequestMapping(value = "baseType.do", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int baseType(int age){
        return age;
    }

    /**
     * /**
     * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
     * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
     * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
     * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
     * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
     * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
     */
    @RequestMapping(value = "baseObjectType.do", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map baseObjectType(int age){
        Map map = new HashMap();
        map.put("age", age);
        return map;
    }

    @RequestMapping(value = "baseStringType.do", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String baseStringType(int age) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        POJO object = new POJO();
        object.setAge(age);
        object.setName("json");
        return mapper.writeValueAsString(object);
    }



}
