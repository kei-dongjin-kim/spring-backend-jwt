package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
