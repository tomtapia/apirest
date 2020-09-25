package cl.courses.infrastructure.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import cl.shared.presentation.TestHttpHelper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CourseControllerTest {

	@LocalServerPort
	private int port;
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	@Autowired
	private TestRestTemplate restTemplate;
	private TestHttpHelper testHttpHelper;

	@BeforeEach
	public void initHelpers() {
		this.testHttpHelper = new TestHttpHelper(port, clientId, clientSecret, restTemplate);
	}

	@Test
	@Sql({"/import_curses.sql"}) // given
	public void shouldReturnAllCourses() {
		// when
		JSONObject coursesFounds = testHttpHelper.get("/courses", JSONObject.class);

		// then
		assertThat(coursesFounds).isNotNull();
	}
}
