package com.jw.model;

import java.io.Serializable;

import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class InOutputDTO implements Serializable {

	private static final long serialVersionUID = -7402903339049015083L;
	private String in;
	@Transient
	private String out;
}
