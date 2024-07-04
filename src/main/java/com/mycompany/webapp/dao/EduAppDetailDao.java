package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduAppDetailDao {

	int selectIsEduApplAlrdyExist(String memberId, int programNo);

	int insertEduApplDtl(String memberId, int programNo);

}
