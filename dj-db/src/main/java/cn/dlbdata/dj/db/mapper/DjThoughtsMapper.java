package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjThoughts;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface DjThoughtsMapper extends Mapper<DjThoughts> {
    List<Long> getByDeptIdAndTypeIdAndSubTypeIdAndYear(@Param("deptId")long deptId,
                                                       @Param("typeId") Long typeId,
                                                       @Param("yearTimeStart")Date yearTimeStart,
                                                       @Param("yearTimeEnd") Date yearTimeEnd);
}