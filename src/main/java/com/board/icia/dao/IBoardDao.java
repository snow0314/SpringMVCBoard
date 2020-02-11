package com.board.icia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.board.icia.dto.Bfile;
import com.board.icia.dto.Board;
import com.board.icia.dto.Reply;

public interface IBoardDao {

	List<Board> getBoardList(int pNum);

	Board getContents(Integer bNum);

	List<Reply> getReplyList(Integer bNum);
	
	@Select("SELECT COUNT(*) FROM BLIST_1")
	int getBoardCount();

	boolean replyInsert(Reply r);

	boolean replyDelete(Integer bNum);

	boolean aticleDelete(Integer bNum);

	boolean boardWrite(Board board);

	@Insert("INSERT INTO BF VALUES(BF_SEQ.NEXTVAL,#{bnum}, #{oriFileName}, #{sysFileName})")
	boolean fileInsert(Map<String, String> fMap);
	
	@Select("SELECT * FROM BF WHERE BF_BNUM=#{bNum}")
	List<Bfile> getBfList(Integer bNum);
	
	String getOriFileName(String sysfilename);

	boolean fileDelete(Integer bNum);

//	@Select("SELECT BOARD_SEQ.CURRVAL FROM DUAL")
//	int getCurBoardNum();
}
