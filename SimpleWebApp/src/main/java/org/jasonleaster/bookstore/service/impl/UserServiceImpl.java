package org.jasonleaster.bookstore.service.impl;

import org.jasonleaster.bookstore.dao.IUserMapper;
import org.jasonleaster.bookstore.model.User;
import org.jasonleaster.bookstore.service.UserService;
import org.jasonleaster.bookstore.util.PageInfo;
import org.jasonleaster.bookstore.web.BaseDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * This annotation will help to sign a business logic component
 */

@SuppressWarnings("serial")
@Service("userService")
public class UserServiceImpl extends BaseDomain implements UserService {

    @Autowired
    private IUserMapper userMapper;

    static private long usersNumInDB;

    public IUserMapper getIUserMapper() {
        return userMapper;
    }

    @Override
    public void init() {
        usersNumInDB = userMapper.countAll();
    }

    @Override
    public User getById(String id) {
        if(id == null){
            return null;
        }

        User user = null;
        try{
            user = userMapper.selectByPrimaryKey(id);
        }catch (Exception ignore){}

        return user;
    }

    @Override
    public void add(User user){
        if(user == null){
            return;
        }

        if(getById(user.getEmail()) == null){
            userMapper.insert(user);
            usersNumInDB++;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public List<User> pagedFuzzyQuery(User user, PageInfo pageInfo) throws Exception {
        List<User> users = new ArrayList<>();

        if(user == null){
            user = new User(); // empty search form
        }

        if(pageInfo == null){
            pageInfo = new PageInfo();
        }

        if(user.getEmail() != null){
            users.add(this.getById(user.getEmail()));
            return users;
        }

        HashMap map = new HashMap();

        Field[] fields = user.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            map.put(field.getName(), field.get(user));
        }

        fields = pageInfo.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            map.put(field.getName(), field.get(pageInfo));
        }

        users = userMapper.selectFuzzy(map);

        int totalCount = userMapper.selectItemCount(map);
        pageInfo.setTotalCount(totalCount);

        return users;
    }

    @Override
    public void delete(String id) {
        if(id == null || getById(id) == null){
            return;
        }
        userMapper.deleteByPrimaryKey(id);
        usersNumInDB--;
    }

    @Override
    public void modify(User user) {
        if(user == null || getById(user.getEmail()) == null){
            return;
        }
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public long totalCountInDB() {
        return usersNumInDB;
    }
}
