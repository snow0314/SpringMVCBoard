package com.board.icia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.icia.dao.IBoardDao;
import com.board.icia.dto.Board;
import com.board.icia.dto.Reply;
import com.board.icia.userClass.DbException;
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
		ModelMap map=mav.getModelMap();
		System.out.println("map 처음상태:"+map.getAttribute("bList"));
		List<Board> mList=(List<Board>) map.getAttribute("bList");
		for(int i=0;i<mList.size();i++) {
			System.out.println(mList.get(i));
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
	
	//RedirectAttributes는 redirect전 session 영역에 저장한뒤 redirect 후 즉시 삭제한다.
	//삭제직전 session 영역에 저장했던 데이터는 request 객체에 저장한다.
	//addFlashAttribute: POST 방식(session에 저장후 1번 사용하면 삭제함)
	//attr.addAttribute: GET 방식(session에 저장후 request 객체에 저장된 후에 session에서 삭제함)
	@Transactional
	public ModelAndView boardDelete(Integer bNum, RedirectAttributes attr) throws DbException {
		System.out.println("bNum="+bNum);
		mav=new ModelAndView();
		boolean r=bDao.replyDelete(bNum);
		boolean a=bDao.aticleDelete(1000);
		if(a==false) { //0개 원글을 삭제한 경우 예외 발생시켜서 롤백
			throw new DbException();
		}
		
		if(r && a) {
			System.out.println("삭제 트랜잭션 성공");
			attr.addFlashAttribute("bNum", bNum); //POST 방식
			//attr.addAttribute("bNum", bNum); //GET 방식 리퀘스트 영역에 저장
		}else {
			System.out.println("삭제 트랜잭션 실패");
		}
		//mav.addObject("bNum", bNum); //GET 방식 리퀘스트 영역에 저장
		mav.setViewName("redirect:boardlist");
		return mav;
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
