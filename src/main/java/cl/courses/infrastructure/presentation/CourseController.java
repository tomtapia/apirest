package cl.courses.infrastructure.presentation;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.courses.domain.Course;
import cl.courses.infrastructure.persistence.CourseRepository;

@RestController
@Validated
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;

	@GetMapping("/courses")
	public ResponseEntity<Iterable<Course>> getCources(@RequestParam(name = "page", required = false) int page) {
		return ResponseEntity.ok(courseRepository.findAll(PageRequest.of(page, 10)));
	}

	@GetMapping("/courses/{id}")
	public ResponseEntity<Course> getCourse(@PathVariable @Min(1) String id) {
		return ResponseEntity.of(courseRepository.findById(UUID.fromString(id)));
	}

	@PostMapping("/courses")
	public ResponseEntity<Object> addCourse(@Valid @RequestBody Course course) {
		courseRepository.save(course);
		return ResponseEntity.created(URI.create(String.format("/courses/%s", course.getId()))).build();
	}

	@PutMapping("/courses/{id}")
	public ResponseEntity<Object> updateCourse(@PathVariable String id, @Valid @RequestBody Course course) {
		course.setId(UUID.fromString(id));
		courseRepository.save(course);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/courses/{id}")
	public ResponseEntity<Object> deleteCourse(@PathVariable String id) {
		courseRepository.deleteById(UUID.fromString(id));
		return ResponseEntity.noContent().build();
	}
}
