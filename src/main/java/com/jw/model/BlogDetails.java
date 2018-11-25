package com.jw.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class BlogDetails {

	@Id
	private String id;
	
}
