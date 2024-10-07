package auth.application.exception.handler;

import auth.application.exception.ApplicationOperationException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import auth.application.exception.dto.ErrorResponse;
import auth.utils.CaseConverter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
@ControllerAdvice
public class RestExceptionHandlerImpl {
    
    /*private final AccessClientRequestLogService accessClientRequestLogService;

    public RestExceptionHandlerImpl(AccessClientRequestLogService accessClientRequestLogService) {
        this.accessClientRequestLogService = accessClientRequestLogService;
    }*/

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, WebRequest request) {
        String error = ex.getMessage();

        final ErrorResponse errorResponse = createErrorResponse(error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, WebRequest request) {
        String error = ex.getMessage();

        final ErrorResponse errorResponse = createErrorResponse(error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, WebRequest request) {
        String error = ex.getMessage();

        final ErrorResponse errorResponse = createErrorResponse(error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    public ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, WebRequest request) {
        String error = ex.getMessage();

        final ErrorResponse errorResponse = createErrorResponse(error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, WebRequest webRequest) {
        String error = ex.getMessage();

        final ErrorResponse errorResponse = createErrorResponse(error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 400

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(String.format("%s %s", CaseConverter.camelCaseToSentence(error.getField()), error.getDefaultMessage()));
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(String.format("%s %s", CaseConverter.camelCaseToSentence(error.getObjectName()), error.getDefaultMessage()));
        }
        final ErrorResponse errorResponse = createErrorResponse(errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(final BindException ex, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ErrorResponse errorResponse = createErrorResponse(errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final WebRequest request) {
        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

        final ErrorResponse errorResponse = createErrorResponse(error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final WebRequest request) {
        final String error = ex.getRequestPartName() + " part is missing";
        final ErrorResponse errorResponse = createErrorResponse(error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final WebRequest request) {
        final String error = ex.getParameterName() + " parameter is missing";
        final ErrorResponse errorResponse = createErrorResponse(error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        String typeName = ex.getRequiredType().getName();
        if (typeName.equalsIgnoreCase("java.time.LocalDate")) {
            typeName = "date";
        }
        String error = ex.getName() + " should be of type " + typeName;

        final ErrorResponse errorResponse = createErrorResponse(error, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(String.format("%s %s", violation.getPropertyPath(), violation.getMessage()));
        }

        final ErrorResponse errorResponse = createErrorResponse(errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchMessageException.class)
    public ResponseEntity<Object> handleAppInputErrorException(NoSuchMessageException ex) {
        final ErrorResponse errorResponse = createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationOperationException.class)
    public ResponseEntity<Object> handleMobiTillWebAppOperationException(ApplicationOperationException ex) {
        ErrorResponse errorResponse;
        if (ex.getErrors() != null) {
            errorResponse = createErrorResponse(ex.getErrors(), HttpStatus.BAD_REQUEST);
        } else {
            errorResponse = createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        ErrorResponse errorResponse;
        String message = ex.getLocalizedMessage();
        if (message.contains("problem:")) {
            errorResponse = createErrorResponse(message.substring(message.indexOf("problem:") + 9, message.indexOf("; nested exception is")), HttpStatus.BAD_REQUEST);
        } else {
            errorResponse = createErrorResponse("operation.invalid.request", HttpStatus.BAD_REQUEST);
        }
        log.warn(String.format("%s %s", ex.getClass().getName(), ex.getLocalizedMessage()));
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 403

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        final ErrorResponse errorResponse = createErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }

    // 404

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final WebRequest request) {
        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        final ErrorResponse errorResponse = createErrorResponse(error, HttpStatus.NOT_FOUND);
        return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    // 405

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));

        final ErrorResponse errorResponse = createErrorResponse(builder.toString(), HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // 415

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));

        final ErrorResponse errorResponse = createErrorResponse(builder.substring(0, builder.length() - 2), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /*@ExceptionHandler(FileUploadBase.FileSizeLimitExceededException.class)
    public ResponseEntity<Object> handleFileSizeLimitExceededException(FileUploadBase.FileSizeLimitExceededException e) {
        final AppResponse errorResponse = new AppResponse("error.file-exceeds-max-permitted-size");
        return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
    }*/

    //StandardServletMultipartResolver
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Object> handleMultipartException(MultipartException e) {
        final ErrorResponse errorResponse = createErrorResponse(Objects.requireNonNull(e.getMessage()), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //CommonsMultipartResolver
    /*@ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        final ErrorResponse errorResponse = createErrorResponse("error.file.upload.limit", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }*/

    @ExceptionHandler(LockAcquisitionException.class)
    public ResponseEntity<Object> lockAcquisitionExceptionException(LockAcquisitionException e) {
        log.error("LockAcquisitionException: Error another user is currently modifying this record");

        final ErrorResponse errorResponse = createErrorResponse("error.concurrent.modification", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CannotAcquireLockException.class)
    public ResponseEntity<Object> cannotAcquireLockExceptionException(CannotAcquireLockException e) {
        log.error("CannotAcquireLockException: Error another user is currently modifying this record");

        final ErrorResponse errorResponse = createErrorResponse("error.concurrent.modification", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValueInstantiationException.class)
    public ResponseEntity<Object> valueInstantiationExceptionException(ValueInstantiationException e) {
        final ErrorResponse errorResponse = createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // 500

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        log.error(ex.getMessage(), ex);
        //500
        final ErrorResponse errorResponse = createErrorResponse("error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse createErrorResponse(String error, HttpStatus httpStatus) {
        final ErrorResponse errorResponse = new ErrorResponse(httpStatus, error);
        //accessClientRequestLogService.logError(errorResponse, httpStatus);
        return errorResponse;
    }

    private ErrorResponse createErrorResponse(List<String> errors, HttpStatus httpStatus) {
        final ErrorResponse errorResponse = new ErrorResponse(httpStatus, errors);
        //accessClientRequestLogService.logError(errorResponse, httpStatus);
        return errorResponse;
    }

}
