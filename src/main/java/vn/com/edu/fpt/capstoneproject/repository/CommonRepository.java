package vn.com.edu.fpt.capstoneproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommonRepository<E, ID> extends JpaRepository<E, ID>, QuerydslPredicateExecutor<E> {

    List<E> findByIdIn(List<ID> ids);
}