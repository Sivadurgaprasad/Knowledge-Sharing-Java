package com.jw.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Importance {
	@Id
	private String id;
	private String importance;
}
