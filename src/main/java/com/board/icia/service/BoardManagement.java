package com.board.icia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import com.board.icia.dao.IBoardDao;
import com.board.icia.dto.Board;
import com.board.icia.dto.Reply;
import com.board.icia.userClass.Paging;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class BoardManagement {
	@Autowired
	private IBoardDao bDao;
	ModelAndView mav;

	public ModelAndView getBoardList(Integer pageNum) { 
		// 초기값 null, int는 초기값이 null 될 수 없다
		List<Board> bList = null;
		mav = new ModelAndView();
		String view = null;
		int pNum = (pageNum == null) ? 1 : pageNum; //삼항연산자, pageNum이 null이면 1, 아니면 pageNum을 pNum에 넣는다.

		bList = bDao.getBoardList(pNum);
		// 로그인시 게시판 리스트 보여주기

		if (bList != null) {
			mav.addObject("bList", bList);
			mav.addObject("paging", getPaging(pNum));
			view = "boardList";
		} else {
			view = "home";
		}

		mav.setViewName(view);
		return mav;
	}

	private Object getPaging(int pNum) {
		int maxNum=bDao.getBoardCount(); 		// 전체 글의 개수
		int listCount=10; //10		// 페이지당 나타낼 글의 갯수
		int pageCount=2; //2		// 페이지그룹당 페이지 갯수
		String boardName="boardlist"; 	// 게시판이 여러개일 때 URL
		
		Paging paging=new Paging(maxNum, pNum, listCount, pageCount, boardName);
		return paging.makeHtmlPaging();
	}

	public ModelAndView getContents(Integer bNum) {
		mav = new ModelAndView();
		String view = null;
		
		Board board=bDao.getContents(bNum);
		mav.addObject("board", board);
		log.info("board:{}",board);
		
		List<Reply> rList=bDao.getReplyList(bNum);
		mav.addObject("rList", rList);
		log.info("rList size:{}",rList.size());
		
		view="boardContentsAjax"; //jsp
		mav.setViewName(view);
		return mav;
	}

	public String replyInsert(Reply r) {
		String json=null;
		mav=new ModelAndView();
		if(bDao.replyInsert(r)) {
			List<Reply> rList=bDao.getReplyList(r.getR_bnum());
			//mav.addObject("rList",  makeHtmlReplyInsert(rList));
			json=new Gson().toJson(rList);
			System.out.println("제이슨="+json);
		}else {
			json=null;
		}
		return json;
	}

	public Map<String, List<Reply>> replyInsertJackSon(Reply r) {
		Map<String, List<Reply>> rMap=null;
		if(bDao.replyInsert(r)) {
			List<Reply> rList=bDao.getReplyList(r.getR_bnum());
			rMap=new HashMap<String, List<Reply>>();
			rMap.put("rList", rList);
			System.out.println("rMap="+rMap);
			System.out.println("rMap="+rMap.get("rList"));
		}else {
			rMap=null;
		}
		return rMap;
	}

//	private String makeHtmlReplyInsert(List<Reply> rList) {
//		StringBuilder sb=new StringBuilder();
//		for(int i=0;i<rList.size();i++) {
//			sb.append("<tr height='20' align='center'>");
//			sb.append("<td width='100'>"+rList.get(i).getR_id()+"</td>");
//			sb.append("<td width='100'>"+rList.get(i).getR_contents()+"</td>");
//			sb.append("<td width='100'>"+rList.get(i).getR_date()+"</td>");
//			sb.append("</tr>");
//		}
//		return sb.toString();
//	}
}
