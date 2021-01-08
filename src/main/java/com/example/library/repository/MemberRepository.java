package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.model.Member;

public interface MemberRepository extends JpaRepository<Member , Long> {

}
