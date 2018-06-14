package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface DjUserMapper extends Mapper<DjUser> {
    boolean existWithUserName(@Param("userName") String userName,@Param("id") Long id);
}