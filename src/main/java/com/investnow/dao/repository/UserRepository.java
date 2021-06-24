package com.investnow.dao.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.investnow.dao.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Serializable>, JpaSpecificationExecutor<User>
{
    Optional<User> findByUserName(String userName);
}
