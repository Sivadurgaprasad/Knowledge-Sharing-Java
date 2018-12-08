package com.jw.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Comment extends BaseModel {

	private static final long serialVersionUID = -1982897230900839066L;
	private String name;
	@NotNull
	@NotEmpty
	private String message;
	@Email
	private String email;
	private Comment reply;
}
