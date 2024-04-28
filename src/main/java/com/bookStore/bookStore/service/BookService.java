package com.bookStore.bookStore.service;

import com.bookStore.bookStore.model.BookDTO;
import com.bookStore.bookStore.entity.Book;
import com.bookStore.bookStore.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Book> convertToEntityList(List<BookDTO> bookDTOList) {
        return bookDTOList.stream()
                .map(bookDTO -> modelMapper.map(bookDTO, Book.class))
                .collect(Collectors.toList());
    }
    public List<BookDTO> convertToDtoList(List<Book> bookList) {
        return bookList.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    public void save(BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        bookRepository.save(book);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
        return modelMapper.map(book, BookDTO.class);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    public List<BookDTO> getAllBooksSorted(String sortBy, String sortDir) {
        List<Book> books = getAllBooks().stream()
                .map(bookDTO -> modelMapper.map(bookDTO, Book.class))
                .collect(Collectors.toList());

        Comparator<Book> comparator = chooseComparator(sortBy);

        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }

        return books.stream().sorted(comparator)
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    private Comparator<Book> chooseComparator(String sortBy) {
        if (sortBy == null) {
            sortBy = "title";
        }
        switch (sortBy) {
            case "title":
                return Comparator.comparing(Book::getTitle, Comparator.nullsFirst(String::compareTo));
            case "author":
                return Comparator.comparing(Book::getAuthor, Comparator.nullsFirst(String::compareTo));
            case "price":
                return Comparator.comparingInt(Book::getPrice);
            case "quantity":
                return Comparator.comparingInt(Book::getQuantity);
            default:
                return Comparator.comparing(Book::getTitle, Comparator.nullsFirst(String::compareTo)); // Handle unexpected values safely
        }
    }

}

