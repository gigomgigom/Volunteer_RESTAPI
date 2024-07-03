package com.mycompany.webapp.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.MemberDto;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.SearchIndex;
import com.mycompany.webapp.dto.VolAppDetailDto;

@Mapper
public interface VolAppDetailDao {
	//봉사신청내역 추가
	public int insertVolAppDetail(VolAppDetailDto volAppDetail);
	//봉사신청 일자 추가
	public int insertVolAppSch(String memberId, int programNo, Date date);
	//이미 신청한 봉사프로그램인지 확인
	public int selectExistingDetail(VolAppDetailDto volAppDetail);
	//봉사 신청내역 페이저(총 갯수가져오기)
	public int selectVolApplTotalCnt(String memberId, SearchIndex searchIndex);
	//봉사 신청내역 목록 가져오기
	public List<VolAppDetailDto> selectVolApplyDetailList(String memberId, SearchIndex searchIndex, Pager pager);
	//봉사 신청내역 상태 수정(단순 상태만 수정)
	public int updateVolAppStts(VolAppDetailDto volAppDtlDto);
	//봉사실적 승인 요청
	public int updateVolApplToRqstPerform(VolAppDetailDto volAppDtlDto);
	//봉사프로그램의 신청인 목록 가져오기
	public List<MemberDto> selectApplicantList(int programNo);
	//봉사 실적 승인요청 총갯수 가져오기
	public int selectPerformRqstCnt();
	//봉사 실적 승인요청 목록 가져오기
	public List<VolAppDetailDto> selectPerformRqstList(Pager pager);

}
