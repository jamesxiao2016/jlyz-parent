package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjDiscipline;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

public interface DjDisciplineMapper extends Mapper<DjDiscipline> {
    Integer getOneByUserIdOrderByCreateTimeDesc(@Param("userId") Long userId,
                                                @Param("yearTimeStart")Date yearTimeStart,
                                                @Param("yearTimeEnd") Date yearTimeEnd);
}