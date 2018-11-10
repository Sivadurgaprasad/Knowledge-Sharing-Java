package com.jw.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataFormatConverter {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataFormatConverter.class);

	@Value("${imgage.write.dir}")
	private String imageWritePath = "F:/ubuntu/Angular/Java-Web/src/assets/";

	/**
	 * Convert BufferedImgage formate to byte[]. If BufferedImage is null it throw
	 * NullPointerException.
	 * 
	 * @param bufferedImage
	 * @return
	 */
	public byte[] convertBufferedImageToByteArray(BufferedImage bufferedImage) {
		WritableRaster writableRaster = null;
		DataBufferByte dataBufferByte = null;
		if (bufferedImage != null) {
			writableRaster = bufferedImage.getRaster();
			dataBufferByte = (DataBufferByte) writableRaster.getDataBuffer();
			LOGGER.debug("Data converted from BufferedImage to byte[]");
		} else
			throw new NullPointerException("BufferedImage data null while converting BufferedImage to byte[]");

		return dataBufferByte.getData();
	}

	public String getGeniricWritePath() {
		if (imageWritePath != null) {
			return imageWritePath.replaceAll("//", File.separator);
		} else
			throw new NullPointerException("Image write path not found");
	}

}
