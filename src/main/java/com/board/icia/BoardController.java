package com.board.icia;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.icia.service.BoardManagement;
import com.board.icia.userClass.DbException;

@Controller
public class BoardController {
	@Autowired
	private BoardManagement bm; //게시판 서비스 클래스
	ModelAndView mav;
	
	@RequestMapping(value = "/boardlist", method = RequestMethod.GET)
	public ModelAndView boardList(Integer pageNum) {
		//게시판 리스트를 보여주는 메소드, int는 초기값을 null로 받을수 없기 때문에 Integer로 받는다.
		mav=bm.getBoardList(pageNum);
		
		return mav;
	}
	
	@RequestMapping(value = "/contents") //method 생략하면 GET, POST 모두 가능
	public ModelAndView contents(Integer bNum) {
		mav=bm.getContents(bNum);
		
		return mav;
	}
	
	@RequestMapping(value = "/boarddelete",method = RequestMethod.GET) //method 생략하면 GET, POST 모두 가능
	public ModelAndView boardDelete(Integer bNum, RedirectAttributes attr) throws DbException {
		mav=bm.boardDelete(bNum,attr);
		
		//attr.addFlashAttribute("bNum", bNum); //무조건 삭제 후
		return mav;
	}
	
	@RequestMapping(value = "/writefrm") //method 생략하면 GET, POST 모두 가능
	public String writeFrm() {
		return "writeFrm";
	}
	
	@PostMapping(value="/boardwrite")
	public ModelAndView boardWrite(MultipartHttpServletRequest multi) {
		mav=new ModelAndView();
		mav=bm.boardWrite(multi);
		
		return mav;
	}
	
}
