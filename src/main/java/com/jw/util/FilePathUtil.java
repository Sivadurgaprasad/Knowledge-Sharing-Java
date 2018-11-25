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
		return rootPath + "temp" + separator + UUID.randomUUID().toString() + separator;
	}

	public String imgBlogDir() {
		return dataFormatConverter.getGeniricWritePath() + "blog" + separator;
	}

	public String imgTechInfoDir() {
		return dataFormatConverter.getGeniricWritePath() + "technology" + separator;
	}

	public String getOriginalImageName(String path) {
		String[] tokens = null;
		tokens = path.split(Pattern.quote(File.separator));
		return tokens[tokens.length - 1];
	}

	public String getTempUUID(String path) {
		String[] tokens = null;

		tokens = path.split(Pattern.quote(File.separator));
		return tokens[tokens.length - 2];
	}

}
