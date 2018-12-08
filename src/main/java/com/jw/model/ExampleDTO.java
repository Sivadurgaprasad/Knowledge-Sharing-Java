package com.jw.model;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class ExampleDTO implements Serializable {

	private static final long serialVersionUID = -8796844375934311226L;
	private String example;
	private String explanation;
	@Transient
	private String program;
}
