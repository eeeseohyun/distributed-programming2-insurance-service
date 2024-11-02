package com.example.insuranceservice.exception;
public class DuplicateIDException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String errorMessage = "[Exception] id가 중복되었습니다. 다시 시도해주세요";
    public DuplicateIDException() {
        super(errorMessage);
    }
    public DuplicateIDException(String customMessage) {
        super(customMessage);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}