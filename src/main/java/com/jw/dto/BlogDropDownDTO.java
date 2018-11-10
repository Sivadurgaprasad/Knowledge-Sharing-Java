package com.jw.dto;

import java.util.List;

import com.jw.model.SubTech;

import lombok.Data;

@Data
public class BlogDropDownDTO {

	private String id;
	private String blog;
	private List<SubTech> subTechs;
}
