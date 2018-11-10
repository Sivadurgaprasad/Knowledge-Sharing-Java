package com.jw.util;

import java.io.File;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilePathUtil {

	@Autowired
	private DataFormatConverter dataFormatConverter;

	private String separator = null;
	private String rootPath = null;

	public FilePathUtil() {
		separator = File.separator;
		rootPath = System.getProperty("user.dir") + separator + "src" + separator + "main" + separator + "resources"
				+ separator + "knowledge-sharing" + separator + "images" + separator;
	}

	public String imgtempDir() {
		String tempDir = rootPath + "temp" + separator + UUID.randomUUID().toString() + separator;
		return tempDir;
	}

	public String imgBlogDir() {
		String imgDir = dataFormatConverter.getGeniricWritePath() + "blog" + separator;
		return imgDir;
	}

	public String imgTechInfoDir() {
		String imgDir = dataFormatConverter.getGeniricWritePath() + "technology" + separator;
		return imgDir;
	}

	public String getOriginalImageName(String path) {
		String[] tokens = null;
		tokens = path.split(Pattern.quote(File.separator));
		return tokens[tokens.length - 1];
	}

	public String getTempUUID(String path) {
		String[] tokens = null;

		tokens = path.split(Pattern.quote(File.separator));
		String p = tokens[tokens.length - 2];
		return p;
	}

}
