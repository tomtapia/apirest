package cl.courses.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;

@Entity
public class Course {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String name;
	@Max(value = 4, message = "Code should not be greater than 4 chars")
	private String code;

	public Course() {
	}

}
