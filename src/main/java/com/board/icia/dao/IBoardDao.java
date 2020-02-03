package com.board.icia.dao;

import java.util.List;

import com.board.icia.dto.Board;

public interface IBoardDao {

	List<Board> getBoardList(int pNum);
}
