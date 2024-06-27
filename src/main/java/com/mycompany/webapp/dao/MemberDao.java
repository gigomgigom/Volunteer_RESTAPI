package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;
import com.mycompany.webapp.dto.MemberDto;

@Mapper
public interface MemberDao {
	public MemberDto selectByMemberId(String memberId);
	public int insertMember(MemberDto member);
}
