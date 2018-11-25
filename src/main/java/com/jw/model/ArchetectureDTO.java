package com.jw.model;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class ArchetectureDTO implements Serializable {

	private static final long serialVersionUID = -2888121561646554681L;
	private String archetecture;
	@Transient
	private String diagram;
	private String explanation;
}
