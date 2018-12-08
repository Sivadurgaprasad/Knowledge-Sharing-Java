package com.jw.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data()
public class SubTechDTO implements Serializable {

	private static final long serialVersionUID = -6177090526035009104L;
	@NotNull
	@NotEmpty
	private String subTech;
	@NotNull
	@NotEmpty
	private List<DefinitionDTO> definitions;
	private List<ExampleDTO> examples;
	private List<String> importances;
	private List<InOutputDTO> inOutputs;
	private List<String> limitations;
	private List<ArchetectureDTO> archetectures;
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
	private List<String> needs;
	private List<String> references;
	private List<ScenarioDTO> scenarios;
	private List<Comment> comments;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		SubTechDTO subTechDto = (SubTechDTO) obj;
		if (this.hashCode() == subTechDto.hashCode()) {
			return true;
		}
		return Boolean.FALSE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subTech == null) ? 0 : subTech.hashCode());
		return result;
	}
}
