package com.jw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jw.dto.TechInfoDTO;
import com.jw.exception.DataAccessNotFoundException;
import com.jw.exception.InvalidInputDataException;
import com.jw.model.TechInfo;
import com.jw.repository.TechInfoRepository;
import com.jw.util.FilePathUtil;

@Service
public class TechInfoServiceImpl implements TechInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TechInfoServiceImpl.class);
	@Autowired
	private TechInfoRepository techInfoRepository;
	@Autowired
	private FilePathUtil filePathUtil;

	@Override
	public TechInfoDTO save(TechInfo techInfo) {
		TechInfoDTO techInfoDto = null;
		LOGGER.debug("Saving Technology Info with data :" + techInfo);
		techInfo.setBlogIconName(filePathUtil.getOriginalImageName(techInfo.getBlogIconName()));
		techInfo = techInfoRepository.save(techInfo);
		techInfoDto = new TechInfoDTO();
		techInfoDto.setId(techInfo.getId());
		techInfoDto.setBlog(techInfo.getBlog());
		techInfoDto.setBlogIconName(techInfo.getBlogIconName());
		techInfoDto.setSubTechs(techInfo.getSubTechs());
		return techInfoDto;
	}

	@Override
	public TechInfoDTO get(String id) {
		TechInfoDTO techInfoDto = null;
		TechInfo techInfo = null;
		Optional<TechInfo> optional = null;

		try {
			LOGGER.info("Technology Info fetching with id :" + id);
			optional = techInfoRepository.findById(id);
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Invalid input for fetching Technology Info from repository with id :" + id);
			throw new InvalidInputDataException("ks1105", "Invalid Input data");
		}
		if (optional.isPresent()) {
			techInfoDto = new TechInfoDTO();
			techInfo = optional.get();
			techInfoDto.setId(techInfo.getId());
			techInfoDto.setBlog(techInfo.getBlog());
			techInfoDto.setBlogIconName(techInfo.getBlogIconName());
			techInfoDto.setShortNote(techInfo.getShortNote());
			techInfoDto.setSubTechs(techInfo.getSubTechs());
			return techInfoDto;
		} else
			LOGGER.error("Getting Technology Info failed with id " + id);
		throw new DataAccessNotFoundException("ks1001", "Technology Info not found with id " + id);
	}

	@Override
	public List<TechInfoDTO> getAll() {
		List<TechInfo> techInfoList = null;
		TechInfoDTO techInfoDTO = null;
		List<TechInfoDTO> techInfoDtoList = null;
		LOGGER.debug("Getting all Technology Info");
		techInfoList = techInfoRepository.findAll();
		techInfoDtoList = new ArrayList<TechInfoDTO>();
		for (TechInfo info : techInfoList) {
			techInfoDTO = new TechInfoDTO();
			techInfoDTO.setId(info.getId());
			techInfoDTO.setBlog(info.getBlog());
			techInfoDTO.setBlogIconName(info.getBlogIconName());
			techInfoDtoList.add(techInfoDTO);
		}
		return techInfoDtoList;
	}

	@Override
	public TechInfo update(TechInfo techInfo) {
		LOGGER.debug("Updating Technology Info with TechInfo " + techInfo);
		return techInfoRepository.insert(techInfo);
	}

	@Override
	public void deleteTechInfo(String id) {
		LOGGER.debug("Technology Info deleted with id " + id);
		techInfoRepository.deleteById(id);
	}

}
