package cn.dlbdata.dj.service;

import cn.dlbdata.dj.common.core.util.poi.ExcelReplaceDataVO;

import java.util.List;
import java.util.Map;

public interface PartyMemberDueService {

    Map<String, List<ExcelReplaceDataVO>> saveDues(List<List<Object>> data);


}
