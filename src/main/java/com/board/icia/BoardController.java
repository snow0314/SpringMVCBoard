package com.board.icia;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.service.BoardManagement;

@Controller
public class BoardController {
	@Autowired
	private BoardManagement bm; //게시판 서비스 클래스
	ModelAndView mav;
	
	@RequestMapping(value = "/boardlist", method = RequestMethod.GET)
	public ModelAndView boardList(Integer pageNum, HttpServletRequest request) {
		//게시판 리스트를 보여주는 메소드, int는 초기값을 null로 받을수 없기 때문에 Integer로 받는다.
		mav=bm.getBoardList(pageNum,request);
		
		return mav;
	}
	
	@RequestMapping(value = "/contents") //method 생략하면 GET, POST 모두 가능
	public ModelAndView contents(Integer bNum) {
		mav=bm.getContents(bNum);
		
		return mav;
	}
	
}
