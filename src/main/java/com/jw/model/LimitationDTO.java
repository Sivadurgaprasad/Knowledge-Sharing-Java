package com.jw.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Limitation {
	@Id
	private String id;
	private String limitation;
}
