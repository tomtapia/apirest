package cl.courses.infrastructure.persistence;

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

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CourseRepositoryTest {

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private CourseRepository courseRepository;

	@Test
	public void injectedComponentsAreNotNull() {
		assertThat(entityManager).isNotNull();
		assertThat(courseRepository).isNotNull();
	}

	@Test
	public void shouldReturnCourseWhenFindByCode() {
		// given
		Course calculus = new Course(UUID.randomUUID(), "Calculus 101", "MX11");
		entityManager.persist(calculus);
		entityManager.flush();

		// when
		List<Course> found = courseRepository.findByCode(calculus.getCode(), PageRequest.of(0, 1));

		// then
		assertThat(found).isNotNull();
		assertThat(found.size()).isGreaterThan(0);
		assertThat(found.get(0).getCode()).isEqualTo(calculus.getCode());
	}
}
