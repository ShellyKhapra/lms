package com.lms.dal.repository;

import com.lms.dal.entities.EAuthor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<EAuthor, Long> {

    List<EAuthor> getAuthorByFirstName(String firstName);

    List<EAuthor> getAuthorByFirstNameAndLastName(String firstName, String lastName);

    List<EAuthor> getAuthorByFirstNameOrLastName(String firstName, String lastName);
}
