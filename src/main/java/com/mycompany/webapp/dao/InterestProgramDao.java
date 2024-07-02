package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.VolProgramDto;

@Mapper
public interface InterestProgramDao {

	public int selectIntrPrgmHistory(int programNo, String memberId);

	public int insertIntrPrgmHistory(int programNo, String memberId);

	public int deleteIntrPrgmHistory(int programNo, String memberId);
	
	public int getIntrPrgmCnt(String memberId);

	public List<VolProgramDto> getIntrPrgmList(String memberId, Pager pager);

}
