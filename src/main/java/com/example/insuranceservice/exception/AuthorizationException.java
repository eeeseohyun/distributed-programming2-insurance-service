package com.example.insuranceservice.exception;
public class AuthorizationException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String errorMessage = "[Exception] 현재 이 기능에 접근 권한이 없습니다.";
    public AuthorizationException() {
        super(errorMessage);
    }
    public AuthorizationException(String customMessage) {
        super(customMessage);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}