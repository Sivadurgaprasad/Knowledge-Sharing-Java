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
	private String blog;
	private String shortNote;
	private String blogIconName;
	@NotNull
	@NotEmpty
	private String tech;
	@NotNull
	@NotEmpty
	private List<SubTechDTO> subTechs;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		TechInfo techInfo = (TechInfo) obj;
		if (this.hashCode() == techInfo.hashCode()) {
			return true;
		}
		return Boolean.FALSE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tech == null) ? 0 : tech.hashCode());
		result = prime * result + ((subTechs == null) ? 0 : subTechs.hashCode());
		return result;
	}
}
