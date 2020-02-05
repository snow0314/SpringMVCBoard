package com.board.icia;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.dto.Reply;
import com.board.icia.service.BoardManagement;



@RestController
@RequestMapping(value="/rest") //무조건 /rest로 시작하는 URL을 받는다. 이걸쓰면 URL에 /rest 안써도 됨
public class BoardRestController {
	@Autowired
	private BoardManagement bm;
	ModelAndView mav;
	
	//json 버전
//	@RequestMapping(value = "/replyinsert", produces = "application/json;charset=UTF-8")
//	public @ResponseBody String replyInsert(Reply r, HttpServletRequest req){
//		System.out.println("r_bnum="+r.getR_bnum());
//		System.out.println("r_con="+r.getR_contents());
//		r.setR_id(req.getSession().getAttribute("id").toString());
//		String json=bm.replyInsert(r);
//		
//		return json;
//	}

	//jackson 버전
	@RequestMapping(value = "/replyinsert", produces = "application/json;charset=UTF-8")
	public @ResponseBody Map<String, List<Reply>> replyInsert(Reply r, HttpServletRequest req){
		r.setR_id(req.getSession().getAttribute("id").toString());
		Map<String, List<Reply>> rMap=bm.replyInsertJackSon(r);
		
		return rMap; //jackson 역활: Map -->json으로 변환
		//{'rList', rList} --> {"rList":[{},{},{}]
	}
}
