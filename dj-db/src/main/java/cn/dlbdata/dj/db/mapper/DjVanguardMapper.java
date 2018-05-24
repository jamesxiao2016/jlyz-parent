package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjVanguard;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DjVanguardMapper extends Mapper<DjVanguard> {
    /**
     *判断党员先锋作用审核状态是否为未审核
     */
    int countUnAuditByPtMemberIdAndType(Long id);

    int countByPtMemberIdStatus(@Param("pid") Long pid,@Param("status") int status);
}