package com.mycompany.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.BoardDao;
import com.mycompany.webapp.dao.ReviewDao;
import com.mycompany.webapp.dto.BoardDto;
import com.mycompany.webapp.dto.ReviewDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	private ReviewDao reviewDao;
	
	public int writeBoard(BoardDto board) {
		return boardDao.insertBoard(board);
	}
	
	public int writeReview(ReviewDto review) {
		return reviewDao.insertReview(review);
	}
	
	public BoardDto getBoard(int boardNo) {
		return boardDao.selectBoardByNo(boardNo);
	}
	
	public ReviewDto getReview(int boardNo) {
		return reviewDao.selectReviewByNo(boardNo);
	}

	public int modifyBoard(BoardDto board) {
		return boardDao.updateBoard(board);
	}
	public int modifyReview(ReviewDto review) {
		return reviewDao.updateReview(review);
	}

	public int addBoardHitCnt(int boardNo) {
		return boardDao.updateHitCntByNo(boardNo);
	}
	
	public int addReviewHitCnt(int boardNo) {
		return reviewDao.updateHitCntByNo(boardNo);
	}
	

	public int removeBoard(int boardNo) {
		return boardDao.deleteBoard(boardNo);
		
	}

	public int removeReview(int boardNo) {
		return reviewDao.deleteReview(boardNo);
	}

	
}
