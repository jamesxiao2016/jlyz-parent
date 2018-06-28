package cn.dlbdata.dj.serviceimpl;

import cn.dlbdata.dj.common.core.exception.BusinessException;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.constant.RoleEnum;
import cn.dlbdata.dj.db.dto.section.SectionAddOrUpdateDto;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjUserMapper;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjUser;
import cn.dlbdata.dj.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.service.ISectionService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SectionServiceImpl extends BaseServiceImpl implements ISectionService {

    @Autowired
    private DjSectionMapper sectionMapper;
    @Autowired
    private DjUserMapper userMapper;
    @Autowired
    private DjDeptMapper djDeptMapper;

    /**
     * 新增片区
     *
     * @return
     */
    @Override
    public Long addSection(SectionAddOrUpdateDto dto, UserVo user) {
        boolean exist = sectionMapper.existWithName(dto.getName(), null);
        if (exist) {
            throw new BusinessException("该片区名称已存在!", CoreConst.ResultCode.Forbidden.getCode());
        }
        DjSection section = new DjSection();
        section.setId(DigitUtil.generatorLongId());
        section.setName(dto.getName());
        section.setDescription(dto.getDescription());
        section.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
        section.setCreateTime(new Date());
        sectionMapper.insert(section);
        return section.getId();
    }

    @Override
    public Long deleteById(Long id) {
        if (id == null) {
            logger.error("id is null");
            return null;
        }
        sectionMapper.deleteByPrimaryKey(id);
        return id;
    }

    /**
     * 更新片区信息.
     *
     * @param id 片区Id
     * @return
     */
    @Override
    public boolean updateSection(SectionAddOrUpdateDto dto, Long id, UserVo user) {
        DjSection section = sectionMapper.selectByPrimaryKey(id);
        if (section == null) {
            throw new BusinessException("所选的片区不存在!", CoreConst.ResultCode.NotFound.getCode());
        }
        if (dto.getPrincipalId() != null) {
            DjUser newPrincipal = userMapper.selectByPrimaryKey(dto.getPrincipalId());
            if (newPrincipal == null) {
                throw new BusinessException("所选的片区负责人不存在!", CoreConst.ResultCode.NotFound.getCode());
            }
            //只有当前片区的党员才可以被设定为片区负责人
            DjDept dept = djDeptMapper.selectByPrimaryKey(newPrincipal.getDeptId());
            if (!dept.getDjSectionId().equals(section.getId())) {
                throw new BusinessException("只有本片区的人才能被设为本片区的负责人!",
                        CoreConst.ResultCode.Forbidden.getCode());
            }
            if (section.getPrincipalId() != null) {
                if (!section.getPrincipalId().equals(dto.getPrincipalId())) {
                    DjUser oldPrincipal = userMapper.selectByPrimaryKey(section.getPrincipalId());
                    //设置之前的片区负责人为普通党员
                    oldPrincipal.setRoleId(RoleEnum.PARTY.getId());
                    //设置传入的人为片区负责人
                    newPrincipal.setRoleId(RoleEnum.HEADER_OF_DISTRICT.getId());
                    //如果新片区负责人原来是片区下的某支部的党支部书记，则需要将新片区负责人所在的党支部的党支部书记信息清除掉
                    DjDept newPpalsDept = djDeptMapper.selectByPrimaryKey(newPrincipal.getDeptId());
                    if (newPpalsDept.getPrincipalId() != null) {
                        if (newPpalsDept.getPrincipalId().equals(newPrincipal.getId())) {
                            newPpalsDept.setPrincipalId(null);
                            newPpalsDept.setPrincipalName(null);
                            djDeptMapper.updateByPrimaryKey(newPpalsDept);
                        }
                    }

                    userMapper.updateByPrimaryKey(oldPrincipal);
                    section.setPrincipalName(newPrincipal.getUserName());
                    section.setPrincipalId(newPrincipal.getId());
                }
            } else {
                section.setPrincipalId(newPrincipal.getId());
                section.setPrincipalName(newPrincipal.getUserName());
                //设置传入的人为片区负责人
                newPrincipal.setRoleId(RoleEnum.HEADER_OF_DISTRICT.getId());
            }
            userMapper.updateByPrimaryKey(newPrincipal);
        } else {
            //清空片区负责人
            if (section.getPrincipalId() != null) {//如果原来有片区负责人则需要将原负责人的角色置为普通党员
                DjUser oldPrincipal = userMapper.selectByPrimaryKey(section.getPrincipalId());
                if (oldPrincipal != null) {
                    oldPrincipal.setRoleId(RoleEnum.PARTY.getId());
                    userMapper.updateByPrimaryKey(oldPrincipal);
                }
            }
            section.setPrincipalId(null);
            section.setPrincipalName(null);
        }

		section.setName(dto.getName());
		section.setDescription(dto.getDescription());
		sectionMapper.updateByPrimaryKey(section);
        return true;
    }

    @Override
    public List<SelectVo> getSectionList() {
        List<SelectVo> rlist = new ArrayList<>();
        List<DjSection> list = sectionMapper.selectAll();
        if (list != null) {
            for (DjSection section : list) {
                rlist.add(new SelectVo(section.getId() + "", section.getName()));
            }
        }
        return rlist;
    }

	@Override
	public DjSection getSectionInfoById(Long id) {
		if(id == null) {
			return null;
		}
		return sectionMapper.selectByPrimaryKey(id);
	}
}
