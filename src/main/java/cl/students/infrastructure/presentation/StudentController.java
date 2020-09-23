package cl.students.infrastructure.presentation;

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

import cl.students.domain.Student;
import cl.students.infrastructure.persistence.StudentRepository;

@RestController
@Validated
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@GetMapping("/students")
	public ResponseEntity<Iterable<Student>> getStudents(@RequestParam(name = "page", required = false) int page) {
		return ResponseEntity.ok(studentRepository.findAll(PageRequest.of(page, 10)));
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable @Min(1) String id) {
		return ResponseEntity.of(studentRepository.findById(UUID.fromString(id)));
	}

	@PostMapping("/students")
	public ResponseEntity<Object> addStudent(@Valid @RequestBody Student student) {
		studentRepository.save(student);
		return ResponseEntity.created(URI.create(String.format("/students/%s", student.getId()))).build();
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Object> updateStudent(@PathVariable String id, @Valid @RequestBody Student student) {
		student.setId(UUID.fromString(id));
		studentRepository.save(student);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable String id) {
		studentRepository.deleteById(UUID.fromString(id));
		return ResponseEntity.noContent().build();
	}

}
