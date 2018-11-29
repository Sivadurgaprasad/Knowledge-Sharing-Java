package com.jw.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jw.dto.BlogDropDownDTO;
import com.jw.service.DropDownService;

@RestController
@RequestMapping(value="/dropDown")
public class DropDownController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DropDownController.class);

	@Autowired
	private DropDownService dropDownService;

	@GetMapping(value = "/technologies")
	public List<BlogDropDownDTO> getTechnology() {
		LOGGER.debug("Technologies fetching for dropdown");
		return dropDownService.getAllTechnologies();
	}

	@GetMapping(value = "/subTechnologies/{id}")
	public BlogDropDownDTO getSubTechnologies(@PathVariable String id) {
		LOGGER.debug("Fetching SubTechnologies for dropdown id: {}", id);
		return dropDownService.getAllSubTechnologies(id);
	}

}
