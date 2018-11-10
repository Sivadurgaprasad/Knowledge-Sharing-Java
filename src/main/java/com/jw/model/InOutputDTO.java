package com.jw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class InOutput {
	@Id
	private String id;
	private String in;
	@Transient
	private String out;
}
