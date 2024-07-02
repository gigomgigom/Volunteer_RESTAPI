package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.ReviewDto;
import com.mycompany.webapp.dto.ReviewReplyDto;

@Mapper
public interface ReviewDao {

	int insertReview(ReviewDto review);

	ReviewDto selectReviewByNo(int boardNo);

	int updateHitCntByNo(int boardNo);

	int updateReview(ReviewDto review);

	int deleteReview(int boardNo);

	int insertReviewReply(ReviewReplyDto reviewReply);

}
