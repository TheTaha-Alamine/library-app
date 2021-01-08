package com.example.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book , Long> {

	Optional<Book> findByIsbn(String isbn);
	
}
