package org.example.ioc.service;

import org.example.ioc.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "bookService_qualifier")
public class BookService_Qualifier {

    @Autowired
    @Qualifier("bookDaoImpl")  // 要与@Autowired，表示根据名称进行匹配
    private BookDao bookDao;

    public void add() {
        bookDao.add();
        System.out.println("service add...");
    }
}
