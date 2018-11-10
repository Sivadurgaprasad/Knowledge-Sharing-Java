package com.jw.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonToPojoConverter<T> implements Converter<String, T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonToPojoConverter.class);
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public T convert(String source) {
		Class<T> response = null;
		T resp = null;
		try {
			System.out.println("request json :"+source);
			resp = (T) objectMapper.readValue(source, response);
		} catch (IOException e) {
			LOGGER.error("Json to " + resp + " conversion failed with json " + source);
		}
		return resp;
	}

}
