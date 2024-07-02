package com.mycompany.webapp.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.dto.SearchIndex;
import com.mycompany.webapp.dto.VolAppDetailDto;

@Mapper
public interface VolAppDetailDao {

	public int insertVolAppDetail(VolAppDetailDto volAppDetail);

	public int insertVolAppSch(String memberId, int programNo, Date date);

	public int selectExistingDetail(VolAppDetailDto volAppDetail);

	public int selectVolApplTotalCnt(String memberId, SearchIndex searchIndex);

	public List<VolAppDetailDto> selectVolApplyDetailList(String memberId, SearchIndex searchIndex, Pager pager);

}
