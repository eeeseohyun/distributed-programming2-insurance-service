package com.example.insuranceservice.exception;
public class AuthenticationException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String errorMessage = "[Exception] 로그인을 먼저 진행해주세요.";
    public AuthenticationException() {
        super(errorMessage);
    }
    public AuthenticationException(String customMessage) {
        super(customMessage);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}