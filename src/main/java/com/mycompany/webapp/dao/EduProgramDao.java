package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.EduProgramDto;

@Mapper
public interface EduProgramDao {

	int insertEduProgram(EduProgramDto eduProgram);

	EduProgramDto selectEduProgramByNo(int programNo);

}
