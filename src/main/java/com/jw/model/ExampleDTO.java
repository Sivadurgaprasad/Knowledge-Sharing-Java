package com.jw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class Example {
	@Id
	private String id;
	private String example;
	private String explanation;
	@Transient
	private String program;
}
