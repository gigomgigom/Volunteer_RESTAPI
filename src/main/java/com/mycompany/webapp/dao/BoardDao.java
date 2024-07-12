package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.BoardDto;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.ReviewReplyDto;
import com.mycompany.webapp.dto.SearchIndex;

@Mapper
public interface BoardDao {
	public int insertBoard(BoardDto board);

	public BoardDto selectBoardByNo(int boardNo);

	public int updateBoard(BoardDto board);

	public int updateHitCntByNo(int boardNo);

	public int deleteBoard(int boardNo);

	public int selectBoardCount(SearchIndex searchIndex);

	public List<BoardDto> selectBoardList(SearchIndex searchIndex, Pager pager);

	//메인에 공지사항 출력
	public List<BoardDto> getNoticeListToMain();


}
