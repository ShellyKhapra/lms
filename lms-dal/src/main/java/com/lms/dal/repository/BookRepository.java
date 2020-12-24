package com.lms.dal.repository;

import com.lms.dal.entities.Author;
import com.lms.dal.entities.Book;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    List<Book> getBookByAuthor(Author author);

    List<Book> getBookByAuthorIn(List<Author> authors);
}
