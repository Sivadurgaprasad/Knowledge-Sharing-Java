package com.jw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jw.dto.TechInfoDTO;
import com.jw.exception.DataAccessNotFoundException;
import com.jw.exception.InvalidInputDataException;
import com.jw.model.BlogModel;
import com.jw.model.SubTechDTO;
import com.jw.model.TechInfoModel;
import com.jw.repository.BlogRepository;
import com.jw.util.ErrorCode;
import com.jw.util.FilePathUtil;
import com.jw.util.KSConstants;

@Service
public class TechInfoServiceImpl implements TechInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TechInfoServiceImpl.class);
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private FilePathUtil filePathUtil;

	@Override
	public TechInfoDTO save(TechInfoModel techInfo) {
		TechInfoDTO techInfoDto = null;
		BlogModel blog = null;
		List<SubTechDTO> subTechs = null;
		SubTechDTO subTechDto = null;
		LOGGER.info("Saving Technology Info with data : {}", techInfo);
		techInfo.setBlogIconName(filePathUtil.getOriginalImageName(techInfo.getBlogIconName()));
		blog = new BlogModel();
		subTechs = new ArrayList<>();
		blog.setBlog(techInfo.getBlog());
		blog.setShortNote(techInfo.getShortNote());
		blog.setBlogIconName(techInfo.getBlogIconName());
		for (String subTech : techInfo.getSubTechs()) {
			subTechDto = new SubTechDTO();
			subTechDto.setSubTech(subTech);
			subTechs.add(subTechDto);
		}
		blog.setSubTechs(subTechs);
		blog = blogRepository.save(blog);
		techInfoDto = new TechInfoDTO();
		techInfoDto.setBlog(blog.getBlog());
		techInfoDto.setBlogIconName(blog.getBlogIconName());
		LOGGER.info("Sub Technologies iterating from SubTechDtos {}", blog.getSubTechs());
		techInfoDto.setSubTechs(blog.getSubTechs().stream().map(SubTechDTO::getSubTech)
				.collect(Collectors.toList()));
		return techInfoDto;
	}

	@Override
	public TechInfoDTO get(String id) {
		TechInfoDTO techInfoDto = null;
		BlogModel blog = null;
		Optional<BlogModel> optional = null;

		try {
			LOGGER.info("Technology Info fetching with id :{}", id);
			optional = blogRepository.findById(id);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Invalid input for fetching Technology Info from repository with id :{}", id);
			throw new InvalidInputDataException(ErrorCode.KS1105.toString(), KSConstants.KS1105);
		}
		if (optional.isPresent()) {
			techInfoDto = new TechInfoDTO();
			blog = optional.get();
			techInfoDto.setId(blog.getId());
			techInfoDto.setBlog(blog.getBlog());
			techInfoDto.setBlogIconName(blog.getBlogIconName());
			techInfoDto.setShortNote(blog.getShortNote());
			techInfoDto.setSubTechs(
					blog.getSubTechs().stream().map(SubTechDTO::getSubTech).collect(Collectors.toList()));
			return techInfoDto;
		} else {
			LOGGER.error("Getting Technology Info failed with id {}", id);
			throw new DataAccessNotFoundException(ErrorCode.KS1001.toString(), KSConstants.KS1001.concat(id));
		}
	}

	@Override
	public List<TechInfoDTO> getAll() {
		List<BlogModel> blogList = null;
		TechInfoDTO techInfoDTO = null;
		List<TechInfoDTO> techInfoDtoList = null;
		LOGGER.debug("Getting all Technology Info");
		blogList = blogRepository.findAll();
		techInfoDtoList = new ArrayList<>();
		for (BlogModel blog : blogList) {
			techInfoDTO = new TechInfoDTO();
			techInfoDTO.setId(blog.getId());
			techInfoDTO.setBlog(blog.getBlog());
			techInfoDTO.setBlogIconName(blog.getBlogIconName());
			techInfoDtoList.add(techInfoDTO);
		}
		return techInfoDtoList;
	}

	@Override
	public BlogModel update(TechInfoModel techInfo) {
		BlogModel blog = null;
		LOGGER.debug("Updating Technology Info with TechInfo {}", techInfo);
		blog = new BlogModel();
		blog.setId(techInfo.getId());
		return blogRepository.save(blog);
	}

	@Override
	public void deleteTechInfo(String id) {
		LOGGER.debug("Technology Info deleted with id {}", id);
		blogRepository.deleteById(id);
	}

}
