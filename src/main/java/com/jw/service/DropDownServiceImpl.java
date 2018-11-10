package com.jw.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jw.dto.BlogDropDownDTO;
import com.jw.exception.DataAccessNotFoundException;
import com.jw.model.TechInfo;
import com.jw.repository.DropDownRepository;

@Service
public class DropDownServiceImpl implements DropDownService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DropDownServiceImpl.class);

	@Autowired
	private DropDownRepository dropDownRepository;

	@Override
	public List<BlogDropDownDTO> getAllTechnologies() {
		List<TechInfo> techInfoList = null;
		List<BlogDropDownDTO> blogDDList = null;
		BlogDropDownDTO blogDD = null;

		LOGGER.debug("Blog technologies fetching");
		techInfoList = dropDownRepository.findAllDropDownTechnologies();
		if (techInfoList != null && !techInfoList.isEmpty() && techInfoList.size() > 0) {
			blogDDList = new ArrayList<BlogDropDownDTO>();
			for (TechInfo techInfo : techInfoList) {
				blogDD = new BlogDropDownDTO();
				blogDD.setId(techInfo.getId());
				blogDD.setBlog(techInfo.getBlog());
				blogDDList.add(blogDD);
			}
		} else {
			LOGGER.error("Fetching Blog technologies failed");
			throw new DataAccessNotFoundException("ks1001", "Blog Technologies not found, Please try again");
		}
		return blogDDList;
	}

	@Override
	public BlogDropDownDTO getAllSubTechnologies(String technologyId) {
		TechInfo techInfo = null;
		BlogDropDownDTO blogDD = null;

		LOGGER.debug("Fetching Sub Technologies list for dropdown");
		techInfo = dropDownRepository.findAllDropDownSubTechs(technologyId);
		if (techInfo != null) {
			blogDD = new BlogDropDownDTO();
			blogDD.setId(techInfo.getId());
			blogDD.setSubTechs(techInfo.getSubTechs());
		} else {
			LOGGER.error("Fetching Blog Sub Technologies failed");
			throw new DataAccessNotFoundException("ks1001", "Blog Sub Technologies not found, Please try again");
		}
		return blogDD;
	}

}
