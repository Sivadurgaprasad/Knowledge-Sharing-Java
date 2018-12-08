package com.jw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jw.dto.BlogDropDownDTO;
import com.jw.exception.DataAccessNotFoundException;
import com.jw.model.BlogModel;
import com.jw.model.SubTechDTO;
import com.jw.repository.DropDownRepository;
import com.jw.util.ErrorCode;
import com.jw.util.KSConstants;

@Service
public class DropDownServiceImpl implements DropDownService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DropDownServiceImpl.class);

	@Autowired
	private DropDownRepository dropDownRepository;

	@Override
	public List<BlogDropDownDTO> getAllTechnologies() {

		List<BlogDropDownDTO> blogDDList = null;
		List<BlogModel> blogs = null;
		BlogDropDownDTO blogDD = null;

		blogs = dropDownRepository.findAllDropDownTechnologies();
		LOGGER.info("Blog technologies fetched list {}", blogs);
		if (blogs != null && !blogs.isEmpty()) {
			blogDDList = new ArrayList<>();
			for (BlogModel blog : blogs) {
				blogDD = new BlogDropDownDTO();
				blogDD.setId(blog.getId());
				blogDD.setBlog(blog.getBlog());
				blogDDList.add(blogDD);
			}
		} else {
			LOGGER.error("Fetching Blog technologies failed");
			throw new DataAccessNotFoundException(ErrorCode.KS1001.toString(), KSConstants.KS1001);
		}
		return blogDDList;
	}

	@Override
	public BlogDropDownDTO getAllSubTechnologies(String id) {
		
		BlogDropDownDTO blogDD = null;
		BlogModel blog = null;

		blog = dropDownRepository.findAllDropDownSubTechs(id.trim());
		LOGGER.info("Fetched Sub Technologies list for dropdown {}", blog);
		if (blog != null) {
			blogDD = new BlogDropDownDTO();
			blogDD.setId(blog.getId());
			blogDD.setSubTechs(blog.getSubTechs() != null
					? blog.getSubTechs().stream().map(SubTechDTO::getSubTech).collect(Collectors.toList())
					: null);
		} else {
			LOGGER.error("Fetching Blog Sub Technologies failed");
			throw new DataAccessNotFoundException(ErrorCode.KS1001.toString(), KSConstants.KS1001);
		}
		return blogDD;
	}

}
