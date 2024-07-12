package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.Pager;

@Mapper
public interface MemberDao {
	public MemberDto selectByMemberId(String memberId);
	public int insertMember(MemberDto member);
	public int selectTotMemCnt();
	public List<MemberDto> selectMemList(Pager pager);
	public int updateMemberInfo(MemberDto member);
	public int updateMemberPassword(String memberId, String newPassword);
	public int updateMemberEnabled(String memberId, int enabled);
	public MemberDto selectMemberByNameAndEmail(String memberName, String email);
	public int countById(String memberId);
}
