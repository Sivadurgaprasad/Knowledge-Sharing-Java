package com.jw.service;

import java.util.List;

import com.jw.dto.TechInfoDTO;
import com.jw.model.Blog;
import com.jw.model.TechInfo;

public interface TechInfoService {

	public TechInfoDTO save(TechInfo techInfo);

	public TechInfoDTO get(String id);

	public List<TechInfoDTO> getAll();

	public Blog update(TechInfo techInfo);

	public void deleteTechInfo(String id);

}
