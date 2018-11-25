package com.jw.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class DefinitionDTO implements Serializable {

	private static final long serialVersionUID = 5485059936715904642L;
	private String definition;
	private String explanation;
}
