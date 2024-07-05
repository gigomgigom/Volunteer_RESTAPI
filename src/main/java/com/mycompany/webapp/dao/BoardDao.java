package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.BoardDto;
import com.mycompany.webapp.dto.Pager;

@Mapper
public interface BoardDao {
	public int insertBoard(BoardDto board);

	public BoardDto selectBoardByNo(int boardNo);

	public int updateBoard(BoardDto board);

	public int updateHitCntByNo(int boardNo);

	public int deleteBoard(int boardNo);

	public int getCount();
	
	public List<BoardDto> getNoticeList(Pager pager);
	
}
