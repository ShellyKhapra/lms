package com.lms.dal.repository;

import com.lms.dal.entities.Author;
import com.lms.dal.entities.Member;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {

    List<Author> getAuthorByFirstName(String firstName);

    List<Author> getAuthorByFirstNameAndLastName(String firstName, String lastName);

    List<Author> getAuthorByFirstNameOrLastName(String firstName, String lastName);
}
