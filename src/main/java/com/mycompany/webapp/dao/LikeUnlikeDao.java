package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.LikeUnlikeDto;
import com.mycompany.webapp.dto.ReviewDto;

@Mapper
public interface LikeUnlikeDao {

	LikeUnlikeDto selectLikeTable(ReviewDto review, String memberId);

	int updateLikeTable(ReviewDto review, String memberId);

	int insertLikeTable(ReviewDto review, String memberId);

	int selectLikeCount(int boardNo);

	int selectUnLikeCount(int boardNo);
	
	
}
