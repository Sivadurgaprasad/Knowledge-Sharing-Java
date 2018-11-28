package com.jw.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jw.dto.TechInfoDTO;
import com.jw.exception.InvalidInputDataException;
import com.jw.exception.ObjectNotFoundException;
import com.jw.model.BlogModel;
import com.jw.model.TechInfoModel;
import com.jw.service.BlogService;
import com.jw.service.ImageManagement;
import com.jw.service.TechInfoService;
import com.jw.util.ErrorCode;
import com.jw.util.FilePathUtil;
import com.jw.util.KSConstants;

@RestController
@Validated
@RequestMapping(value = "/blog")
public class BlogController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogController.class);

	@Autowired
	private BlogService blogService;
	@Autowired
	private TechInfoService techInfoService;
	@Autowired
	private ImageManagement imageManagement;
	@Autowired
	private FilePathUtil filePathUtil;

	/******************************************************************
	 *********************** Blog Information *************************
	 ******************************************************************/

	/**
	 * Saving Blog in mongoDB. If it has any errors it will throw InvalidInputDataException.
	 * @param blog
	 * @param errors
	 * @return
	 */
	@PostMapping(value = "/save")
	public String save(@RequestBody @Valid BlogModel blog, Errors errors) {

		if (errors.hasErrors()) {
			LOGGER.error("Saving blog failed with blog {} and got these errors {}", blog, errors);
			throw new InvalidInputDataException(ErrorCode.KS1002.toString(),
					KSConstants.KS1002.concat(errors.toString()));
		}
		blog = blogService.saveBlog(blog);
		if (LOGGER.isInfoEnabled())
			LOGGER.info("Blog saved successfully with {}", blog);
		return blog.getTech() + " of " + blog.getSubTechs().get(0).getSubTech() + " Blog successfully Submitted.";
	}

	// Getting Single Blog details
	@GetMapping(value = "/details/{subTech}")
	public BlogModel get(@PathVariable String subTech) {
		LOGGER.info("Getting data with Blog Name : {}", subTech);
		return blogService.getBlog(subTech);
	}

	// Getting all Blogs
	@GetMapping(value = "/all")
	public List<BlogModel> getAll() {
		LOGGER.info("Getting all Blogs");
		return blogService.getAllBlogs();
	}

	// Updating Blog details
	@PutMapping(value = "/update")
	public BlogModel update(@RequestBody @Valid BlogModel blog, Errors errors) {

		if (errors.hasErrors()) {
			LOGGER.error("Updating blog failed with blog {} and got these errors {}", blog, errors);
			throw new InvalidInputDataException(ErrorCode.KS1002.toString(),
					KSConstants.KS1002.concat(errors.toString()));
		}
		LOGGER.debug("Blog updating with data :{}", blog);
		return blogService.updateBlog(blog);
	}

	// Deleting Blog details
	@DeleteMapping(value = "/delete/{id}")
	public String delete(@PathVariable String id) {
		LOGGER.info("Deleting blog with id {}", id);
		if (id != null)
			blogService.remove(id);
		else
			throw new InvalidInputDataException("ks1002", "Invalid input data " + id);
		return id + " Blog deleted successfully";
	}

	/******************************************************************
	 ***************** Technology Information *************************
	 ******************************************************************/

	/**
	 * Save Technology Info with Uploaded image Path.If it has any Errors it will throw InvalidInputDataException.
	 * 
	 * @param techInfo
	 * @param errors
	 * @return
	 */
	@PostMapping(value = "/saveTechInfo")
	public TechInfoDTO saveTechInfo(@RequestBody @Valid TechInfoModel techInfo, Errors errors) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Request received for BlogController.save() is starting with {}", techInfo);
		}
		// check errors
		if (errors.hasErrors()) {
			LOGGER.error("Saving Tehcnology Info failed with techInfo {} and got these errors {}", techInfo, errors);
			throw new InvalidInputDataException("ks1002", "Input data has errors. Those " + errors.toString());
		}
		LOGGER.info("Uploading Technology Icon from temp directory and deleting that image from temp directory");
		imageManagement.uploadTechIconImageAndDeleteFromTemp(techInfo);
		if (techInfo.getBlogIconName() != null && techInfo.getBlogIconName().length() > 0) {
			LOGGER.info("Saving Complete Technology Info with {}", techInfo);
			return techInfoService.save(techInfo);
		} else {
			throw new ObjectNotFoundException("ks1003", "Uploaded Blog Icon not available in context");
		}
	}

	/**
	 * Fetch Single Technology Information with id
	 * 
	 * @param id
	 * @return
	 */

	@GetMapping(value = "/techInfo/{id}")
	public TechInfoDTO getTechInfo(@PathVariable String id) {
		return techInfoService.get(id);
	}

	@GetMapping(value = "/allTechInfo")
	public List<TechInfoDTO> getAllTechInfo() {
		return techInfoService.getAll();
	}

	@DeleteMapping(value = "/deleteTech/{id}")
	public String deleteTechInfo(String id) {
		techInfoService.deleteTechInfo(id);
		return "Technology Deleted with id " + id;
	}

	/******************************************************************
	 *********************** Image Management *************************
	 ******************************************************************/

	/**
	 * Uploading images
	 * 
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/upload")
	public Map<String, String> upload(@RequestParam("uploadImage") MultipartFile multipartFile) {
		Map<String, String> result = null;
		String uploadImagePath = null;

		LOGGER.debug("Uploading Technology Icon of image {}", multipartFile.getOriginalFilename());
		// upload file in temporary directory
		uploadImagePath = imageManagement.writeImage(multipartFile, multipartFile.getOriginalFilename(), null,
				filePathUtil.imgtempDir());

		result = new HashMap<>();
		result.put("uploadImagePath", uploadImagePath);
		return result;
	}

}
