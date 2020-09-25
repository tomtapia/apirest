package cl.shared.infrastructure.presentation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class SharedControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest req, Exception ex) {
		log.error("Error: " + ex.getLocalizedMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(createResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest req, IllegalArgumentException ex) {
		log.error("Error: " + ex.getLocalizedMessage(), ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createResponse(HttpStatus.BAD_REQUEST, ex));
	}

	private Map<String, Object> createResponse(HttpStatus status, Exception ex) {
		Map<String, Object> errorResponse = new HashMap<String, Object>();
		errorResponse.put("timestamp", new Date());
		errorResponse.put("error", status.getReasonPhrase());
		errorResponse.put("error_description", ex.getLocalizedMessage());
		return errorResponse;
	}

}
