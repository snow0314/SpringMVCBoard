package com.board.icia.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.icia.exception.PageException;

@ControllerAdvice
public class ControllerAdviceMVC {
	
	@ExceptionHandler(PageException.class)
	public String except(PageException ex, RedirectAttributes attr) {
		attr.addFlashAttribute("msg", ex.getMessage());
		return "redirect:/boardlist";
	}
}
