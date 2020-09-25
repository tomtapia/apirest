package cl.shared.infrastructure.presentation;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public abstract interface AbstractCrudController<T> {

	public ResponseEntity<Iterable<T>> getResources(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable);

	public ResponseEntity<T> getResource(@PathVariable(required = true) String id);

	public ResponseEntity<?> addResource(@Valid @RequestBody T entity);

	public ResponseEntity<?> updateResource(@PathVariable(required = true) String id, @Valid @RequestBody T entity);

	public ResponseEntity<?> deleteResource(@PathVariable(required = true) String id);

}
