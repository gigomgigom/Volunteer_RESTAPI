package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.EduProgramDto;

@Mapper
public interface EduProgramDao {

	public int insertEduProgram(EduProgramDto eduProgram);

	public int updateEduProgram(EduProgramDto eduProgramDto);
}
