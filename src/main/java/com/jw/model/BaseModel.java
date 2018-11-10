package com.jw.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String createdUser;
	private LocalDateTime createdDate;
	private String lastUsedUser;
	private LocalDateTime lastUsedDate;
	private String operation;
}
