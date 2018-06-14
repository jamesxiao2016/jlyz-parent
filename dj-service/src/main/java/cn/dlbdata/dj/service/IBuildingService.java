package cn.dlbdata.dj.service;

import cn.dlbdata.dj.db.dto.building.*;

public interface IBuildingService {

    /**
     * 新增楼宇
     * @param dto
     * @return
     */
    boolean add(BuildingAddOrUpdateDto dto);

    /**
     * 楼宇信息修改
     * @param id 楼宇Id
     * @param dto dto
     */
    boolean update(Long id, BuildingAddOrUpdateDto dto);

    /**
     * 作废楼宇.
     * @param id 楼宇Id
     * @return
     */
    boolean invalid(Long id);

}
