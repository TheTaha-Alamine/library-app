package com.example.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.model.Book;
import com.example.library.model.Lend;
import com.example.library.model.LendStatus;


public interface LendRepository extends JpaRepository<Lend , Long> {

	Optional<Lend> findByBookAndStatus(Book book, LendStatus burrowed);

}
