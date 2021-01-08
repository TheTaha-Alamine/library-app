package com.example.library.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.model.Lend;
import com.example.library.model.LendStatus;
import com.example.library.model.Member;
import com.example.library.model.MemberStatus;
import com.example.library.model.request.AuthorCreationRequest;
import com.example.library.model.request.BookCreationRequest;
import com.example.library.model.request.BookLendRequest;
import com.example.library.model.request.MemberCreationRequest;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LendRepository;
import com.example.library.repository.MemberRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;



@Service
public class LibraryService {
	
	
	private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final LendRepository lendRepository;
    public LibraryService(AuthorRepository authorRepository, MemberRepository memberRepository,
			LendRepository lendRepository, BookRepository bookRepository) {
		super();
		this.authorRepository = authorRepository;
		this.memberRepository = memberRepository;
		this.lendRepository = lendRepository;
		this.bookRepository = bookRepository;
	}

	private final BookRepository bookRepository;
 
    public Book readBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given ID");
    }
    public List<Book> readBooks() {
        return bookRepository.findAll();
    }
    public Book readBook(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given ISBN");
    }
    public Book createBook(BookCreationRequest book) {
        Optional<Author> author = authorRepository.findById(book.getAuthorId());
        if (!author.isPresent()) {
            throw new EntityNotFoundException("Author Not Found");
        }
        Book bookToCreate = new Book();
        BeanUtils.copyProperties(book, bookToCreate);
        bookToCreate.setAuthor(author.get());
        return bookRepository.save(bookToCreate);
    }
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    public Member createMember(MemberCreationRequest request) {
        Member member = new Member();
        BeanUtils.copyProperties(request, member);
        return memberRepository.save(member);
    }
    public Member updateMember (Long id, MemberCreationRequest request) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (!optionalMember.isPresent()) {
            throw new EntityNotFoundException("Member not present in the database");
        }
        Member member = optionalMember.get();
        member.setLastName(request.getLastName());
        member.setFirstName(request.getFirstName());
        return memberRepository.save(member);
    }
    public Author createAuthor (AuthorCreationRequest request) {
        Author author = new Author();
        BeanUtils.copyProperties(request, author);
        return authorRepository.save(author);
    }
    
	public List<String> lendABook (List<BookLendRequest> list) {
        List<String> booksApprovedToBurrow = new ArrayList();
        list.forEach(bookLendRequest -> {
            Optional<Book> bookForId = bookRepository.findById(bookLendRequest.getBookId());
            if (!bookForId.isPresent()) {
                throw new EntityNotFoundException("Cant find any book under given ID");
            }
            Optional<Member> memberForId = memberRepository.findById(bookLendRequest.getMemberId());
            if (!memberForId.isPresent()) {
                throw new EntityNotFoundException("Member not present in the database");
            }
            Member member = memberForId.get();
            if (member.getStatus() != MemberStatus.ACTIVE) {
                throw new RuntimeException("User is not active to proceed a lending.");
            }
            Optional<Lend> burrowedBook = lendRepository.findByBookAndStatus(bookForId.get(), LendStatus.BURROWED);
            if (!burrowedBook.isPresent()) {
                booksApprovedToBurrow.add(bookForId.get().getName());
                Lend lend = new Lend();
                lend.setMember(memberForId.get());
                lend.setBook(bookForId.get());
                lend.setLendStatus(LendStatus.BURROWED);
                lend.setStartOn(Instant.now());
                lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));
                lendRepository.save(lend);
            }
        });
        return booksApprovedToBurrow;
    }
}
