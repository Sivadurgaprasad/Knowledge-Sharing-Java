package com.jw.service;

import java.util.List;
import java.util.Map;

import com.jw.model.Blog;

public interface BlogService {

	public Blog saveBlog(Blog blog);

	public Blog getBlog(String subTech);

	public List<Blog> getAllBlogs();

	public Blog updateBlog(Blog blog);

	public void remove(String id);

	public Map<String, List<String>> getAllBlogTechAndSubTechnologies();

}
