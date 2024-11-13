package com.example.todo.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.todo.dto.ErrorResponse;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		logger.error("An unexpected error occurred: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
	}
	




	// 共通のエラーレスポンス作成メソッド
	private ErrorResponse createErrorResponse(
			String errorMessage, int statusCode, String errorTitle, List<String> errorDetails) {

		return new ErrorResponse(
				LocalDateTime.now(),       // タイムスタンプ
				statusCode,                // ステータスコード
				errorTitle,                // エラーメッセージ
				errorDetails               // 詳細エラーリスト
				);
	}



	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	        MethodArgumentNotValidException ex, org.springframework.http.HttpHeaders headers,
	        HttpStatusCode status, WebRequest request) {

	    // エラーメッセージを収集
	    List<String> errors = ex.getBindingResult()
	                                .getFieldErrors()
	                                .stream()
	                                .map(x -> x.getField() + ": " + x.getDefaultMessage())
	                                .collect(Collectors.toList());

	    // ErrorResponse を作成
	    ErrorResponse errorResponse = createErrorResponse(
	        "Validation Failed",       // エラーメッセージ
	        status.value(),            // ステータスコード
	        "Validation Failed",       // エラーメッセージタイトル
	        errors                     // 詳細エラーリスト
	    );

	    // ログ出力
	    logger.error("Validation failed: {}", errors);

	    return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
	        HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
	        HttpStatusCode status, WebRequest request) {

	    // ErrorResponse を作成
	    ErrorResponse errorResponse = createErrorResponse(
	        "Method Not Allowed",      // エラーメッセージ
	        status.value(),            // ステータスコード
	        "Method Not Allowed",      // エラーメッセージタイトル
	        List.of(ex.getMessage())   // 詳細エラーリスト
	    );

	    // ログ出力
	    logger.error("Method not allowed: {}", ex.getMessage());

	    return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
	        HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
	        HttpStatusCode status, WebRequest request) {

	    // ErrorResponse を作成
	    ErrorResponse errorResponse = createErrorResponse(
	        "Unsupported Media Type",  // エラーメッセージ
	        status.value(),            // ステータスコード
	        "Unsupported Media Type",  // エラーメッセージタイトル
	        List.of(ex.getMessage())   // 詳細エラーリスト
	    );

	    // ログ出力
	    logger.error("Unsupported media type: {}", ex.getMessage());

	    return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(
	        MissingPathVariableException ex, HttpHeaders headers,
	        HttpStatusCode status, WebRequest request) {

	    // ErrorResponse を作成
	    ErrorResponse errorResponse = createErrorResponse(
	        "Missing Path Variable",  // エラーメッセージ
	        status.value(),            // ステータスコード
	        "Missing Path Variable",  // エラーメッセージタイトル
	        List.of(ex.getMessage())   // 詳細エラーリスト
	    );

	    // ログ出力
	    logger.error("Missing path variable: {}", ex.getMessage());

	    return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
	        MissingServletRequestParameterException ex, HttpHeaders headers,
	        HttpStatusCode status, WebRequest request) {

	    // ErrorResponse を作成
	    ErrorResponse errorResponse = createErrorResponse(
	        "Missing Request Parameter",  // エラーメッセージ
	        status.value(),               // ステータスコード
	        "Missing Request Parameter",  // エラーメッセージタイトル
	        List.of(ex.getMessage())      // 詳細エラーリスト
	    );

	    // ログ出力
	    logger.error("Missing request parameter: {}", ex.getMessage());

	    return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(
	        TypeMismatchException ex, HttpHeaders headers,
	        HttpStatusCode status, WebRequest request) {

	    // ErrorResponse を作成
	    ErrorResponse errorResponse = createErrorResponse(
	        "Argument Type Mismatch",   // エラーメッセージ
	        status.value(),              // ステータスコード
	        "Argument Type Mismatch",   // エラーメッセージタイトル
	        List.of(ex.getMessage())     // 詳細エラーリスト
	    );

	    // ログ出力
	    logger.error("Argument type mismatch: {}", ex.getMessage());

	    return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

	    // ErrorResponse を作成
	    ErrorResponse errorResponse = createErrorResponse(
	        "Handler Not Found",      // エラーメッセージ
	        status.value(),            // ステータスコード
	        "Handler Not Found",      // エラーメッセージタイトル
	        List.of(ex.getMessage())   // 詳細エラーリスト
	    );

	    // ログ出力
	    logger.error("Handler not found: {}", ex.getMessage());

	    return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
	}
	
	@ExceptionHandler(AlreadyMemberException.class)
	public ResponseEntity<Object> handleAccessDeninedException(AlreadyMemberException ex, WebRequest request) {
		ErrorResponse errorResponse = createErrorResponse(
		        "Conflict",      // エラーメッセージ
		        HttpStatus.CONFLICT.value(),            // ステータスコード
		        "Conflict",      // エラーメッセージタイトル
		        List.of(ex.getMessage())   // 詳細エラーリスト
		    );
		logger.error("Conflict: {}", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex,WebRequest request) {
		ErrorResponse errorResponse = createErrorResponse(
		        "Resorce not found",      // エラーメッセージ
		        HttpStatus.NOT_FOUND.value(),            // ステータスコード
		        "Resorce not found",      // エラーメッセージタイトル
		        List.of(ex.getMessage())   // 詳細エラーリスト
		    );
		logger.error("Resorce not found: {}", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}



}
