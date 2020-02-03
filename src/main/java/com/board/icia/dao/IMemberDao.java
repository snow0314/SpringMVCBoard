package com.board.icia.dao;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.board.icia.dto.Member;
@Component
public interface IMemberDao {
	public boolean getLoginResult(Member mb);

	public boolean memberJoin(Member mb);

	public String getSecurityPwd(String m_id);

	public Member getMemberInfo(String m_id);

	public boolean hashMapTest(Map<String, String> hMap);

	public Map<String, String> hashMapTest2(String id);
}
