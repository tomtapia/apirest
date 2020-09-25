package cl.students.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import cl.courses.domain.Course;
import cl.students.domain.Student;

@SpringBootTest
public class RutValidationTest {

	private Validator validator;

	@BeforeEach
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testContactSuccess() {
		// given
		Course calculus = new Course(UUID.randomUUID(), "Calculus 101", "MX11");
		Student fran = new Student(UUID.randomUUID(), "1-9", "Fran", "Smith", 18, calculus);

		// when
		Set<ConstraintViolation<Student>> violations = validator.validate(fran);
		
		// then
		assertThat(violations.isEmpty()).isTrue();
	}

}
