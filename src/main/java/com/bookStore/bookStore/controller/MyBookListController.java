package com.bookStore.bookStore.controller;

import com.bookStore.bookStore.service.MyBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyBookListController {
    @Autowired
    private MyBooksService myBooksService;
    @RequestMapping("/deleteMyList/{id}")
    public String removeBookFromMyList(@PathVariable("id") int id) {
        myBooksService.removeBookFromMyList(id);
        return "redirect:/my_books";
    }
}

