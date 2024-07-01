package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.VolProgramDto;

@Mapper
public interface VolProgramDao {

	public int insertVolProgram(VolProgramDto volProgram);

	public int selectVolCount();
	
	public List<VolProgramDto> selectVolList(Pager pager);

	VolProgramDto selectVolProgramByNo(int programNo);

	int updateVolProgram(VolProgramDto volProgram);

}
