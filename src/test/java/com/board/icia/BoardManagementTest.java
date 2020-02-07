package com.board.icia;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.board.icia.dto.Board;
import com.board.icia.service.BoardManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/**/*-context.xml")
//@Transactional  //테스트 조작시 자동으로 롤백처리해줌
public class BoardManagementTest {
	@Autowired
	private BoardManagement bm;
	
	@Test
	public void testExist() {
		log.info("bm={}",bm);
		assertThat(bm, is(notNullValue()));
	}
	@Test
	@SuppressWarnings("unchecked")
	public void testGetList() {
		List<Board> bList=(List<Board>) bm.getBoardList(1).getModelMap().getAttribute("bList");
		bList.forEach(b->log.info("b={}",b));
		
//		for(Board b: bList) {
//			log.info("bb={}",b);
//		} 
		assertThat(bList.size(), is(10));
		//assertThat(bm, is(notNullValue()));
	}
	
}
