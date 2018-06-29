package cn.dlbdata.dj.service;

import java.util.List;

import cn.dlbdata.dj.db.vo.jlyz.BuildingVo;
import cn.dlbdata.dj.db.vo.jlyz.MemberStatVo;
import cn.dlbdata.dj.db.vo.jlyz.PartyBranchVo;
import cn.dlbdata.dj.db.vo.jlyz.SectionVo;
import cn.dlbdata.dj.service.base.IBaseService;

public interface IJlyzService extends IBaseService {
	/**
	 * 查询片区及楼宇信息
	 * @param id
	 * @return
	 */
	public SectionVo querySection(Long id);
	
	/**
	 * 根据楼宇编号获取楼宇信息及金领驿站新
	 * @param buildingCode
	 * @return
	 */
	BuildingVo queryBuilding(String buildingCode, String rootUrl);
	
	/**
	 * 查询楼宇党支部信息
	 * @param buildingCode
	 * @return
	 */
	public List<PartyBranchVo> queryPartyBranches(String buildingCode);
	
	/**
	 * 查询楼宇党支部统计信息
	 * @param buildingCode
	 * @return
	 */
	public MemberStatVo queryMemberStatistic(String buildingCode);
}
