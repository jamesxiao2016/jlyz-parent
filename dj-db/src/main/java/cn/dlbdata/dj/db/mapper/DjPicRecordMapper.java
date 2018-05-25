package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjPicRecord;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DjPicRecordMapper extends Mapper<DjPicRecord> {
    List<Long> getIdsByTableNameAndRecordId(@Param("tableName") String tableName, @Param("recordId") Long recordId);
}