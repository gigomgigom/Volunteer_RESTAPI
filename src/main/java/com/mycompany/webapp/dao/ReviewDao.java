package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.ReviewDto;
import com.mycompany.webapp.dto.ReviewReplyDto;
import com.mycompany.webapp.dto.SearchIndex;

@Mapper
public interface ReviewDao {

	int insertReview(ReviewDto review);

	ReviewDto selectReviewByNo(int boardNo);

	int updateHitCntByNo(int boardNo);

	int updateReview(ReviewDto review);

	int deleteReview(int boardNo);

	int insertReviewReply(ReviewReplyDto reviewReply);
	
	public List<ReviewReplyDto> selectReviewReplyByNo(int boardNo);

	int selectReviewCount(SearchIndex searchIndex);

	List<ReviewDto> selectReviewList(SearchIndex searchIndex, Pager pager);

}
