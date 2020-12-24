package com.lms.dal.repository;

import com.lms.dal.entities.Member;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {

}
