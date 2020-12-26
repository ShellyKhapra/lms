package com.lms.dal.repository;

import com.lms.dal.entities.EAuthor;
import com.lms.dal.entities.EBook;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<EBook, Integer> {

    List<EBook> getBookByAuthor(EAuthor author);

    List<EBook> getBookByAuthorIn(List<EAuthor> authors);
}
