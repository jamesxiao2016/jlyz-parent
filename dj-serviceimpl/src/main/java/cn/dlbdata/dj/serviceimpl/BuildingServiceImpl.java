package cn.dlbdata.dj.serviceimpl;

import cn.dlbdata.dj.common.core.exception.BusinessException;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjBuildingMapper;
import cn.dlbdata.dj.db.pojo.DjBuilding;
import cn.dlbdata.dj.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.dlbdata.dj.db.dto.building.*;

import java.util.Date;

@Service
public class BuildingServiceImpl implements IBuildingService {

    @Autowired
    private DjBuildingMapper buildingMapper;


    /**
     * 新增楼宇
     * @param dto
     * @return
     */
    @Override
    public boolean add(BuildingAddOrUpdateDto dto) {

        boolean existWithCode = buildingMapper.existWithCode(dto.getCode(),null);
        if (existWithCode) {
            throw new BusinessException("已存在同编号的楼宇!",CoreConst.ResultCode.Forbidden.getCode());
        }
        boolean existWithName = buildingMapper.existWithName(dto.getName(),null);
        if (existWithName) {
            throw new BusinessException("已存在同名的楼宇!",CoreConst.ResultCode.Forbidden.getCode());
        }
//        DjBuilding building = buildingMapStruct.dtoToEntity(dto);
        DjBuilding building = new DjBuilding();

        building.setDjSectionId( dto.getSectionId() );
        building.setName( dto.getName() );
        building.setFloorNum( dto.getFloorNum() );
        building.setAddress( dto.getAddress() );
        building.setCode( dto.getCode() );
        building.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
        building.setId(DigitUtil.generatorLongId());
        building.setCreateTime(new Date());
        buildingMapper.insert(building);
        return false;
    }

    /**
     * 楼宇信息修改
     *
     * @param id  楼宇Id
     * @param dto dto
     */
    @Transactional
    @Override
    public boolean update(Long id, BuildingAddOrUpdateDto dto) {
        DjBuilding building = buildingMapper.selectByPrimaryKey(id);
        if (building == null) {
            throw new BusinessException("楼宇不存在!",CoreConst.ResultCode.NotFound.getCode());
        }
        if (building.getStatus() != DlbConstant.BASEDATA_STATUS_VALID) {
            throw new BusinessException("楼宇状态不是有效状态!",CoreConst.ResultCode.Forbidden.getCode());
        }

        boolean existWithCode = buildingMapper.existWithCode(dto.getCode(),building.getId());
        if (existWithCode) {
            throw new BusinessException("已存在同编号的楼宇!",CoreConst.ResultCode.Forbidden.getCode());
        }
        boolean existWithName = buildingMapper.existWithName(dto.getName(),building.getId());
        if (existWithName) {
            throw new BusinessException("已存在同名的楼宇!",CoreConst.ResultCode.Forbidden.getCode());
        }
        building.setDjSectionId( dto.getSectionId() );
        building.setName( dto.getName() );
        building.setFloorNum( dto.getFloorNum() );
        building.setAddress( dto.getAddress() );
        building.setCode( dto.getCode() );
        buildingMapper.updateByPrimaryKey(building);
        return true;
    }

    /**
     * 作废楼宇.
     *
     * @param id 楼宇Id
     * @return
     */
    @Override
    public boolean invalid(Long id) {
        DjBuilding building = buildingMapper.selectByPrimaryKey(id);
        if (building == null) {
            throw new BusinessException("楼宇不存在!",CoreConst.ResultCode.NotFound.getCode());
        }
        if (building.getStatus() != DlbConstant.BASEDATA_STATUS_VALID) {
            throw new BusinessException("楼宇状态已经是作废状态!",CoreConst.ResultCode.Forbidden.getCode());
        }
        building.setStatus(DlbConstant.BASEDATA_STATUS_INVALID);
        buildingMapper.updateByPrimaryKey(building);
        return true;
    }
}
