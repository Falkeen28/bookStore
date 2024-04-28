package com.bookStore.bookStore.repository;

import com.bookStore.bookStore.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
    MyUser findByUsername(String username);
}

