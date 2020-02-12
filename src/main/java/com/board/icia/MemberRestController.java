package com.board.icia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.board.icia.service.MemberManagement;

@RestController // @ResponseBody 생략가능
@RequestMapping(value = "/user")
public class MemberRestController {
	@Autowired
	private MemberManagement mm;
	// 회원 테이블에서 아이디가 사용가능한 지 확인
//		@GetMapping(path="/users/user/username", produces=MediaType.TEXT_PLAIN_VALUE)
//		public ResponseEntity<?> idAvailable(@NonNull String username) {
//			return ResponseEntity.ok(service.idAvailable(username)+"");
//		}
	
	@GetMapping(value="/userid", produces="text/plain;charset=utf-8")  
	public ResponseEntity<?> idAvailable(String m_id){
//		ResponseEntity<?> result=null;
//		if(mm.idAvailable(m_id)) {
//			result=ResponseEntity.ok().body("사용가능한 아이디");
//		}else {
//			result=ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("사용불가 아이디");
//		}
//		return result;
		return ResponseEntity.ok(mm.idAvailable(m_id));
	}
	
}
