package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.EduAppDetailDto;
import com.mycompany.webapp.dto.Pager;

@Mapper
public interface EduAppDetailDao {

	int selectIsEduApplAlrdyExist(String memberId, int programNo);

	int insertEduApplDtl(String memberId, int programNo);

	int updateEduApplDtlStts(String memberId, int programNo, int sttsNo);

	int selectEduApplCnt(String memberId);

	List<EduAppDetailDto> selectEduApplDtlList(String memberId, Pager pager);

}
