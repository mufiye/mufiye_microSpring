package org.example.ioc.service;

import org.example.ioc.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 @AutoWired 根据属性类型进行自动装配
 @Qualifier 根据属性名称进行注入
 @Value 注入普通类型属性
 */

@Service(value = "bookService_autowired")
public class BookService_Autowired {
    private final BookDao bookDao;

    // @Autowired优先按照类型去找，如果有多个相同类型的组件，则将属性的名称作为组件的id去容器中查找
    // to solve the problem that Field injection is not recommended
    @Autowired
    public BookService_Autowired(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void add() {
        bookDao.add();
        System.out.println("service add...");
    }
}

