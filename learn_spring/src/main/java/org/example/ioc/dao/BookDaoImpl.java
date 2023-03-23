package org.example.ioc.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/*
 @Component 普通的组件
 @Service 业务层
 @Controller 控制层
 @Repository 持久层
 */

@Primary  // 该注解表示该组件作为首选被装配
@Repository
public class BookDaoImpl implements BookDao {
    @Override
    public void add() {
        System.out.println("BookDaoImpl add...");
    }
}
