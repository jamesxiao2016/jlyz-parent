package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjPartymemberDues;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DjPartymemberDuesMapper extends Mapper<DjPartymemberDues> {
    boolean existWithOrderCode(String orderCode);
    int batchInset(List<DjPartymemberDues> list);
}