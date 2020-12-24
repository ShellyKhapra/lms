package com.lms.service;

import com.lms.dal.entities.Author;
import com.lms.dal.entities.Book;
import com.lms.dal.repository.AuthorRepository;
import com.lms.dal.repository.BookRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllBooks(){

        List<Book> list = new ArrayList<>();
        Iterator<Book> iterator =  this.bookRepository.findAll().iterator();
        iterator.forEachRemaining(list::add);

        return list;
    }

    public List<Book> getAllBooksForAuthorFirstName(String firstName){

        List<Author> authors = this.authorRepository.getAuthorByFirstName(firstName);

        List<Book> books = this.bookRepository.getBookByAuthorIn(authors);

        return books;
    }
}
