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
import com.jw.model.SubTechDTO;
import com.jw.model.TechInfo;
import com.jw.util.ErrorCode;
import com.jw.util.FilePathUtil;
import com.jw.util.KSConstants;

@Service
public class ImageManagementImpl implements ImageManagement {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageManagementImpl.class);

	@Autowired
	private FilePathUtil filePathUtil;

	@Autowired
	private ImageManagement imageManagement;

	@Override
	public String upload(MultipartFile multipartFile, String uploadLocation) {
		Path filePath = null;

		try {
			filePath = Files.createDirectory(Paths.get(uploadLocation, UUID.randomUUID().toString()));
		} catch (IOException ex) {
			LOGGER.error("Creating Directory failed, please check the file name or directory");
			throw new NullDirectoryException(ErrorCode.KS1100.toString(),
					KSConstants.KS1100.concat(multipartFile.getOriginalFilename()));
		}
		if (!multipartFile.isEmpty() && Files.isWritable(filePath)) {
			try {
				Files.write(filePath, multipartFile.getBytes());
			} catch (IOException e) {
				LOGGER.error("Failed to writing file in path : {} and size is {} {}", filePath, multipartFile.getSize(),
						e.getMessage());
				throw new FileIOException(ErrorCode.KS1101.toString(), KSConstants.KS1101);
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
			LOGGER.error("Reading file failed of path {} and exception {}", uploadLocation, e.getMessage());
			throw new NullDirectoryException(ErrorCode.KS1102.toString(), KSConstants.KS1102);
		}
		return fileBytes;
	}

	@Override
	public boolean deleteFile(String file) {
		try {
			return Files.deleteIfExists(Paths.get(file));
		} catch (IOException e) {
			LOGGER.error("Failed file deleting of {}", file);
			throw new NullDirectoryException(ErrorCode.KS1103.toString(), KSConstants.KS1103);
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
					throw new FileIOException(ErrorCode.KS1101.toString(), KSConstants.KS1101);
				path = Paths.get(fileDir.toString(), originalFileName);
			} else {
				LOGGER.error("Invalid file directory is {}", fileDir);
				throw new NullDirectoryException(ErrorCode.KS1101.toString(), KSConstants.KS1101);
			}

		} catch (IOException e) {
			LOGGER.error("Image writing failed of {}", fileDir + File.separator + originalFileName);
			throw new FileIOException("ks1101", "Image writing failed or file path null");
		}

		return path.toString();
	}

	@Override
	public List<String> writeMultipleImages(Map<String, String> imgPathOriginalName, String imgWritePath) {
		List<String> uploadedPaths = null;

		uploadedPaths = new ArrayList<>();
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
			if (uploadLocation != null) {
				file = new File(uploadLocation);
				bufferedImage = ImageIO.read(file);
				bufferedImage.flush();
			}
		} catch (IOException e) {
			LOGGER.error("Image reading failed or input null {}", e.getMessage());
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
			LOGGER.debug("Deleting uploaded File in {}", imageName);
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
		SubTechDTO subTech = null;

		if (blog.getSubTechs() != null && !blog.getSubTechs().isEmpty())
			subTech = blog.getSubTechs().get(0);
		else {
			throw new InvalidInputDataException("ks1002", "Invalid Input data.{}" + blog);
		}

		if (subTech.getArcheUploadImagePaths() != null && !subTech.getArcheUploadImagePaths().isEmpty()) {
			LOGGER.debug("Archetecture images uploading and uploaded images deleting those are {} and {}",
					subTech.getArcheUploadImagePaths(), subTech.getArcheDeleteImagePaths());
			// Adding all Archetecture upload image paths to a Map
			archePathOriginalName = iterateImagePaths(subTech.getArcheUploadImagePaths());
			subTech.setArcheImages(writeMultipleImages(archePathOriginalName, filePathUtil.imgBlogDir()));
			subTech.getArcheDeleteImagePaths().forEach(path -> imageManagement.deleteImage(path));
		}

		if (subTech.getScenarioUploadImagePaths() != null && !subTech.getScenarioUploadImagePaths().isEmpty()) {
			LOGGER.debug("Scenario images uploading and uploaded images deleting those are {}  and {}",
					subTech.getScenarioUploadImagePaths(), subTech.getScenarioDeleteImagePaths());
			// Adding all Scenario upload image paths to a Map
			scenarioPathOriginalName = iterateImagePaths(subTech.getScenarioUploadImagePaths());
			subTech.setScenarioImages(writeMultipleImages(scenarioPathOriginalName, filePathUtil.imgBlogDir()));
			subTech.getScenarioDeleteImagePaths().forEach(path -> imageManagement.deleteImage(path));
		}

		if (subTech.getProgramUploadImagePaths() != null && subTech.getProgramUploadImagePaths().isEmpty()) {
			LOGGER.debug("Program images uploading and uploaded images deleting those are "
					+ subTech.getProgramUploadImagePaths() + " and " + subTech.getProgramDeleteImagePaths());
			// Adding all Program upload image paths to a Map
			programPathOriginalName = iterateImagePaths(subTech.getProgramUploadImagePaths());
			subTech.setProgramImages(writeMultipleImages(programPathOriginalName, filePathUtil.imgBlogDir()));
			subTech.getProgramDeleteImagePaths().forEach(path -> imageManagement.deleteImage(path));
		}

		if (subTech.getOutputUploadImagePaths() != null && !subTech.getOutputUploadImagePaths().isEmpty()) {
			LOGGER.debug("Output images uploading and uploaded images deleting those are "
					+ subTech.getOutputUploadImagePaths() + " and " + subTech.getOutputDeleteImagePaths());
			// Adding all Output upload image paths to a Map
			outputPathOriginalName = iterateImagePaths(subTech.getOutputUploadImagePaths());
			subTech.setOutputImages(writeMultipleImages(outputPathOriginalName, filePathUtil.imgBlogDir()));
			subTech.getOutputDeleteImagePaths().forEach(path -> imageManagement.deleteImage(path));
		}
	}

	/*
	 * for modularizing the uploadBlogImageAndDeleteFromTemp() method and code
	 * reusabulity.
	 */
	private Map<String, String> iterateImagePaths(List<String> imagePaths) {
		Map<String, String> imagePathOriginalName = null;

		imagePathOriginalName = new HashMap<>();
		for (String path : imagePaths) {
			imagePathOriginalName.put(path, filePathUtil.getOriginalImageName(path));
		}
		return imagePathOriginalName;
	}

	@Override
	public void uploadTechIconImageAndDeleteFromTemp(TechInfo techInfo) {
		// get the uploaded image from directory and delete it and save that image with
		// Corresponding data
		if (techInfo.getUploadImagePath() != null) {
			techInfo.setBlogIconName(writeImage(null, filePathUtil.getOriginalImageName(techInfo.getUploadImagePath()),
					techInfo.getUploadImagePath(), filePathUtil.imgTechInfoDir()));
			techInfo.getDeleteImageUrlList().forEach(path -> imageManagement.deleteImage(path));
		} else {
			throw new InvalidInputDataException("ks1002", "Blog icon id is null, please upload Blog icon first");
		}
	}

}
