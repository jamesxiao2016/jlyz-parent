package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjPicRecord;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DjPicRecordMapper extends Mapper<DjPicRecord> {
    //TODO SQL 没写
    List<Long> getIdsBytableNameAndRecordId(String tableName,Long recordId);
}