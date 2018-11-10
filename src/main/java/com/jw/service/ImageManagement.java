package com.jw.service;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jw.exception.FileIOException;
import com.jw.model.Blog;
import com.jw.model.TechInfo;

public interface ImageManagement {

	public String upload(MultipartFile multipartFile, String uploadLocation);

	public byte[] downloadFile(String uploadLocation);

	public boolean deleteFile(String fileName);

	/**
	 * we can write the images using this method. Don't make both imageStream and
	 * imgReadPaht null. If both null it throws FileIOException.
	 * 
	 * @param imageStream      if it null, images read from imgReadPath.
	 * @param originalFileName
	 * @param imgReadPath      if it null image read from imageStream.
	 * @param imgWritePath
	 * @return
	 * @throws FileIOException if input byte[] or paths are null
	 */
	public String writeImage(MultipartFile multipartFile, String originalFileName, String imgReadPath, String imgWritePath);

	public List<String> writeMultipleImages(Map<String, String> imgPathOriginalName, String imgWritePath);

	public BufferedImage readImage(String uploadLocation);

	public List<BufferedImage> readMultipleImages(List<String> uploadedLocations);

	public boolean deleteImage(String imageName);

	/**
	 * Upload Blog images from temp folder, set uploading paths to Blog and delete those images in temp.
	 * @param blog
	 */
	void uploadBlogImageAndDeleteFromTemp(Blog blog);

	/**
	 * Upload Technology Icon image from temp folder, set uploading path to TechnologyInfo and delete that image in temp.
	 * @param techInfo
	 */
	void uploadTechIconImageAndDeleteFromTemp(TechInfo techInfo);

}
