package com.mycompany.webapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.BoardDao;
import com.mycompany.webapp.dao.LikeUnlikeDao;
import com.mycompany.webapp.dao.ReviewDao;
import com.mycompany.webapp.dto.BoardDto;
import com.mycompany.webapp.dto.LikeUnlikeDto;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.ReviewDto;
import com.mycompany.webapp.dto.ReviewReplyDto;
import com.mycompany.webapp.dto.SearchIndex;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private ReviewDao reviewDao;
	@Autowired
	private LikeUnlikeDao likeUnlikeDao;
	
	//메인에 공지사항 출력
	public  List<BoardDto> getNoticeListToMain() {
		return boardDao.getNoticeListToMain();
	}
	
	
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

	public int registerReviewReply(ReviewReplyDto reviewReply) {
		return reviewDao.insertReviewReply(reviewReply);

	}

	public List<ReviewReplyDto> getCommentList(int boardNo) {
		// 게시판 번호에 맞는 모든 댓글리스트를 찾아온다.
		List<ReviewReplyDto> list = reviewDao.selectReviewReplyByNo(boardNo);
		// preReviewReply의 번호가 0번인 리스트를 모아놓을 배열리스트를 선언한다
		List<ReviewReplyDto> listForZero = new ArrayList<>();
		for (ReviewReplyDto reply : list) {
			if (reply.getPreReplyNo() == 0) {
				listForZero.add(reply);
			}
		}
		
		for (ReviewReplyDto firstReply : listForZero) {
			List<ReviewReplyDto> data = new ArrayList<>();
			for (ReviewReplyDto reply : list) {
				if (firstReply.getReplyNo() == reply.getPreReplyNo() && reply.getPreReplyNo() != 0) {
					data.add(reply);
				}
			}
			if(data.size() != 0) {
				firstReply.setSecondCommentList(data);
			}
			log.info("data : " + data);
			//firstReply.setSecondCommentList(data);
		}
		log.info("리스트 : " + listForZero);
		return listForZero;
	}

	public int getReviewCount(SearchIndex searchIndex) {
		return reviewDao.selectReviewCount(searchIndex);
	}

	public List<ReviewDto> getReviewList(SearchIndex searchIndex, Pager pager) {
		return reviewDao.selectReviewList(searchIndex, pager);
	}

	public int getBoardCount(SearchIndex searchIndex) {
		return boardDao.selectBoardCount(searchIndex);
	}

	public List<BoardDto> getBoardList(SearchIndex searchIndex, Pager pager) {
		return boardDao .selectBoardList(searchIndex, pager);
	}

	public LikeUnlikeDto getLikeTable(ReviewDto review, String memberId) {
		return likeUnlikeDao.selectLikeTable(review, memberId);
	}

	public int modifyLikeTable(ReviewDto review, String memberId) {
		return likeUnlikeDao.updateLikeTable(review, memberId);
		
	}

	public int createLikeTable(ReviewDto review, String memberId) {
		return likeUnlikeDao.insertLikeTable(review, memberId);
		
	}

	public int getLikeCount(int boardNo) {
		return likeUnlikeDao.selectLikeCount(boardNo);
	}

	public int getUnlikeCount(int boardNo) {
		return likeUnlikeDao.selectUnLikeCount(boardNo);
	}

	public int getReviewCountByMember(SearchIndex searchIndex, String memberId) {
		return reviewDao.selectReviewCountByMember(searchIndex, memberId);
	}

	public List<BoardDto> getReviewListByMember(SearchIndex searchIndex, Pager pager, String memberId) {
		return reviewDao.selectReviewListByMember(searchIndex, pager, memberId);
	}

}
