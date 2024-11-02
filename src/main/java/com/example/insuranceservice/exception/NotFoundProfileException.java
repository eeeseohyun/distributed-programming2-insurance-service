package com.example.insuranceservice.exception;
public class NotFoundProfileException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String errorMessage = "[Exception] 회원 정보가 존재하지 않습니다.";
    public NotFoundProfileException() {
        super(errorMessage);
    }
    public NotFoundProfileException(String customMessage) {
        super(customMessage);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}