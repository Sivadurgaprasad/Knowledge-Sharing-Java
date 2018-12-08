package com.jw.service;

import java.util.List;

import com.jw.dto.TechInfoDTO;
import com.jw.model.BlogModel;
import com.jw.model.TechInfoModel;

public interface TechInfoService {

	public TechInfoDTO save(TechInfoModel techInfo);

	public TechInfoDTO get(String id);

	public List<TechInfoDTO> getAll();

	public BlogModel update(TechInfoModel techInfo);

	public void deleteTechInfo(String id);

}
