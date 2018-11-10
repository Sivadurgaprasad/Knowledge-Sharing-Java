package com.jw.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jw.exception.FileIOException;
import com.jw.exception.InvalidInputDataException;
import com.jw.exception.NullDirectoryException;
import com.jw.model.Blog;
import com.jw.model.SubTech;
import com.jw.model.TechInfo;
import com.jw.util.FilePathUtil;

@Service
public class ImageManagementImpl implements ImageManagement {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageManagementImpl.class);

	@Autowired
	private FilePathUtil filePathUtil;

	@Override
	public String upload(MultipartFile multipartFile, String uploadLocation) {
		Path filePath = null;

		try {
			filePath = Files.createDirectory(Paths.get(uploadLocation, UUID.randomUUID().toString()));
		} catch (IOException ex) {
			LOGGER.error("Creating Directory failed, please check the file name or directory");
			throw new NullDirectoryException("ks1100", "Directory Creation failed, Please check the paht of the file "
					+ multipartFile.getOriginalFilename());
		}
		if (!multipartFile.isEmpty() && Files.isWritable(filePath)) {
			try {
				Files.write(filePath, multipartFile.getBytes());
			} catch (IOException e) {
				LOGGER.error("Failed to writing file in path :" + filePath.toString() + " and size is "
						+ multipartFile.getSize() + " " + e.getMessage());
				throw new FileIOException("ks1101", "File writing failed, please try again");
			}
		}
		return filePath.toString();
	}

	@Override
	public byte[] downloadFile(String uploadLocation) {
		byte[] fileBytes = null;
		try {
			if (uploadLocation != null) {
				fileBytes = Files.readAllBytes(Paths.get(uploadLocation));
			}
		} catch (IOException e) {
			LOGGER.error("Reading file failed of path " + uploadLocation + " and exception " + e.getMessage());
			throw new NullDirectoryException("ks1102", "File reading failed or file path null");
		}
		return fileBytes;
	}

	@Override
	public boolean deleteFile(String file) {
		try {
			return Files.deleteIfExists(Paths.get(file));
		} catch (IOException e) {
			LOGGER.error("Failed file deleting of " + file);
			throw new NullDirectoryException("ks1103", "Image deleting failed or file path null");
		}
	}

	@Override
	public String writeImage(MultipartFile multipartFile, String originalFileName, String imgReadPath,
			String imgWritePath) {
		File fileDir = null;
		Path path = null;
		BufferedImage bufferedImage = null;
		boolean isSave = false;

		try {
			// create file with temp dir if condition true else create in normal directory
			fileDir = new File(imgWritePath);
			fileDir.mkdirs();
			if (fileDir.exists()) {
				if (imgReadPath != null) {
					bufferedImage = ImageIO.read(new File(imgReadPath));
				} else {
					bufferedImage = ImageIO.read(multipartFile.getInputStream());
				}
				isSave = ImageIO.write(bufferedImage, "png",
						new File(fileDir.toString() + File.separator + originalFileName));
				// If Image not save it throw exception.
				if (!isSave)
					throw new FileIOException("ks1101", "Image writing failed or file path null");
				path = Paths.get(fileDir.toString(), originalFileName);
			}

		} catch (IOException e) {
			LOGGER.error("Image writing failed of " + fileDir + File.separator + originalFileName);
			throw new FileIOException("ks1101", "Image writing failed or file path null");
		}

		return path.toString();
	}

	@Override
	public List<String> writeMultipleImages(Map<String, String> imgPathOriginalName, String imgWritePath) {
		List<String> uploadedPaths = null;

		uploadedPaths = new ArrayList<String>();
		for (Map.Entry<String, String> pathName : imgPathOriginalName.entrySet()) {
			uploadedPaths.add(writeImage(null, pathName.getValue(), pathName.getKey(), imgWritePath));
		}
		return uploadedPaths;
	}

	@Override
	public BufferedImage readImage(String uploadLocation) {
		File file = null;
		BufferedImage bufferedImage = null;

		try {
			file = new File(uploadLocation);
			if (file != null) {
				bufferedImage = ImageIO.read(file);
				bufferedImage.flush();
			}
		} catch (IOException e) {
			LOGGER.error("Image reading failed or input null" + e.getMessage());
			throw new FileIOException("ks1102", "Image reading failed or file path null");
		}

		return bufferedImage;
	}

	@Override
	public List<BufferedImage> readMultipleImages(List<String> uploadedLocations) {
		List<BufferedImage> readImages = null;

		readImages = new ArrayList<>();
		for (String path : uploadedLocations) {
			readImages.add(readImage(path));
		}
		return readImages;
	}

	@Override
	public boolean deleteImage(String imageName) {
		try {
			LOGGER.debug("Deleting uploaded File in " + imageName);
			return Files.deleteIfExists(Paths.get(imageName));
		} catch (IOException e) {
			LOGGER.error("Image deleting failed");
			throw new NullDirectoryException("ks1103", "Image deleting failed or file path null");
		}
	}

	@Override
	public void uploadBlogImageAndDeleteFromTemp(Blog blog) {
		Map<String, String> archePathOriginalName = null;
		Map<String, String> scenarioPathOriginalName = null;
		Map<String, String> programPathOriginalName = null;
		Map<String, String> outputPathOriginalName = null;
		SubTech subTech = null;

		if (blog.getSubTechs() != null && !blog.getSubTechs().isEmpty() && blog.getSubTechs().size() > 0)
			subTech = blog.getSubTechs().get(0);
		else {
			throw new InvalidInputDataException("ks1002", "Invalid Input data.{}" + blog);
		}

		if (subTech.getArcheUploadImagePaths() != null && !subTech.getArcheUploadImagePaths().isEmpty()) {
			LOGGER.debug("Archetecture images uploading and uploaded images deleting those are "
					+ subTech.getArcheUploadImagePaths() + " and " + subTech.getArcheDeleteImagePaths());
			archePathOriginalName = new HashMap<>();
			for (String uploadPath : subTech.getArcheUploadImagePaths()) {
				archePathOriginalName.put(uploadPath, filePathUtil.getOriginalImageName(uploadPath));
			}
			subTech.setArcheImages(writeMultipleImages(archePathOriginalName, filePathUtil.imgBlogDir()));
			subTech.getArcheDeleteImagePaths().forEach(path -> deleteImage(path));
		}

		if (subTech.getScenarioUploadImagePaths() != null && !subTech.getScenarioUploadImagePaths().isEmpty()) {
			LOGGER.debug("Scenario images uploading and uploaded images deleting those are "
					+ subTech.getScenarioUploadImagePaths() + " and " + subTech.getScenarioDeleteImagePaths());
			scenarioPathOriginalName = new HashMap<>();
			for (String path : subTech.getScenarioUploadImagePaths()) {
				scenarioPathOriginalName.put(path, filePathUtil.getOriginalImageName(path));
			}
			subTech.setScenarioImages(writeMultipleImages(scenarioPathOriginalName, filePathUtil.imgBlogDir()));
			subTech.getScenarioDeleteImagePaths().forEach(path -> deleteImage(path));
		}

		if (subTech.getProgramUploadImagePaths() != null && subTech.getProgramUploadImagePaths().isEmpty()) {
			LOGGER.debug("Program images uploading and uploaded images deleting those are "
					+ subTech.getProgramUploadImagePaths() + " and " + subTech.getProgramDeleteImagePaths());
			programPathOriginalName = new HashMap<>();
			for (String path : subTech.getProgramUploadImagePaths()) {
				programPathOriginalName.put(path, filePathUtil.getOriginalImageName(path));
			}
			subTech.setProgramImages(writeMultipleImages(programPathOriginalName, filePathUtil.imgBlogDir()));
			subTech.getProgramDeleteImagePaths().forEach(path -> deleteImage(path));
		}

		if (subTech.getOutputUploadImagePaths() != null && !subTech.getOutputUploadImagePaths().isEmpty()) {
			LOGGER.debug("Output images uploading and uploaded images deleting those are "
					+ subTech.getOutputUploadImagePaths() + " and " + subTech.getOutputDeleteImagePaths());
			outputPathOriginalName = new HashMap<>();
			for (String path : subTech.getOutputUploadImagePaths()) {
				outputPathOriginalName.put(path, filePathUtil.getOriginalImageName(path));
			}
			subTech.setOutputImages(writeMultipleImages(outputPathOriginalName, filePathUtil.imgBlogDir()));
			subTech.getOutputDeleteImagePaths().forEach(path -> deleteImage(path));
		}
	}

	@Override
	public void uploadTechIconImageAndDeleteFromTemp(TechInfo techInfo) {
		// get the uploaded image from directory and delete it and save that image with
		// Corresponding data
		if (techInfo.getUploadImagePath() != null) {
			techInfo.setBlogIconName(writeImage(null, filePathUtil.getOriginalImageName(techInfo.getUploadImagePath()),
					techInfo.getUploadImagePath(), filePathUtil.imgTechInfoDir()));
			techInfo.getDeleteImageUrlList().forEach(path -> {
				deleteImage(path);
			});
		} else
			throw new InvalidInputDataException("ks1002", "Blog icon id is null, please upload Blog icon first");
	}

}