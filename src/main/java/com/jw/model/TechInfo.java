package com.jw.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "TechInfo")
@Setter
@Getter
@ToString
public class TechInfo extends BaseModel {

	private static final long serialVersionUID = 4525465395605762084L;
	@Id
	private String id;
	@NotNull
	@NotEmpty
	private String blog;
	@NotNull
	@NotEmpty
	private String shortNote;
	@Transient
	private String uploadImagePath;
	@Transient
	private List<String> deleteImageUrlList;
	private List<SubTech> subTechs;
	private String blogIconName;
}
