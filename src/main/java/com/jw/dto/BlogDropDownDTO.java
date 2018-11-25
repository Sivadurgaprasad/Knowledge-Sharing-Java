package com.jw.dto;

import java.util.List;

import lombok.Data;

@Data
public class BlogDropDownDTO {

	private String id;
	private String blog;
	private List<String> subTechs;
}
