package com.jw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class Scenario {
	@Id
	private String id;
	private String scenario;
	private String explanation;
	@Transient
	private String archetecture;
}
