package com.mycompany.webapp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.webapp.dto.BoardDto;
import com.mycompany.webapp.dto.LikeUnlikeDto;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.ReviewDto;
import com.mycompany.webapp.dto.ReviewReplyDto;
import com.mycompany.webapp.dto.SearchIndex;
import com.mycompany.webapp.dto.VolProgramDto;
import com.mycompany.webapp.service.BoardService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j

@RequestMapping("/Board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	//게시글 추가
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/create_board")
	public BoardDto createBoard(BoardDto boardDto, Authentication authentication) {
		// 첨부파일 넘어왔을 때 처리하는 if문
		if (boardDto.getBattachFile() != null && !boardDto.getBattachFile().isEmpty()) {
			MultipartFile mf = boardDto.getBattachFile();
			// 파일 이름을 설정
			boardDto.setBattachOname(mf.getOriginalFilename());
			// 파일 종류를 설정
			boardDto.setBattachType(mf.getContentType());
			try {
				// 파일 데이터를 설정
				boardDto.setBattachData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// 이미지 파일이 넘어왔을 때 처리하는 if문
		if (boardDto.getBattachImg() != null && !boardDto.getBattachImg().isEmpty()) {
			MultipartFile mf = boardDto.getBattachImg();
			// 이미지 이름을 설정
			boardDto.setImgOname(mf.getOriginalFilename());
			// 이미지 종류를 설정
			boardDto.setImgType(mf.getContentType());
			try {
				// 이미지 데이터를 설정
				boardDto.setImgData(mf.getBytes());
			} catch (IOException e) {
			}
		}
		// board의 작성자를 세팅
		boardDto.setMemberId(authentication.getName());
		log.info("게시글정보 : " + boardDto);
		// DB에 저장해주는 코드
		boardService.writeBoard(boardDto);

		// 포스트맨 확인을 위해 null값 처리
		boardDto.setBattachFile(null);
		boardDto.setBattachData(null);
		boardDto.setBattachImg(null);
		boardDto.setImgData(null);
		return boardDto;
	}
	
	//리뷰글 추가
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
	//게시글 상세조회
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/read_board/{boardNo}")
	public BoardDto readBoard(@PathVariable int boardNo) {
		// 조회수 수정
		boardService.addBoardHitCnt(boardNo);
		BoardDto board = boardService.getBoard(boardNo);
		// JSON으로 변환되지 않는 필드는 null처리
		board.setBattachData(null);
		board.setImgData(null);
		return board;
	}
	//리뷰글 상세조회
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/read_review/{boardNo}")
	public ReviewDto readReview(@PathVariable int boardNo) {
		// 조회수 수정
		boardService.addReviewHitCnt(boardNo);
		ReviewDto review = boardService.getReview(boardNo);
		// JSON으로 변환되지 않는 필드는 null처리
		review.setBattachData(null);
		review.setImgData(null);
		return review;
	}
	//게시글 수정
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
	//리뷰글 수정하기
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
	//게시글 삭제하기
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@DeleteMapping("/delete_board/{boardNo}")
	public String deleteBoard(@PathVariable int boardNo) {
		String result = "fail";
		if (boardService.removeBoard(boardNo) == 1) {
			result = "success";
		}
		return result;
	}
	//리뷰글 삭제하기
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@DeleteMapping("/delete_review/{boardNo}")
	public String deleteReview(@PathVariable int boardNo) {
		String result = "fail";
		if (boardService.removeReview(boardNo) == 1) {
			result = "success";
		}
		return result;
	}
	//댓글, 답글 작성하기
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/write_review_reply")
	public ReviewReplyDto writeReviewReply(ReviewReplyDto reviewReply, Authentication authentication) {
		// 댓글작성자 세팅
		reviewReply.setMemberId(authentication.getName());
		log.info("댓글 : " + reviewReply);
		// 댓글 등록
		boardService.registerReviewReply(reviewReply);
		return reviewReply;
	}

	// 댓글과 답글 리스트 조회
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/get_review_reply_list/{boardNo}")
	public List<ReviewReplyDto> reviewReplyList(@PathVariable int boardNo) {
		Map<String, Object> map = new HashMap<>();
		List<ReviewReplyDto> commentList = boardService.getCommentList(boardNo);
		return commentList;
	}

	// 리뷰글 리스트 조회(페이징)
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/get_review_list")
	public Map<String, Object> reviewList(SearchIndex searchIndex) {
		Map<String, Object> map = new HashMap<>();
		int totalRows = boardService.getReviewCount(searchIndex);
		if(searchIndex.getPageNo() == 0) {
			searchIndex.setPageNo(1);
		}
		Pager pager = new Pager(10, 5, totalRows, searchIndex.getPageNo());
		List<ReviewDto> reviewList = boardService.getReviewList(searchIndex, pager);
		// 응답 생성
		map.put("result", "success");
		map.put("reviewList", reviewList);
		map.put("pager", pager);
		return map;
	}
	// 게시글 리스트 조회(페이징)
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/get_board_list")
	public Map<String, Object> boardList(SearchIndex searchIndex) {
		Map<String, Object> map = new HashMap<>();
		int totalRows = boardService.getBoardCount(searchIndex);
		if(searchIndex.getPageNo() == 0) {
			searchIndex.setPageNo(1);
		}
		Pager pager = new Pager(10, 5, totalRows, searchIndex.getPageNo());
		List<BoardDto> boardList = boardService.getBoardList(searchIndex, pager);
		
		map.put("result", "success");
		map.put("boardList", boardList);
		map.put("pager", pager);
		
		return map;
	}
	// 리뷰게시판 추천/비추천 기능
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/review_like_unlike")
	public Map<String, Object> reviewLikeUnlike(ReviewDto review, Authentication authentication) {
		//맵을 만들기 위한 코드
		Map<String, Object> map = new HashMap<>();
		//회원의 아이디를 얻어오는 코드
		String memberId = authentication.getName();
		
		log.info("memberId : " + memberId + "reveiw :" + review);
		//현재 받아온 매개변수로 테이블이 존재하는지 확인하는 코드
		LikeUnlikeDto likeTable = boardService.getLikeTable(review, memberId);
		
		//이미 테이블이 존재하고 있다면 데이터의 값을 변경해주는 코드
		if(likeTable != null) {
			//테이블이 존재하고 있는데, 다른 값이 넘어왔을 경우 업데이트를 해준다.
			if(likeTable.getLikeStts() != review.getLikeStts()) {
				boardService.modifyLikeTable(review, memberId);
				map.put("result", "success");
				//테이블이 존재하고 있는데, 같은 값이 넘어왔을 경우 업데이트를 하지 않는다
			}else if(likeTable.getLikeStts() == review.getLikeStts()) {
				map.put("result", "fail");
			}
			//테이블이 존재하고 있지 않을 경우
		}else if(likeTable == null) {
			//테이블을 새로 만들어주고 데이터를 넣어준다.
			boardService.createLikeTable(review, memberId);
			map.put("result", "success");
		}
		//테이블을 다시 조회하고 조회한 값을 map에 넣어서 보내준다.
		likeTable = boardService.getLikeTable(review, memberId);
		map.put("likeTable", likeTable);
		return map;
	}
	// 게시글마다 추천, 비추천 수 조회하는 기능
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/like_unlike_count/{boardNo}")
	public Map<String,Integer> likeUnlikeCount(@PathVariable int boardNo) {
		Map<String, Integer> map = new HashMap<>();
		int likeCount = boardService.getLikeCount(boardNo);
		int unLikeCount = boardService.getUnlikeCount(boardNo);
		int likeUnlikeCount = likeCount - unLikeCount;
		map.put("likeCount", likeCount);
		map.put("unLikeCount", unLikeCount);
		map.put("likeUnlikeCount", likeUnlikeCount);
 		return map;
	}
	
	//회원이 작성한 후기 리스트 조회
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/get_review_list_member")
	public Map<String, Object> reviewListByMember(SearchIndex searchIndex, Authentication authentication){
		Map<String, Object> map = new HashMap<>();
		
		int totalRows = boardService.getReviewCountByMember(searchIndex, authentication.getName());
		if(searchIndex.getPageNo() == 0) {
			searchIndex.setPageNo(1);
		}
		Pager pager = new Pager(10, 5, totalRows, searchIndex.getPageNo());
		List<BoardDto> reviewListByMember = boardService.getReviewListByMember(searchIndex, pager, authentication.getName());
		
		map.put("result", "success");
		map.put("reviewListByMember", reviewListByMember);
		map.put("pager", pager);
		return map;
	}
	//통합게시판 첨부파일 다운로드
	@GetMapping("/download_board_battach_file")
	public void boardDownloadBattach(int boardNo, HttpServletResponse response) {
		BoardDto board = boardService.getBoard(boardNo);
		if(board != null) {
			try {
				String fileName = new String(board.getBattachOname().getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 응답 Contents타입 설정
			response.setContentType(board.getBattachType());
			// 응답 바디에 파일 데이터를 출력
			OutputStream os;
			try {
				os = response.getOutputStream();
				os.write(board.getBattachData());
				os.flush();
				os.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
	//통합게시판 이미지 다운로드, 이미지 출력
	@GetMapping("/download_board_img_file")
	public void boardDownloadImg(int boardNo, HttpServletResponse response) {
		BoardDto board = boardService.getBoard(boardNo);
		if(board != null) {
			try {
				String fileName = new String(board.getImgOname().getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 응답 Contents타입 설정
			response.setContentType(board.getImgType());
			// 응답 바디에 파일 데이터를 출력
			OutputStream os;
			try {
				os = response.getOutputStream();
				os.write(board.getImgData());
				os.flush();
				os.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
	//리뷰게시판 첨부파일 다운로드
	@GetMapping("/download_review_battach_file")
	public void reviewDownloadBattach(int boardNo, HttpServletResponse response) {
		ReviewDto review = boardService.getReview(boardNo);
		if(review != null) {
			try {
				String fileName = new String(review.getBattachOname().getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 응답 Contents타입 설정
			response.setContentType(review.getBattachType());
			// 응답 바디에 파일 데이터를 출력
			OutputStream os;
			try {
				os = response.getOutputStream();
				os.write(review.getBattachData());
				os.flush();
				os.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
	//리뷰게시판 이미지 다운로드, 이미지 출력
	@GetMapping("/download_review_img_file")
	public void reviewDownloadImg(int boardNo, HttpServletResponse response) {
		ReviewDto review = boardService.getReview(boardNo);
		if(review != null) {
			try {
				String fileName = new String(review.getImgOname().getBytes("UTF-8"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 응답 Contents타입 설정
			response.setContentType(review.getImgType());
			// 응답 바디에 파일 데이터를 출력
			OutputStream os;
			try {
				os = response.getOutputStream();
				os.write(review.getImgData());
				os.flush();
				os.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
}
