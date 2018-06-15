package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DjUserMapper extends Mapper<DjUser> {
    boolean existWithUserName(@Param("userName") String userName,@Param("id") Long id);

    List<DjUser> getByRoleIdAndDeptIdIn(@Param("roleId") Long roleId,@Param("deptIds") List<Long> deptIds);
}