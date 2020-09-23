package cl.students.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import cl.courses.domain.Course;
import cl.students.domain.Student;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StudentRepositoryTest {

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private StudentRepository studentRepository;

	@Test
	public void injectedComponentsAreNotNull() {
		assertThat(entityManager).isNotNull();
		assertThat(studentRepository).isNotNull();
	}

	@Test
	public void whenFindByRut_thenReturnStudent() {
		// given
		Course calculus = new Course(UUID.randomUUID(), "Calculus 101", "MX11");
		entityManager.persist(calculus);
		Student fran = new Student(UUID.randomUUID(), "1-9", "Fran", "Smith", 18, calculus);
		entityManager.persist(fran);
		entityManager.flush();

		// when
		List<Student> foundList = studentRepository.findByRut(fran.getRut(), PageRequest.of(0, 1));

		// then
		assertThat(foundList).isNotNull();
		assertThat(foundList.size()).isGreaterThan(0);
		Student found = foundList.get(0);
		assertThat(found.getRut()).isEqualTo(fran.getRut());
		assertThat(found.getCourse()).isNotNull();
		assertThat(found.getCourse().getCode()).isEqualTo(calculus.getCode());
	}

	@Test
	public void whenValidRut_thenSaveStudent() {
		// given
		Course calculus = new Course(UUID.randomUUID(), "Calculus 101", "MX11");
		entityManager.persist(calculus);
		entityManager.flush();
		Student alex = new Student(UUID.randomUUID(), "1111111-1", "Alex", "Lordop", 18, calculus);

		// when
		studentRepository.save(alex);

		// then
		assertThat(alex.getRut()).isNotNull();
	}
}
