package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.db.vo.UserResVo;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DjUserMapper extends Mapper<DjUser> {
    boolean existWithUserName(@Param("userName") String userName,@Param("id") Long id);

    List<DjUser> getByRoleIdAndDeptIdIn(@Param("roleId") Long roleId,@Param("deptIds") List<Long> deptIds);
    
    UserResVo getUserFromAccountAndPwd(@Param("account") String account,@Param("password") String password);
}