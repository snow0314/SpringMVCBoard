package com.board.icia;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.board.icia.dao.IMemberDao;
import com.board.icia.dto.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
@Transactional //업데이트, 삭제 등 DB 수정하는 쿼리문이 테스트만 하고 실제로 반영은 안되게 하는 어노테이션
public class MemberDaoTest {
	@Autowired
	private IMemberDao dao;
	
	@Test
	public void loginTest() {
		log.info("dao={}",dao);
		log.info("단위테스트");
		Member mb=new Member().setM_id("LEE").setM_pwd("1111");
		assertThat(dao.getLoginResult(mb), is(true));
		mb=dao.getMemberInfo("aaa");
		assertThat(mb.getM_name(), is("아"));
	}
}
