package cn.dlbdata.dj.web.aop;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.JwtTokenUtil;
import cn.dlbdata.dj.common.core.util.cache.CacheManager;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.OperationsEnum;
import cn.dlbdata.dj.db.mapper.DjLogOptMapper;
import cn.dlbdata.dj.db.pojo.DjLogOpt;
import cn.dlbdata.dj.dto.ActiveSignUpRequest;
import cn.dlbdata.dj.dto.study.StudyResubmitDto;
import cn.dlbdata.dj.dto.vangard.VanguardParamVo;
import cn.dlbdata.dj.vo.*;
import cn.dlbdata.dj.web.vo.SignVo;
import com.google.gson.Gson;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Component
@Aspect
public class LogInterceptor {
    private static String ip= null;
    @Autowired
    private DjLogOptMapper logOptMapper;
    private void addLog(String json, Object object, OperationsEnum enums) {
        UserVo user = this.getCurUser();
        if (object instanceof ResultVo) {
            ResultVo result = (ResultVo) object;
            DjLogOpt opt = new DjLogOpt();
            opt.setId(DigitUtil.generatorLongId());
            if(user != null) {
                opt.setDjUserId(user.getUserId());
                opt.setDjDeptId(user.getDeptId());
            }
            opt.setIp(ip);
            opt.setOptId(enums.getType());
            opt.setOptDesc(json);
            opt.setStatus(result.getCode());
            opt.setErrorMsg(result.getMsg());
            opt.setCreateTime(new Date());
            logOptMapper.insert(opt);
        }


    }
    private UserVo getCurUser() {
        HttpServletRequest request = SysContent.getRequest();
        ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "本地";
        }
        String token = request.getHeader("token");
        Map<String, String> tokenMap = JwtTokenUtil.getTokenInfo(token);
        if (tokenMap == null) {

        }
        String userId = tokenMap.get(JwtTokenUtil.KEY_UID);
        UserVo currUser = (UserVo) CacheManager.getInstance().get(userId);
        return currUser;
    }

    /**
     *金领驿站活动报名
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.ActiveController.participate(cn.dlbdata.dj.dto.ActiveSignUpRequest))" +
            " && args(activeSignUpRequest)")
    private void participate(ActiveSignUpRequest activeSignUpRequest) {
    }

    @AfterReturning(pointcut ="participate(req)",returning = "rvt")
    public  void participateInterceptor(ActiveSignUpRequest req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.ACTIVE_SIGNUP);
    }

    /**
     *发起活动
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.ActiveController.createActive(cn.dlbdata.dj.vo.ActiveVo))" +
            " && args(req)")
    private void createActive(ActiveVo req) {
    }

    @AfterReturning(pointcut ="createActive(req)",returning = "rvt")
    public  void createActiveInterceptor(ActiveVo req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.CREATE_ACTIVE);
    }

    /**
     *活动签到
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.ActiveController.signIn(cn.dlbdata.dj.web.vo.SignVo))" +
            " && args(req)")
    private void signIn(SignVo req) {
    }

    @AfterReturning(pointcut ="signIn(req)",returning = "rvt")
    public  void signInInterceptor(SignVo req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.ACTIVE_SIGNIN);
    }

    /**
     *取消活动
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.ActiveController.cancelActiveById(cn.dlbdata.dj.web.vo.SignVo))" +
            " && args(req)")
    private void cancelActive(SignVo req) {
    }

    @AfterReturning(pointcut ="cancelActive(req)",returning = "rvt")
    public  void cancelActiveInterceptor(SignVo req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.ACTIVE_CANCEL);
    }

    /**
     *上传图片
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.PicController.upload(cn.dlbdata.dj.vo.PicVo))" +
            " && args(req)")
    private void uploadPic(PicVo req) {
    }

    @AfterReturning(pointcut ="uploadPic(req)",returning = "rvt")
    public  void uploadPicInterceptor(PicVo req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.PIC_UPLOAD);
    }

    /**
     *删除图片
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.PicController.deleteActivePicById(cn.dlbdata.dj.vo.DjActivePicVo))" +
            " && args(req)")
    private void deletePic(DjActivePicVo req) {
    }

    @AfterReturning(pointcut ="deletePic(req)",returning = "rvt")
    public  void deletePicInterceptor(DjActivePicVo req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.PIC_DELETE);
    }

    /**
     *发起自主学习申请
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.WorkflowController.applyStudy(cn.dlbdata.dj.vo.StudyVo))" +
            " && args(req)")
    private void studyApply(StudyVo req) {
    }

    @AfterReturning(pointcut ="studyApply(req)",returning = "rvt")
    public  void studyApplyInterceptor(StudyVo req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.APPLY_STUDY);
    }

    /**
     *驿站生活违纪违规扣分申请
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.WorkflowController.applyDiscipline(cn.dlbdata.dj.vo.DisciplineVo))" +
            " && args(req)")
    private void applyDiscipline(DisciplineVo req) {
    }

    @AfterReturning(pointcut ="applyDiscipline(req)",returning = "rvt")
    public  void applyDisciplineInterceptor(DisciplineVo req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.ONE_VOTE_VETO);
    }

    /**
     *先锋作用申请
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.WorkflowController.applyVanguard(cn.dlbdata.dj.dto.vangard.VanguardParamVo))" +
            " && args(req)")
    private void applyVanguard(VanguardParamVo req) {
    }

    @AfterReturning(pointcut ="applyVanguard(req)",returning = "rvt")
    public  void applyVanguardInterceptor(VanguardParamVo req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.VANGUARD_APPLY);
    }

    /**
     *思想汇报申请
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.WorkflowController.applyThoughts(cn.dlbdata.dj.vo.ThoughtsVo))" +
            " && args(req)")
    private void applyThoughts(ThoughtsVo req) {
    }

    @AfterReturning(pointcut ="applyThoughts(req)",returning = "rvt")
    public  void applyThoughtsInterceptor(ThoughtsVo req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.THOUGHTS_GRADE);
    }

    /**
     *审核
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.WorkflowController.audit(cn.dlbdata.dj.vo.AuditVo))" +
            " && args(req)")
    private void audit(AuditVo req) {
    }

    @AfterReturning(pointcut ="audit(req)",returning = "rvt")
    public  void auditInterceptor(AuditVo req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.AUDIT);
    }

    /**
     *自主活动重新提交
     */
    @Pointcut(value = "execution(* cn.dlbdata.dj.web.controller.api.v1.WorkflowController.studyResubmit(cn.dlbdata.dj.dto.study.StudyResubmitDto))" +
            " && args(req)")
    private void studyResubmit(StudyResubmitDto req) {
    }

    @AfterReturning(pointcut ="studyResubmit(req)",returning = "rvt")
    public  void studyResubmitInterceptor(StudyResubmitDto req, Object rvt) {
        Gson gson = new Gson();
        addLog(gson.toJson(req),rvt,OperationsEnum.STUDY_RESUBMIT);
    }

}
