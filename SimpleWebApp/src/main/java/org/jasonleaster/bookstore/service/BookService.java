package org.jasonleaster.bookstore.service;

import org.jasonleaster.bookstore.form.BookSearchForm;
import org.jasonleaster.bookstore.model.Book;
import org.jasonleaster.bookstore.util.PageInfo;

import java.util.List;

public interface BookService {
    public void init();

    public Book getById(String id);

    public List<Book> pagedFuzzyQuery(BookSearchForm book, PageInfo pageInfo) throws Exception;

    public List<Book> getPopularBook(long bookNum);

    public void add(Book book) throws Exception;

    public void delete(String id);

    public void modify(Book book);

    public long totalCountInDB();
}
