package cl.students.infrastructure.presentation;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.shared.infrastructure.presentation.AbstractCrudController;
import cl.students.domain.Student;
import cl.students.infrastructure.persistence.StudentRepository;

@RestController
@Validated
public class StudentController implements AbstractCrudController<Student> {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	@GetMapping("/students")
	public ResponseEntity<Iterable<Student>> getResources(Pageable pageable) {
		return ResponseEntity.ok(studentRepository.findAll(pageable));
	}

	@Override
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getResource(String id) {
		return ResponseEntity.of(studentRepository.findById(UUID.fromString(id)));
	}

	@Override
	@PostMapping("/students")
	public ResponseEntity<?> addResource(@Valid Student entity) {
		studentRepository.save(entity);
		return ResponseEntity.created(URI.create(String.format("/students/%s", entity.getId()))).build();
	}

	@Override
	@PutMapping("/students/{id}")
	public ResponseEntity<?> updateResource(String id, @Valid Student entity) {
		entity.setId(UUID.fromString(id));
		studentRepository.save(entity);
		return ResponseEntity.ok().build();
	}

	@Override
	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteResource(String id) {
		Optional<Student> student = studentRepository.findById(UUID.fromString(id));
		if (student.isPresent()) {
			studentRepository.delete(student.get());
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
