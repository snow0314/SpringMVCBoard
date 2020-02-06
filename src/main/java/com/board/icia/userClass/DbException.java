package com.board.icia.userClass;

public class DbException extends RuntimeException {
	
	public DbException() {
		super("DB 예외 발생");
	}
}
