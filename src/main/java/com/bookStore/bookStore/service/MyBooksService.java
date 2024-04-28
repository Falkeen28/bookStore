package com.bookStore.bookStore.service;

import com.bookStore.bookStore.entity.Book;
import com.bookStore.bookStore.entity.MyBookList;
import com.bookStore.bookStore.entity.MyUser;
import com.bookStore.bookStore.repository.BookRepository;
import com.bookStore.bookStore.repository.MyBooksRepository;
import com.bookStore.bookStore.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyBooksService {
    @Autowired
    private MyBooksRepository myBooksRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MyUserRepository myUserRepository;

    public List<MyBookList> getAllMyBooks() {
        return myBooksRepository.findAll();
    }
    public void addBookToMyList(int bookId, int userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Książka nie znaleziona: " + bookId));
        book.decrementQuantity(); // Zmniejsz ilość książki

        MyUser user = myUserRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Użytkownik nie znaleziony: " + userId));

        MyBookList myBookList = new MyBookList();
        myBookList.setTitle(book.getTitle());
        myBookList.setAuthor(book.getAuthor());
        myBookList.setQuantity(1); // Zakładamy, że ilość dodawana do listy to zawsze 1
        myBookList.setPrice(book.getPrice());
        myBookList.setMyUser(user); // Ustaw użytkownika dla listy książek

        myBooksRepository.save(myBookList); // Zapisz do listy użytkownika

        bookRepository.save(book); // Zaktualizuj książkę w ogólnej liście z zmniejszoną ilością
    }
    public void removeBookFromMyList(int myBookListId) {
        MyBookList myBookList = myBooksRepository.findById(myBookListId).orElseThrow(() -> new IllegalArgumentException("MyBookList not found: " + myBookListId));
        Book book = bookRepository.findById(myBookList.getId()).orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + myBookList.getId()));

        book.incrementQuantity(); // zwiększ ilość książek

        myBooksRepository.deleteById(myBookListId); // usuń książkę z listy użytkownika

        bookRepository.save(book); //zapisz książkę z zwiększoną ilością
    }
    public List<MyBookList> getAllMyBooksSorted(String sortBy, String sortDir) {
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "title";
        }
        List<MyBookList> myBooks = getAllMyBooks();
        Comparator<MyBookList> comparator;
        switch (sortBy) {
            case "title":
                comparator = Comparator.comparing(MyBookList::getTitle, Comparator.nullsFirst(String::compareTo));
                break;
            case "author":
                comparator = Comparator.comparing(MyBookList::getAuthor, Comparator.nullsFirst(String::compareTo));
                break;
            case "price":
                comparator = Comparator.comparingInt(MyBookList::getPrice);
                break;
            case "quantity":
                comparator = Comparator.comparingInt(MyBookList::getQuantity);
                break;
            default:
                return myBooks;
        }
        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }
        return myBooks.stream().sorted(comparator).collect(Collectors.toList());
    }
}

