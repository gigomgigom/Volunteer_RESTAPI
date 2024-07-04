package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.EduProgramDto;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.SearchIndex;

@Mapper
public interface EduProgramDao {

	int insertEduProgram(EduProgramDto eduProgram);

	EduProgramDto selectEduProgramByNo(int programNo);

	int updateEduProgram(EduProgramDto eduProgram);

	int updateEduProgramEnabled(int programNo);

	int selectTotalRows(SearchIndex searchIndex);

	List<EduProgramDto> selectEduPrgmList(SearchIndex searchIndex, Pager pager);

	int updateApplyCnt(boolean isFull, int programNo);

}
