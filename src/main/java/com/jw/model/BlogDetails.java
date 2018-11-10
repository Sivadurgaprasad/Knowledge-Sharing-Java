package com.jw.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BlogDetails {

	@Id
	private String id;
	
}
