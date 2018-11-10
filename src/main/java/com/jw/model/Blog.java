package com.jw.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "Blog")
@Setter
@Getter
@ToString
public class Blog extends BaseModel {
	private static final long serialVersionUID = -1651727884634205326L;

	@Id
	private String id;
	@NotNull
	@NotEmpty
	private String tech;
	@NotNull
	@NotEmpty
	private List<SubTech> subTechs;

}
