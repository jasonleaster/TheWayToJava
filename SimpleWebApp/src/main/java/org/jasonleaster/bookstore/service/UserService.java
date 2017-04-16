package org.jasonleaster.bookstore.service;



import org.jasonleaster.bookstore.model.User;
import org.jasonleaster.bookstore.util.PageInfo;

import java.util.List;

public interface UserService {
    public void init();

    public User getById(String id);

    public List<User> pagedFuzzyQuery(User user, PageInfo pageInfo) throws Exception;

    public void add(User user);

    public void delete(String id);

    public void modify(User user);

    public long totalCountInDB();
}
