package com.board.icia.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.dao.IBoardDao;
import com.board.icia.dto.Board;

@Service
public class BoardManagement {
	@Autowired
	private IBoardDao bDao;
	ModelAndView mav;

	public ModelAndView getBoardList(Integer pageNum, HttpServletRequest request) { 
		// 초기값 null, int는 초기값이 null 될 수 없다
		List<Board> bList = null;
		mav = new ModelAndView();
		String view = null;
		int pNum = (pageNum == null) ? 1 : pageNum; //삼항연산자, pageNum이 null이면 1, 아니면 pageNum을 pNum에 넣는다.

		bList = bDao.getBoardList(pNum);
		// 로그인시 게시판 리스트 보여주기

		if (bList != null) {
			mav.addObject("bList", bList);
			view = "boardList";
		} else {
			view = "home";
		}

		mav.setViewName(view);
		return mav;
	}

	public ModelAndView getContents(Integer bNum) {
		mav = new ModelAndView();
		String view = null;

		/* 로그인 했을때에만 볼 수 있게 하는 로직, 인터셉트를 사용하여 필요없다.
		 * if (request.getSession().getAttribute("id") != null) { view = "boardList";
		 * 
		 * } else { view = "home"; }
		 */

		mav.setViewName(view);
		return mav;
	}
}
