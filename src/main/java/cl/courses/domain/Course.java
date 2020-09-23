package cl.courses.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Entity
@Table(name = "courses")
public class Course {

	@Id
	@Column(updatable = false, nullable = false)
	private UUID id;
	private String name;
	@Column(length = 4)
	@Size(max = 4, message = "Code should not be greater than 4 chars")
	private String code;

	public Course() {
		super();
	}

	public Course(UUID id, String name,
			@Max(value = 4, message = "Code should not be greater than 4 chars") String code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
