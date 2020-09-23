package cl.students.infrastructure.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import cl.students.infrastructure.persistence.StudentRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

	@Autowired
	private StudentRepository studentRepository;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void injectedComponentsAreNotNull() {
		assertThat(studentRepository).isNotNull();
		assertThat(restTemplate).isNotNull();
	}

	@Test
	public void shouldReturnAllStudents() {

	}
}
