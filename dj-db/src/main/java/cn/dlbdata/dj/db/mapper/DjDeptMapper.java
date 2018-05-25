package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.vo.party.BranchDeptInfoVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DjDeptMapper extends Mapper<DjDept> {
    // TODO sql里面的支部负责人姓名暂时没查
    List<BranchDeptInfoVo>getBranchDeptInfoBySectionId(Long sectionId);
}