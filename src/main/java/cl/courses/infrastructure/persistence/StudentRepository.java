package cl.courses.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cl.courses.domain.Student;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, UUID> {

	List<Student> findByRut(String rut, Pageable pageable);

}
