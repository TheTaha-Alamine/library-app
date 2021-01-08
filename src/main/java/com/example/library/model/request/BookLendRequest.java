package com.example.library.model.request;

import lombok.Data;

@Data
public class BookLendRequest {
	private Long bookId;
    private Long memberId;
	public Long getBookId() {
		// TODO Auto-generated method stub
		return bookId;
	}
	public Long getMemberId() {
		// TODO Auto-generated method stub
		return memberId;
	}
}
