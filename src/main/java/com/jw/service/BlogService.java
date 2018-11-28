package com.jw.service;

import java.util.List;
import java.util.Map;

import com.jw.model.BlogModel;

public interface BlogService {

	public BlogModel saveBlog(BlogModel blog);

	public BlogModel getBlog(String subTech);

	public List<BlogModel> getAllBlogs();

	public BlogModel updateBlog(BlogModel blog);

	public void remove(String id);

	public Map<String, List<String>> getAllBlogTechAndSubTechnologies();

}
