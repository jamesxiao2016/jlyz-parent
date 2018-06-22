package cn.dlbdata.dj.serviceimpl;

import cn.dlbdata.dj.common.core.util.DatetimeUtil;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.poi.ExcelReplaceDataVO;
import cn.dlbdata.dj.constant.ActiveSubTypeEnum;
import cn.dlbdata.dj.constant.ActiveTypeEnum;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjPartymemberDuesMapper;
import cn.dlbdata.dj.db.mapper.DjPartymemberMapper;
import cn.dlbdata.dj.db.mapper.DjScoreMapper;
import cn.dlbdata.dj.db.pojo.DjPartymember;
import cn.dlbdata.dj.db.pojo.DjPartymemberDues;
import cn.dlbdata.dj.db.pojo.DjScore;
import cn.dlbdata.dj.service.PartyMemberDueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class PartyMemberDueServiceImpl implements PartyMemberDueService {
    @Autowired
    private DjPartymemberDuesMapper duesMapper;
    @Autowired
    private DjPartymemberMapper partymemberMapper;
    @Autowired
    private DjScoreMapper scoreMapper;

    @Override
    public Map<String, List<ExcelReplaceDataVO>> saveDues(List<List<Object>> data) {
        Map<String,  List<ExcelReplaceDataVO>> map = new HashMap<>();
        List<ExcelReplaceDataVO> errorInfo = new ArrayList<>();
        List<String> orderCodeList = new ArrayList<>();
        Set<String> orderCodeSet = new HashSet<>();
        for (int i = 1;i<data.size();i++) {
            orderCodeList.add(data.get(i).get(2).toString());
            orderCodeSet.add(data.get(i).get(2).toString());
        }
        List<String> repeatList = new ArrayList<>();
        if (orderCodeList.size() != orderCodeSet.size()) {//传入的Excel中存在重复的订单号
           orderCodeList.removeAll(orderCodeSet);//取差集
            repeatList = orderCodeList;
        }
        List<DjPartymemberDues> duesList = new ArrayList<>();
        List<DjScore> scoreList = new ArrayList<>();
        for (int i = 1;i<data.size();i++) {//第一行是表格头部，不需要取，直接取第二行
            DjPartymemberDues due = new DjPartymemberDues();
            due.setId(DigitUtil.generatorLongId());
            due.setCreateTime(new Date());

            DjScore score = new DjScore();
            score.setId(DigitUtil.generatorLongId());
            score.setDjSubTypeId(ActiveTypeEnum.ACTIVE_G.getActiveId());
            score.setDjTypeId(ActiveSubTypeEnum.ACTIVE_SUB_I.getActiveSubId());
            score.setScore(1.25F);
            score.setAddTime(new Date());
            score.setAddYear(2018);//TODO 需要修改
            score.setStatus(1);
            score.setRecrodDesc("党员按月足额缴纳党费");

            List<Object> list = data.get(i);
            //订单编号
            String orderCode = list.get(2).toString();//TODO 校验重复
            if (orderCode == null || "".equals(orderCode)) {
                ExcelReplaceDataVO repVo= new ExcelReplaceDataVO();
                repVo.setRow(i);
                repVo.setColumn(1);
                repVo.setValue("请填写订单号!");
                errorInfo.add(repVo);
            } else {
                if (repeatList.contains(orderCode)) {
                    ExcelReplaceDataVO repVo= new ExcelReplaceDataVO();
                    repVo.setRow(i);
                    repVo.setColumn(1);
                    repVo.setValue("清单中订单号重复!");
                    errorInfo.add(repVo);
                } else {
                    boolean exists = duesMapper.existWithOrderCode(orderCode);
                    if (exists) {
                        ExcelReplaceDataVO repVo= new ExcelReplaceDataVO();
                        repVo.setRow(i);
                        repVo.setColumn(1);
                        repVo.setValue("订单号已存在系统中!");
                        errorInfo.add(repVo);
                    }
                }
            }
            //缴费时间
            String payTimeStr = list.get(0).toString();
            Date payTime = DatetimeUtil.getLongDateByStr(payTimeStr);
            if (payTime == null) {
                ExcelReplaceDataVO repVo= new ExcelReplaceDataVO();
                repVo.setRow(i);
                repVo.setColumn(0);
                repVo.setValue("请填写正确的缴费时间!");
                errorInfo.add(repVo);
            } else {
                due.setPaymentTime(payTime);
            }

            //缴费金额
            String moneyStr = list.get(4).toString();
            Float money = DigitUtil.parseToFloat(moneyStr,null);
            if (money == null) {
                ExcelReplaceDataVO repVo= new ExcelReplaceDataVO();
                repVo.setRow(i);
                repVo.setColumn(4);
                repVo.setValue("请填写正确的缴费金额!");
                errorInfo.add(repVo);
            } else {
                due.setRealMoney(money);
                due.setDuesMoney(money);
            }
            DjPartymember partymember = null;
            //身份证号
            String idCardStr = list.get(9).toString();
            if (idCardStr == null || "".equals(idCardStr)) {
                ExcelReplaceDataVO repVo= new ExcelReplaceDataVO();
                repVo.setRow(i);
                repVo.setColumn(9);
                repVo.setValue("请填写身份证号!");
                errorInfo.add(repVo);
            } else {
                partymember = partymemberMapper.getByIdCard(idCardStr);
                if (partymember == null) {
                    ExcelReplaceDataVO repVo= new ExcelReplaceDataVO();
                    repVo.setRow(i);
                    repVo.setColumn(9);
                    repVo.setValue("系统中无此身份证号对应的党员!");
                    errorInfo.add(repVo);
                } else {
                    due.setDjPartymemberId(partymember.getId());
                    score.setUserId(partymember.getId());
                    score.setUserName(partymember.getName());
                }
            }
            String name = list.get(10).toString();
            if (name == null) {
                ExcelReplaceDataVO repVo= new ExcelReplaceDataVO();
                repVo.setRow(i);
                repVo.setColumn(10);
                repVo.setValue("请填写姓名!");
                errorInfo.add(repVo);
            } else {
                if (partymember != null) {
                    if (!partymember.getName().equals(name)) {
                        ExcelReplaceDataVO repVo= new ExcelReplaceDataVO();
                        repVo.setRow(i);
                        repVo.setColumn(10);
                        repVo.setValue("党员身份证错误!");
                        errorInfo.add(repVo);
                    }
                }
            }

            //TODO 需要写正则判断传入的日期
            String monthStr = list.get(14).toString();

            if (monthStr == null) {
                ExcelReplaceDataVO repVo= new ExcelReplaceDataVO();
                repVo.setRow(i);
                repVo.setColumn(14);
                repVo.setValue("请填写缴纳月份!");
                errorInfo.add(repVo);
            }

            String payStatusStr = list.get(15).toString();
            if (payStatusStr == null || "".equals(payStatusStr)) {
                ExcelReplaceDataVO repVo= new ExcelReplaceDataVO();
                repVo.setRow(i);
                repVo.setColumn(15);
                repVo.setValue("请填写缴费状态!");
                errorInfo.add(repVo);
            }
            due.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
            if (!payStatusStr.trim().equals("已缴费")) { //没缴费的不保存
                continue;
            } else {
                duesList.add(due);
                scoreList.add(score);
            }
        }
        if (!errorInfo.isEmpty()) {//文件中有错误就不做保存操作
            map.put("error",errorInfo);
            return map;
        } else {
            duesMapper.batchInset(duesList);
            scoreMapper.batchInsert(scoreList);
        }
        return null;
    }
}
