package com.jw.service;

import java.util.List;

import com.jw.dto.BlogDropDownDTO;

public interface DropDownService {
	
	public List<BlogDropDownDTO> getAllTechnologies();
	
	public BlogDropDownDTO getAllSubTechnologies(String technologyId);
	
}
