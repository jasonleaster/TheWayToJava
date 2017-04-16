package org.jasonleaster.bookstore.controller.restful;

import org.jasonleaster.bookstore.form.BookSearchForm;
import org.jasonleaster.bookstore.model.Book;
import org.jasonleaster.bookstore.service.BookService;
import org.jasonleaster.bookstore.util.AttributesKey;
import org.jasonleaster.bookstore.util.PageInfo;
import org.jasonleaster.bookstore.util.URLs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = URLs.API + URLs.BOOKS)
public class RestfulBookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = URLs.QUERY, produces={"application/json;charset=UTF-8"})
    public @ResponseBody List<Book>
    query(BookSearchForm form,
         @RequestParam(value = "pageNum", required = false) Integer pageNum,
          HttpServletRequest request) throws Exception {

        form = BookSearchForm.searchFormPreProcess(form);

        HttpSession session = request.getSession();
        BookSearchForm oldForm = (BookSearchForm) session.getAttribute(AttributesKey.SESSION_ATTRIBUTES_BOOK_QUERY_FORM);

        if (pageNum != null) {
            form = oldForm;
        } else {
            session.setAttribute(AttributesKey.SESSION_ATTRIBUTES_BOOK_QUERY_FORM, form);
        }

        if (form.getIsbn() != null) {
            // Unfinished
            return null;
            //queryBookByISBN(form.getIsbn(), model);
        }

        PageInfo pageInfo;

        int pageSize = 4;
        if (pageNum == null) {
            pageInfo = new PageInfo(0, pageSize, new ArrayList<>());
        } else {
            pageInfo = new PageInfo((pageNum.intValue() - 1) * pageSize, pageSize, new ArrayList<>());
        }

        pageInfo.setURL(request.getRequestURI());

        List<Book> booksInDB = null;
        try {
            booksInDB = bookService.pagedFuzzyQuery(form, pageInfo);
        }catch (Exception e){
        }

        return booksInDB;
    }
}
