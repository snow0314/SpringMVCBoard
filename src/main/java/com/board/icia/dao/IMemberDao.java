package com.board.icia.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.board.icia.dto.Member;
@Component
public interface IMemberDao {
	public boolean getLoginResult(Member mb);

	public boolean memberJoin(Member mb);

	public String getSecurityPwd(String m_id);
	
	//@Select("SELECT * FROM MINFO WHERE M_ID=#{m_id}") Mapper에 등록하지 않고 어노테이션으로도 사용 가능
	public Member getMemberInfo(String m_id);

	public boolean hashMapTest(Map<String, String> hMap);

	public Map<String, String> hashMapTest2(String id);
}
