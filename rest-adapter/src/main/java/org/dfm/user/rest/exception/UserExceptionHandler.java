package org.dfm.user.rest.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.dfm.user.domain.exception.UserNotFoundException;
import org.dfm.user.rest.generated.model.ProblemDetail;

@RestControllerAdvice(basePackages = {"org.dfm.user"})
public class UserExceptionHandler {

  @ExceptionHandler(value = UserNotFoundException.class)
  public final ResponseEntity<ProblemDetail> handleUserNotFoundException(
      final Exception exception, final WebRequest request) {
    var problem =
        ProblemDetail.builder()
            .type("https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404")
            .status(HttpStatus.NOT_FOUND.value())
            .title("User not found")
            .detail(exception.getMessage())
            .instance(((ServletWebRequest) request).getRequest().getRequestURI())
            .timestamp(LocalDateTime.now())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
  }
}
