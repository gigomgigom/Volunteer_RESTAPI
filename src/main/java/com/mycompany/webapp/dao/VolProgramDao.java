package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.SearchIndex;
import com.mycompany.webapp.dto.VolProgramDto;

@Mapper
public interface VolProgramDao {
	//봉사프로그램 추가하기
	public int insertVolProgram(VolProgramDto volProgram);
	//검색한 봉사프로그램의 총 갯수 가져오기
	public int selectVolCount(SearchIndex searchIndex);
	//검색한 봉사프로그램 목록에서 페이징 결과를 가져온다.
	public List<VolProgramDto> selectVolList(SearchIndex searchIndex, Pager pager);
	//봉사프로그램 번호에 해당하는 봉사프로그램 상세정보 가져오기
	public VolProgramDto selectVolProgramByNo(int programNo);
	//봉사프로그램 수정하기
	public int updateVolProgram(VolProgramDto volProgram);
	//봉사프로그램 삭제(비활성화)하기
	public int updateVolProgramEnabled(int programNo);
	//봉사프로그램 총 신청인 수 업데이트
	public void updateVolProgramApplCnt(boolean isFull, int programNo);

}
