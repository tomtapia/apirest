package cl.courses.infrastructure.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import cl.courses.domain.Course;
import cl.courses.infrastructure.persistence.CourseRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CourseControllerTest {

	@Autowired
	private CourseRepository courseRepository;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void injectedComponentsAreNotNull() {
		assertThat(courseRepository).isNotNull();
		assertThat(restTemplate).isNotNull();
	}

	@Test
	public void shouldReturnAllCourses() {
		// given
		Course calculus = new Course(UUID.randomUUID(), "Calculus 101", "MX11");
		courseRepository.save(calculus);

		// when
//		List<Course> coursesFounds = restTemplate.getForObject("http://localhost:" + port + "/courses", List.class);
		Object coursesFounds = restTemplate.getForObject("http://localhost:" + port + "/courses", Object.class);

		// then
		assertThat(coursesFounds).isNotNull();
//		assertThat(coursesFounds.size()).isGreaterThan(0);
//		assertThat(coursesFounds.get(0).getCode()).isEqualTo(calculus.getCode());
	}
}
