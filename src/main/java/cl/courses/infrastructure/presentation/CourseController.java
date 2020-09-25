package cl.courses.infrastructure.presentation;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.courses.domain.Course;
import cl.courses.infrastructure.persistence.CourseRepository;
import cl.shared.infrastructure.presentation.AbstractCrudController;

@RestController
@Validated
public class CourseController implements AbstractCrudController<Course> {

	@Autowired
	private CourseRepository courseRepository;

	@Override
	@GetMapping("/courses")
	public ResponseEntity<Iterable<Course>> getResources(Pageable pageable) {
		return ResponseEntity.ok(courseRepository.findAll(pageable));
	}

	@Override
	@GetMapping("/courses/{id}")
	public ResponseEntity<Course> getResource(String id) {
		return ResponseEntity.of(courseRepository.findById(UUID.fromString(id)));
	}

	@Override
	@PostMapping("/courses")
	public ResponseEntity<?> addResource(Course entity) {
		courseRepository.save(entity);
		return ResponseEntity.created(URI.create(String.format("/courses/%s", entity.getId()))).build();
	}

	@Override
	@PutMapping("/courses/{id}")
	public ResponseEntity<?> updateResource(String id, Course entity) {
		entity.setId(UUID.fromString(id));
		courseRepository.save(entity);
		return ResponseEntity.ok().build();
	}

	@Override
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<?> deleteResource(String id) {
		Optional<Course> course = courseRepository.findById(UUID.fromString(id));
		if (course.isPresent()) {
			courseRepository.delete(course.get());
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
