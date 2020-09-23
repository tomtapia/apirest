package cl.students.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import cl.courses.domain.Course;

@Entity
@Table(name = "students")
public class Student {

	@Id
	@Column(updatable = false, nullable = false)
	private UUID id;
	@Pattern(regexp = "^[0-9]+[-|‐]{1}[0-9kK]{1}$", message = "RUT should be valid")
	private String rut;
	private String name;
	private String lastName;
	@Min(value = 18, message = "Age should not be less than 18")
	private Integer age;
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	public Student() {
		super();
	}

	public Student(UUID id,
			@Pattern(regexp = "/^[0-9]+[-|‐]{1}[0-9kK]{1}$/", message = "RUT should be valid") String rut, String name,
			String lastName, @Min(value = 18, message = "Age should not be less than 18") Integer age, Course course) {
		super();
		this.id = id;
		this.rut = rut;
		this.name = name;
		this.lastName = lastName;
		this.age = age;
		this.course = course;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
