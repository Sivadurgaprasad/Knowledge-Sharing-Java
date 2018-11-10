package com.jw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class Archetecture {
	@Id
	private String id;
	private String archetecture;
	@Transient
	private String diagram;
	private String explanation;
}
