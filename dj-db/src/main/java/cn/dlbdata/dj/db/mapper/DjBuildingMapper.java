package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjBuilding;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface DjBuildingMapper extends Mapper<DjBuilding> {
    boolean existWithCode(@Param("code") String code,@Param("id") Long id);
    boolean existWithName(@Param("name") String name,@Param("id") Long id);
}