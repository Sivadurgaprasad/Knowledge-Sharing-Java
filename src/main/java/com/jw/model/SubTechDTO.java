package com.jw.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class SubTech {

	@Id
	private String id;
	@NotNull
	@NotEmpty
	private String subTech;
	@NotNull
	@NotEmpty
	private List<Definition> definitions;
	private List<Example> examples;
	private List<Importance> importances;
	private List<InOutput> inOutputs;
	private List<Limitation> limitations;
	private List<Archetecture> archetectures;
	// identifying saving image paths.
	@Transient
	private List<String> archeUploadImagePaths;
	private List<String> archeImages;
	// deleting multiple times uploaded and present saving paths.
	@Transient
	private List<String> archeDeleteImagePaths;
	@Transient
	private List<String> scenarioUploadImagePaths;
	private List<String> scenarioImages;
	@Transient
	private List<String> scenarioDeleteImagePaths;
	@Transient
	private List<String> programUploadImagePaths;
	private List<String> programImages;
	@Transient
	private List<String> programDeleteImagePaths;
	@Transient
	private List<String> outputUploadImagePaths;
	private List<String> outputImages;
	@Transient
	private List<String> outputDeleteImagePaths;
	private List<Need> needs;
	private List<Reference> references;
	private List<Scenario> scenarios;
}
