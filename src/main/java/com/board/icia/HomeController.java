package com.board.icia;



import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.board.icia.dto.Member;
import com.board.icia.service.MemberManagement;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private MemberManagement mm; //회원관리 서비스(비즈니스 로직)
	private ModelAndView mav;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		mav=new ModelAndView();
		mav.setViewName("home"); //로그인 화면
		logger.info("로그인화면으로 이동",mav);
		return mav;
	}
	
	@RequestMapping(value = "/access", method = RequestMethod.POST)
	public ModelAndView access(@ModelAttribute("mb") Member mb, HttpServletRequest request) {
		//데이터를 받을때 주는 이름과 받는 이름을 다르게 설정하고 싶다면 @ModelAttribute("변수명")을 사용
		System.out.println("access call");
		System.out.println("id="+mb.getM_id());
		System.out.println("pw="+mb.getM_pwd());
		mav=mm.memberAccess(mb,request);
		
		return mav;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logout(HttpServletRequest request) {
		mav=new ModelAndView();
		request.getSession().invalidate(); //세션을 삭제
		mav.setViewName("redirect:home");
		return mav;
	}
	
	@RequestMapping(value = "/joinfrm", method = RequestMethod.GET)
	public ModelAndView joinFrm() {
		mav=new ModelAndView();
		mav.setViewName("joinFrm");
		return mav;
	}
	
	@RequestMapping(value = "/memberjoin", method = RequestMethod.POST)
	public ModelAndView memberJoin(Member mb) {
		mav=mm.memberJoin(mb);
		
		return mav;
	}
	

	
}//Controller End
