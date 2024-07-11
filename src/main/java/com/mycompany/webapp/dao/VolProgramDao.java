package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.VolProgramDto;

@Mapper
public interface VolProgramDao {

	int insertVolProgram(VolProgramDto volProgram);

	//전체 행수 가져오기
	public int getcount();

	//한 페이지 가져오기
	public List<VolProgramDto> selectByPage(Pager pager);

	//봉사프로그램 상세 가져오기
	public VolProgramDto getVolProgram(int programNo);
	
	//메인 페이지의 최신 봉사 목록 (캐러셀)
	public  List<VolProgramDto> getVolProgramToMain();

}
