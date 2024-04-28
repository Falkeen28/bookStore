package com.bookStore.bookStore.controller;

import com.bookStore.bookStore.entity.MyBookList;
import com.bookStore.bookStore.model.BookDTO;
import com.bookStore.bookStore.service.BookService;
import com.bookStore.bookStore.service.MyBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private MyBooksService myBooksService;

    @GetMapping({"/", "/home"})
    public String Home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();

            model.addAttribute("username", currentUserName);
        }
        return "home";
    }

    @GetMapping("/book_register")
    public String BookRegister() {
        return "bookRegister";
    }

    @GetMapping("/available_books")
    public ModelAndView getAllBooks(@RequestParam(value = "sort", required = false) String sort,
                                    @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        List<BookDTO> books = bookService.getAllBooksSorted(sort, sortDir);
        return new ModelAndView("bookList", "books", books);
    }

    @PostMapping("/save")
    public String addBook(@ModelAttribute BookDTO bookDTO) {
        bookService.save(bookDTO);
        return "redirect:/available_books";
    }

    @GetMapping("/my_books")
    public String getMyBooks(Model model,
                             @RequestParam(value = "sort", required = false, defaultValue = "title") String sort,
                             @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir) {
        List<MyBookList> myBooks = myBooksService.getAllMyBooksSorted(sort, sortDir);
        model.addAttribute("myBooks", myBooks);
        return "myBooks";
    }


    @RequestMapping("/mylist/{id}")
    public String addBookToMyList(@PathVariable("id") int id) {
        myBooksService.addBookToMyList(id, id);
        return "redirect:/my_books";
    }

    @RequestMapping("/editBook/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        bookService.getBookById(id);
        model.addAttribute("book", bookService.getBookById(id));
        return "bookEdit";
    }

    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/available_books";
    }
}

