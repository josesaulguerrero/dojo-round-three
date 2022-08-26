package ec.com.reactive.music.infrastructure.exceptions.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@AllArgsConstructor
public class HttpException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}
