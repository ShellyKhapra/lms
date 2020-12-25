package com.lms.service;

import com.lms.dal.entities.EAuthor;
import com.lms.dal.entities.EBook;
import com.lms.dal.repository.AuthorRepository;
import com.lms.dal.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<EBook> getAllBooks(){

        List<EBook> list = new ArrayList<>();
        Iterator<EBook> iterator =  this.bookRepository.findAll().iterator();
        iterator.forEachRemaining(list::add);

        return list;
    }

    public List<EBook> getAllBooksForAuthorFirstName(String firstName){

        List<EAuthor> authors = this.authorRepository.getAuthorByFirstName(firstName);

        List<EBook> books = this.bookRepository.getBookByAuthorIn(authors);

        return books;
    }

    public static void getBooksTest(){
//        BookService bookService = new BookService()
    }
}
