package com.jw.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TechInfo  {

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
	private List<String> subTechs;
	private String blogIconName;

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
		result = prime * result + ((blog == null) ? 0 : blog.hashCode());
		result = prime * result + ((subTechs == null) ? 0 : subTechs.hashCode());
		return result;
	}

}
