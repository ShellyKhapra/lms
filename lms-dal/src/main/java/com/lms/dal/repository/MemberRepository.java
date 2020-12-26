package com.lms.dal.repository;

import com.lms.dal.entities.EMember;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends PagingAndSortingRepository<EMember, Integer> {

}
