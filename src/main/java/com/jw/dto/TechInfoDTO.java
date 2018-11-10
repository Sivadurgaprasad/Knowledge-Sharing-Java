package com.jw.dto;

import java.util.List;

import com.jw.model.SubTech;

import lombok.Data;

@Data
public class TechInfoDTO {

	private String id;
	private String blog;
	private List<SubTech> subTechs;
	private String blogIconName;
	private String shortNote;
}
