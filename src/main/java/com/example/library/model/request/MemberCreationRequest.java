package com.example.library.model.request;

import lombok.Data;

@Data
public class MemberCreationRequest {
	private String firstName;
    private String lastName;
	public String getLastName() {
		// TODO Auto-generated method stub
		return lastName;
	}
	public String getFirstName() {
		// TODO Auto-generated method stub
		return firstName;
	}
}
