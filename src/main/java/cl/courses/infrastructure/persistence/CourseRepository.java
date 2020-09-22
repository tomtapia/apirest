package cl.courses.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cl.courses.domain.Course;

@Repository
public interface CourseRepository extends PagingAndSortingRepository<Course, UUID> {

}
