package org.example.ioc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service(value = "bookService_value")
public class BookService_Value {
    @Value(value = "abc")
    private String name;

    public void add() {
        System.out.println(name);
        System.out.println("service add...");
    }
}
