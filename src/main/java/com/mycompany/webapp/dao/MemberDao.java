package com.mycompany.webapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.webapp.dto.EduOfUserDto;
import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.VolOfUserDto;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.TodayVolunteersDto;

@Mapper
public interface MemberDao {
    public MemberDto selectByMemberId(String memberId);
    public int insertMember(MemberDto member);
    public int getMemberCount();
    public List<MemberDto> getMemberList(Pager pager);
    public MemberDto findIdByPhoneNum(@Param("memberName") String memberName, @Param("tel") String tel);
    public MemberDto findIdByEmail(@Param("memberName") String memberName, @Param("email") String email);

    public MemberDto findPwByPhoneNum(@Param("memberName") String memberName, @Param("memberId") String memberId, @Param("tel") String tel);
    
    //비밀번호 변경
    public int updatePw(@Param("memberId") String memberId, @Param("password") String password);
    
    //회원의 완료한 봉사시간, 봉사갯수 가져오기
    public VolOfUserDto getTotalVolOfUser(@Param("memberId") String memberId);
    
    //신청한 봉사 갯수 가져오기
    public VolOfUserDto getAppliedVolOfUser(@Param("memberId") String memberId);

    //신청한 교육 갯수 가져오기
    public EduOfUserDto getAppliedEduOfUser(@Param("memberId") String memberId);
    
    //오늘 봉사 활동하는 인원 수 (메인 페이지)
    public TodayVolunteersDto getTodayVolunteers();
}
