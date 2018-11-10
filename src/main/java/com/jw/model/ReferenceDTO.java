package com.jw.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Reference {
	@Id
	private String id;
	private String reference;
}
