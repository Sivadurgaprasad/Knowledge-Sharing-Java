package com.jw.dto;

import java.util.List;

import lombok.Data;

@Data
public class TechInfoDTO {

	private String id;
	private String blog;
	private List<String> subTechs;
	private String blogIconName;
	private String shortNote;
}
