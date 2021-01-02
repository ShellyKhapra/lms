package com.lms.dal.repository;

import com.lms.dal.entities.EAuthor;
import com.lms.dal.entities.EBook;
import java.util.List;

import com.lms.dal.entities.EBookType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<EBook, Integer> {

    List<EBook> getBookByAuthor(EAuthor author);

    List<EBook> getBookByAuthorIn(List<EAuthor> authors);

    List<EBook> getByNumberOfCopiesIsGreaterThan(Integer numberOfCopies);

    // We can create complex queries this way too
    @Query("select count(b) from EBook b where b.bookType = :bookType")
    Long countByBookType(@Param("bookType") EBookType bookType);
}
