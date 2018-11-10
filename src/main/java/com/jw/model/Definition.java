package com.jw.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Definition {
	@Id
	private String id;
	private String definition;
	private String explanation;
}
