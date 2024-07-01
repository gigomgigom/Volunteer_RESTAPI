package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.VolProgramDto;

@Mapper
public interface VolProgramDao {

	int insertVolProgram(VolProgramDto volProgram);

	VolProgramDto selectVolProgramByNo(int programNo);

	int updateVolProgram(VolProgramDto volProgram);

}
