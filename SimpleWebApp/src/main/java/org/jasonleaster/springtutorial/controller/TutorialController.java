package org.jasonleaster.springtutorial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jasonleaster.bookstore.util.URLs;
import org.jasonleaster.springtutorial.model.User;
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
     *
     * 关于@ResponseBody注解:
     * Annotation that indicates a method return value should be bound to the web
     * response body.
     */
    // localhost:8080/tutorial/baseType.do?age=10
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
    //localhost:8080/tutorial/baseObjectType.do?age=10
    @RequestMapping(value = "baseObjectType.do", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map baseObjectType(int age){
        Map map = new HashMap();
        map.put("age", age);
        return map;
    }

    //localhost:8080/tutorial/baseStringType.do?age=10
    @RequestMapping(value = "baseStringType.do", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String baseStringType(int age) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User object = new User();
        object.setAge(age);
        object.setName("json");
        return mapper.writeValueAsString(object);
    }

    //localhost:8080/tutorial/baseArrayType.do?name=jason&name=annabella&name=jack
    @RequestMapping(value = "baseArrayType.do", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String baseArrayType(String[] names) throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        List objs = new LinkedList();
        for (String name : names){
            User object = new User();
            object.setName(name);
            objs.add(object);
        }

        return mapper.writeValueAsString(objs);
    }

    /**
     * 简单对象和多层级对象的绑定
     */
    // localhost:8080/tutorial/objectType.do?age=10&name=jasonleaster&contact.phone=110
    @RequestMapping(value = "objectType.do", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User objectType(User user) throws Exception{
        // 此处使用对象作为参数相当于form表单接受对象
        user.setName("changedName");
        return user;
    }





}
