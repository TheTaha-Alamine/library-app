package com.example.library.model.request;

import lombok.Data;

@Data
public class BookCreationRequest {
	private String name;
    private String isbn;
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	private Long authorId;
	public Long getAuthorId() {
		// TODO Auto-generated method stub
		return authorId;
	}
	
}
