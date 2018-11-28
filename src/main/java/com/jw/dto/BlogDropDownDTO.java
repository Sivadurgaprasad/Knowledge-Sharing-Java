package com.jw.dto;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class BlogDropDownDTO {

	@Id
	private String id;
	private String blog;
	private List<String> subTechs;
}
