package com.lms.dal.repository;

import com.lms.dal.entities.EBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryExt {

    private final BookRepository bookRepository;
    private final EntityManager entityManager;

    @Autowired
    public BookRepositoryExt(BookRepository bookRepository, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.entityManager = entityManager;
    }

    // We can create complex queries this way too
    public List<EBook> getMostFamousBooks(int numberOfCopies){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EBook> criteriaQuery = cb.createQuery(EBook.class);
        Root<EBook> root = criteriaQuery.from(EBook.class);
        criteriaQuery.select(root);
        List<Predicate> predicatesList = new ArrayList<>();

        predicatesList.add(cb.greaterThan(root.get("numberOfCopies"), numberOfCopies));

        Predicate[] finalPredicates = new Predicate[predicatesList.size()];
        predicatesList.toArray(finalPredicates);

        criteriaQuery.where(finalPredicates);

        List<EBook> result = entityManager.createQuery(criteriaQuery).getResultList();

        return result;
    }

}
