package com.mycompany.webapp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dto.BoardDto;
import com.mycompany.webapp.dto.ReviewDto;
import com.mycompany.webapp.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/Board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/create_board")
	public BoardDto createBoard(BoardDto board, Authentication authentication) {
		// 첨부파일 넘어왔을 때 처리하는 if문
		if (board.getBattachFile() != null && !board.getBattachFile().isEmpty()) {
			MultipartFile mf = board.getBattachFile();
			// 파일 이름을 설정
			board.setBattachOname(mf.getOriginalFilename());
			// 파일 종류를 설정
			board.setBattachType(mf.getContentType());
			try {
				// 파일 데이터를 설정
				board.setBattachData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// 이미지 파일이 넘어왔을 때 처리하는 if문
		if (board.getBattachImg() != null && !board.getBattachImg().isEmpty()) {
			MultipartFile mf = board.getBattachImg();
			// 이미지 이름을 설정
			board.setImgOname(mf.getOriginalFilename());
			// 이미지 종류를 설정
			board.setImgType(mf.getContentType());
			try {
				// 이미지 데이터를 설정
				board.setImgData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// board의 작성자를 세팅
		board.setMemberId(authentication.getName());
		// DB에 저장해주는 코드
		boardService.writeBoard(board);

		// 포스트맨 확인을 위해 null값 처리
		board.setBattachFile(null);
		board.setBattachData(null);
		board.setBattachImg(null);
		board.setImgData(null);
		return board;
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/create_review")
	public ReviewDto createReview(ReviewDto review, Authentication authentication) {
		// 첨부파일 넘어왔을 때 처리하는 if문
		if (review.getBattachFile() != null && !review.getBattachFile().isEmpty()) {
			MultipartFile mf = review.getBattachFile();
			// 파일 이름을 설정
			review.setBattachOname(mf.getOriginalFilename());
			// 파일 종류를 설정
			review.setBattachType(mf.getContentType());
			try {
				// 파일 데이터를 설정
				review.setBattachData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// 이미지 파일이 넘어왔을 때 처리하는 if문
		if (review.getBattachImg() != null && !review.getBattachImg().isEmpty()) {
			MultipartFile mf = review.getBattachImg();
			// 이미지 이름을 설정
			review.setImgOname(mf.getOriginalFilename());
			// 이미지 종류를 설정
			review.setImgType(mf.getContentType());
			try {
				// 이미지 데이터를 설정
				review.setImgData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// board의 작성자를 세팅
		review.setMemberId(authentication.getName());
		// DB에 저장해주는 코드
		boardService.writeReview(review);

		// 포스트맨 확인을 위해 null값 처리
		review.setBattachFile(null);
		review.setBattachData(null);
		review.setBattachImg(null);
		review.setImgData(null);
		return review;
	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/read_board/{boardNo}")
	public BoardDto readBoard(@PathVariable int boardNo) {
		//조회수 수정
		boardService.addBoardHitCnt(boardNo);
		BoardDto board = boardService.getBoard(boardNo);
		// JSON으로 변환되지 않는 필드는 null처리
		board.setBattachData(null);
		board.setImgData(null);
		return board;
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/read_review/{boardNo}")
	public ReviewDto readReview(@PathVariable int boardNo) {
		//조회수 수정
		boardService.addReviewHitCnt(boardNo);
		ReviewDto review = boardService.getReview(boardNo);
		// JSON으로 변환되지 않는 필드는 null처리
		review.setBattachData(null);
		review.setImgData(null);
		return review;
	}
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PutMapping("/update_board")
	public BoardDto updateBoard(BoardDto board) {
		// 첨부파일 넘어왔을 때 처리하는 코드
		if (board.getBattachFile() != null && !board.getBattachFile().isEmpty()) {
			MultipartFile mf = board.getBattachFile();
			// 파일 이름을 설정
			board.setBattachOname(mf.getOriginalFilename());
			// 파일 종류를 설정
			board.setBattachType(mf.getContentType());
			try {
				// 파일 데이터를 설정
				board.setBattachData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// 이미지 파일이 넘어왔을 때 처리하는 코드
		if (board.getBattachImg() != null && !board.getBattachImg().isEmpty()) {
			MultipartFile mf = board.getBattachImg();
			// 이미지 이름을 설정
			board.setImgOname(mf.getOriginalFilename());
			// 이미지 종류를 설정
			board.setImgType(mf.getContentType());
			try {
				// 이미지 데이터를 설정
				board.setImgData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// 수정하기
		boardService.modifyBoard(board);
		// 수정된 내용의 Board 객체 얻기
		board = boardService.getBoard(board.getBoardNo());
		// JSON으로 변환되지 않는 필드는 null 처리
		board.setBattachData(null);
		board.setImgData(null);
		return board;
	}
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PutMapping("/update_review")
	public ReviewDto updateReview(ReviewDto review) {
		// 첨부파일 넘어왔을 때 처리하는 코드
		if (review.getBattachFile() != null && !review.getBattachFile().isEmpty()) {
			MultipartFile mf = review.getBattachFile();
			// 파일 이름을 설정
			review.setBattachOname(mf.getOriginalFilename());
			// 파일 종류를 설정
			review.setBattachType(mf.getContentType());
			try {
				// 파일 데이터를 설정
				review.setBattachData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// 이미지 파일이 넘어왔을 때 처리하는 코드
		if (review.getBattachImg() != null && !review.getBattachImg().isEmpty()) {
			MultipartFile mf = review.getBattachImg();
			// 이미지 이름을 설정
			review.setImgOname(mf.getOriginalFilename());
			// 이미지 종류를 설정
			review.setImgType(mf.getContentType());
			try {
				// 이미지 데이터를 설정
				review.setImgData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// 수정하기
		boardService.modifyReview(review);
		// 수정된 내용의 Board 객체 얻기
		review = boardService.getReview(review.getBoardNo());
		// JSON으로 변환되지 않는 필드는 null 처리
		review.setBattachData(null);
		review.setImgData(null);
		return review;
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@DeleteMapping("/delete_board/{boardNo}")
	public String deleteBoard(@PathVariable int boardNo) {
		String result = "fail";
		if(boardService.removeBoard(boardNo) == 1) {
			result = "success";
		}
		return result;
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@DeleteMapping("/delete_review/{boardNo}")
	public String deleteReview(@PathVariable int boardNo) {
		String result = "fail";
		if(boardService.removeReview(boardNo) == 1) {
			result = "success";
		}
		return result;
	}
}
