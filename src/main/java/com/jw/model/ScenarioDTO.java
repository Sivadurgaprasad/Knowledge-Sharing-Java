package com.jw.model;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class ScenarioDTO implements Serializable {

	private static final long serialVersionUID = -1513166105620057937L;
	private String scenario;
	private String explanation;
	@Transient
	private String archetecture;
}
