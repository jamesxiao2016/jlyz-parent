package cn.dlbdata.dj.db.mapper;

import cn.dlbdata.dj.db.pojo.DjStudy;
import cn.dlbdata.dj.db.vo.study.AdminStudyDetailVo;
import cn.dlbdata.dj.db.vo.study.ReviewScheduleListVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DjStudyMapper extends Mapper<DjStudy> {
	List<ReviewScheduleListVo> getReviewScheduleList(Map<String , Object> map);
	
	AdminStudyDetailVo getAdminStudyDetail(@Param("id") Long id);
}