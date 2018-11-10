package com.jw.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jw.exception.DataAccessNotFoundException;
import com.jw.exception.InvalidDataAccessException;
import com.jw.exception.InvalidInputDataException;
import com.jw.model.Blog;
import com.jw.model.BlogDetails;
import com.jw.repository.BlogRepository;

@Service
public class BlogServiceImpl implements BlogService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogServiceImpl.class);

	@Autowired
	private BlogRepository homeRepository;

	@Autowired
	private ImageManagement imageManagement;

	@Override
	public Blog saveBlog(Blog blog) {
		Blog blogResponse = null;
		LOGGER.info(
				"Uploading Images from Temp directory, Set Image name to Blog and Delete that image from Temp Directory.");
		imageManagement.uploadBlogImageAndDeleteFromTemp(blog);
		LOGGER.info("New Blog savigng with blog " + blog);
		blogResponse = homeRepository.insert(blog);
		if (blogResponse != null) {
			LOGGER.error("Blog not saved in mongoDB due to it return null {}", blog);
			throw new InvalidDataAccessException("ks1108",
					"Blog data not available for " + blog.getTech() + " and subTechnology " + blog.getSubTechs().get(0).getSubTech());
		}
		return blogResponse;
	}

	@Override
	public Blog getBlog(String subTech) {
		Blog blog = null;
		try {
			LOGGER.debug("Blog fetching with Blog Name :" + subTech);
			blog = homeRepository.findBySubTech(subTech);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Invalid input for fetching Blog from repository with id :" + subTech);
			throw new InvalidInputDataException("ks1105", "Invalid Input data");
		}
		if (blog != null)
			return blog;
		else
			LOGGER.error("Getting blog failed with id " + subTech);
		throw new DataAccessNotFoundException("ks1001", "Blog data not found with id " + subTech);
	}

	@Override
	public List<Blog> getAllBlogs() {
		LOGGER.debug("Getting all Blogs");
		return homeRepository.findAll();
	}

	@Override
	public Blog updateBlog(Blog blog) {
		Blog blogResponse = null;
		LOGGER.info(
				"Uploading Images from Temp directory, Set Image name to Blog and Delete that image from Temp Directory.");
		imageManagement.uploadBlogImageAndDeleteFromTemp(blog);
		LOGGER.debug("Updating blog with blog " + blog);
		blogResponse = homeRepository.save(blog);
		if (blogResponse != null) {
			LOGGER.error("Blog not saved in mongoDB due to it return null {}", blog);
			throw new InvalidDataAccessException("ks1108",
					"Blog data not available for " + blog.getTech() + " and subTechnology " + blog.getSubTechs().get(0).getSubTech());
		}
		if (LOGGER.isDebugEnabled())
			LOGGER.debug("Blog updated Successfully with {}", blog);
		return blogResponse;
	}

	@Override
	public void remove(String id) {
		homeRepository.deleteById(id);
	}

	@Override
	public Map<String, List<String>> getAllBlogTechAndSubTechnologies() {
		List<Blog> blogs = null;
		List<String> blogDetail = null;
		Map<String, List<String>> blogDetails = null;

		blogs = homeRepository.findTechAndSubTechnologies();
		blogDetails = new HashMap<>();
		for (Blog blog : blogs) {
			if (blogDetails.containsKey(blog.getTech())) {
				blogDetail = blogDetails.get(blog.getTech());
				blogDetail.add(blog.getSubTech());
				blogDetails.put(blog.getTech(), blogDetail);
			} else {
				blogDetail = new ArrayList<>();
				blogDetail.add(blog.getSubTech());
				blogDetails.put(blog.getTech(), blogDetail);
			}
		}
		return null;
	}

}
