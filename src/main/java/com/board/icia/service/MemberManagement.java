package com.board.icia.service;
//회원관리 서비스 클래스(MODEL)

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.dao.IMemberDao;
import com.board.icia.dto.Member;

@Component
public class MemberManagement {
	@Autowired
	private IMemberDao mDao;
	private ModelAndView mav;
	
	private void hashMapTest(String id, String pwd) {
		Map<String, String> hMap=new HashMap<>();
		hMap.put("id", id);
		hMap.put("pw", pwd);
		boolean result=mDao.hashMapTest(hMap);
		System.out.println("result="+result); //로그인 성공:true, 실패:false
		hMap=mDao.hashMapTest2(id);
		System.out.println("name="+hMap.get("M_NAME"));
		System.out.println("name="+hMap.get("G_NAME"));
		
	}
	
	public ModelAndView memberAccess(Member mb, HttpServletRequest request) {
		mav=new ModelAndView();
		String view=null;
		
		//스프링에선 복호화 메소드 없음
		BCryptPasswordEncoder pwdEncoder=new BCryptPasswordEncoder();
		String pwdEncode=mDao.getSecurityPwd(mb.getM_id());
		System.out.println("pw="+pwdEncode);
		
		//hashMap 로그인 테스트
		hashMapTest(mb.getM_id(), pwdEncode);
		
		if(pwdEncode!=null) {
			if(pwdEncoder.matches(mb.getM_pwd(), pwdEncode)) {//비교
				//로그인 성공
				HttpSession session=request.getSession();
				session.setAttribute("id", mb.getM_id());
				//로그인 후 회원정보를 화면 출력하기 위해
				mb=mDao.getMemberInfo(mb.getM_id());
				session.setAttribute("mb", mb);
				
				//view="boardList"; //jsp
				//view="forward:/boardList"; //forward:url, POST->POST, GET->GET끼리만
				view="redirect:/boardlist"; //redirect:url, POST,GET-->GET 방식으로만
			}else {//비번 오류
				view="home";
				mav.addObject("check", 2); //로그인 실패
			}
		}else { //아이디 오류
			view="home";
			mav.addObject("check", 2); //로그인 실패
		}
		mav.setViewName(view);
		
		return mav;
	}

	

	public ModelAndView memberJoin(Member mb) {
		mav=new ModelAndView();
		String view=null;
		//Encoder(암호화) <-->Decoder(복호화)
		//스프링은 암호화는 가능하지만 복호화가 불가능하다.
		BCryptPasswordEncoder pwdEncoder=new BCryptPasswordEncoder();
		//비번만 암호화해서 DB에 저장
		mb.setM_pwd(pwdEncoder.encode(mb.getM_pwd()));
		if(mDao.memberJoin(mb)) {
			view="home"; //회원가입 성공시 로그인
			mav.addObject("check", 1); //성공
		}else {
			view="joinFrm";
		}
		mav.setViewName(view);
		return mav;
	}
	
}
