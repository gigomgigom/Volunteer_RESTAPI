package com.mycompany.webapp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.SearchIndex;
import com.mycompany.webapp.dto.VolProgramDto;
import com.mycompany.webapp.service.VolProgramService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/VolProgram")
public class VolProgramContoller {

	@Autowired
	private VolProgramService volService;

	// 봉사 프로그램 목록 조회
	@GetMapping("/get_vol_program_list")
	public Map<String, Object> list(SearchIndex searchIndex) {
		log.info(searchIndex.toString());
		Map<String, Object> map = new HashMap<>();
		int totalRows = volService.getVolCount(searchIndex);
		// Pager객체 생성
		Pager pager = new Pager(10, 5, totalRows, searchIndex.getPageNo());

		// Pager객체에 해당하는 목록들을 가져온다.
		List<VolProgramDto> volList = volService.getVolList(searchIndex, pager);

		// 응답 생성
		map.put("result", "success");
		map.put("volList", volList);
		map.put("pager", pager);

		return map;
	}

	// 봉사 프로그램 상세 조회
	@GetMapping("/get_vol_program_detail")
	public Map<String, Object> getVolProgramDetail(int programNo) {

		// 요청시 받아온 번호에 해당하는 봉사 프로그램 정보를 가져온다.
		VolProgramDto volDto = volService.getVolPgrmByPgrmNo(programNo);

		// 응답 생성
		Map<String, Object> map = new HashMap<>();

		// 만약 DB에서 찾은 값이 존재한다면
		if (volDto != null) {
			volDto.setBattachData(null);
			volDto.setImgData(null);
			if (volDto.getEnabled() == 0) { // 만약 비활성화(삭제)된 봉사 프로그램일 경우
				map.put("result", "diabledPgrm"); // 비활성화된 Dto객체라는걸 알려주기
				return map;
			} else {
				// 성공 메세지 및 결과 응답
				map.put("result", "success");
				map.put("volProgram", volDto);
				return map;
			}
		} else {
			// 실패 메세지 응답
			map.put("result", "failed");
			return map;
		}
	}

	// 봉사 프로그램 첨부파일 다운로드
	@GetMapping("/download_vol_pgrm_battach_file")
	public void downloadBattach(int programNo, HttpServletResponse response) {
		VolProgramDto volDto = volService.getVolPgrmByPgrmNo(programNo);
		try {
			String fileName = new String(volDto.getBattachOname().getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 응답 Contents타입 설정
		response.setContentType(volDto.getBattachType());
		// 응답 바디에 파일 데이터를 출력
		OutputStream os;
		try {
			os = response.getOutputStream();
			os.write(volDto.getBattachData());
			os.flush();
			os.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	// 봉사 프로그램 이미지 다운로드 및 출력
	@GetMapping("/download_vol_pgrm_img_file")
	public void downloadImg(int programNo, HttpServletResponse response) {
		VolProgramDto volDto = volService.getVolPgrmByPgrmNo(programNo);
		try {
			String fileName = new String(volDto.getImgOname().getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 응답 Contents타입 설정
		response.setContentType(volDto.getImgType());
		// 응답 바디에 파일 데이터를 출력
		OutputStream os;
		try {
			os = response.getOutputStream();
			os.write(volDto.getImgData());
			os.flush();
			os.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
