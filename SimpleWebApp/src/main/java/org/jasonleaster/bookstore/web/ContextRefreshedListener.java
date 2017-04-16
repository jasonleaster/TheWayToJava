package org.jasonleaster.bookstore.web;

import org.jasonleaster.bookstore.Project;
import org.jasonleaster.bookstore.model.Book;
import org.jasonleaster.bookstore.model.Record;
import org.jasonleaster.bookstore.model.User;
import org.jasonleaster.bookstore.service.BookService;
import org.jasonleaster.bookstore.service.RecordService;
import org.jasonleaster.bookstore.service.UserService;
import org.jasonleaster.bookstore.util.SupplementaryDataFactory;
import org.jasonleaster.bookstore.web.config.DataConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RecordService recordService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        ApplicationContext context = event.getApplicationContext();

        if(Project.UNIT_TEST){
            return;
        }

        if(Project.WEB_INTEGRATED_DEBUG){
            DataConfig.initDB(dataSource);
            Book[] books = SupplementaryDataFactory.getBooks();
            User[] users = SupplementaryDataFactory.getUsers();
            Record[] records = SupplementaryDataFactory.getRecords();

            try {
                for (int i = 0; i < books.length; i++){
                    bookService.add(books[i]);
                }

                for (int i = 0; i < users.length; i++){
                    userService.add(users[i]);
                }

                for (int i = 0; i < records.length; i++){
                    recordService.add(records[i]);
                }
            }catch (Exception ignore){
            }


        }


        /*
         * Just image that, what if the website crashed for some reasons.
         * You fix the bug quickly and restart the server to run this project.
         * Some useful information about database are cached in memory.
         * In this situation, the in-memory data lost and the variable @booksNumInDB
         * reinitialized into zero.
         *
         * It's a good trick to put the database related meta-information code
         * into the Spring Bean setter function. And the value of @booksNumInDB
         * is correct.
         * */
        bookService.init();
        userService.init();
        recordService.init();


        try {
            userService.add(Project.ADMIN);
        }catch (Exception ignore){}


        if(context.getParent() == null){
            System.out.println("Spring容器初始化完毕================================================");
            System.out.println("ContextRefreshedListener is invoked");
        }
    }

}