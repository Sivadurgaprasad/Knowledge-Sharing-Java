package com.jw.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Need {
	@Id
	private String id;
	private String need;
}
