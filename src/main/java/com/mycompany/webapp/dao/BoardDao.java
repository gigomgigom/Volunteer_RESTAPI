package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.BoardDto;

@Mapper
public interface BoardDao {
	public int insertBoard(BoardDto board);

	public BoardDto selectBoardByNo(int boardNo);

	public int updateBoard(BoardDto board);

	public int updateHitCntByNo(int boardNo);

	public int deleteBoard(int boardNo);
	
}
