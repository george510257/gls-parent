package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.LoanEntity;
import com.gls.quality.inspection.process.web.model.LoanModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class LoanConverter implements BaseConverter<LoanEntity, LoanModel> {
    @Resource
    private CheckConverter checkConverter;

    @Override
    public LoanModel copySourceToTarget(LoanEntity loanEntity, LoanModel loanModel) {
        loanModel.setCheck(checkConverter.sourceToTarget(loanEntity.getCheck()));
        loanModel.setTelNumber(loanEntity.getTelNumber());
        loanModel.setCheckTime(loanEntity.getCheckTime());
        loanModel.setCheckNo(loanEntity.getCheckNo());
        loanModel.setEndTime(loanEntity.getEndTime());
        loanModel.setFaceMess(loanEntity.getFaceMess());
        loanModel.setOperateUser(loanEntity.getOperateUser());
        loanModel.setOperateName(loanEntity.getOperateName());
        loanModel.setBrIdPar(loanEntity.getBrIdPar());
        loanModel.setBrId(loanEntity.getBrId());
        loanModel.setGroupId(loanEntity.getGroupId());
        loanModel.setGroupName(loanEntity.getGroupName());
        loanModel.setDhSpResult(loanEntity.getDhSpResult());
        loanModel.setFileUrl(loanEntity.getFileUrl());
        loanModel.setStatus(loanEntity.getStatus());
        loanModel.setBelType(loanEntity.getBelType());
        loanModel.setPrivateEmployer(loanEntity.getPrivateEmployer());
        loanModel.setPurpose(loanEntity.getPurpose());
        loanModel.setPayType(loanEntity.getPayType());
        loanModel.setScoreGrade(loanEntity.getScoreGrade());
        loanModel.setCarQualification(loanEntity.getCarQualification());
        loanModel.setHouseQualification(loanEntity.getHouseQualification());
        loanModel.setPolicyQualification(loanEntity.getPolicyQualification());
        loanModel.setId(loanEntity.getId());
        return loanModel;
    }

    @Override
    public LoanEntity copyTargetToSource(LoanModel loanModel, LoanEntity loanEntity) {
        loanEntity.setCheck(checkConverter.targetToSource(loanModel.getCheck()));
        loanEntity.setTelNumber(loanModel.getTelNumber());
        loanEntity.setCheckTime(loanModel.getCheckTime());
        loanEntity.setCheckNo(loanModel.getCheckNo());
        loanEntity.setEndTime(loanModel.getEndTime());
        loanEntity.setFaceMess(loanModel.getFaceMess());
        loanEntity.setOperateUser(loanModel.getOperateUser());
        loanEntity.setOperateName(loanModel.getOperateName());
        loanEntity.setBrIdPar(loanModel.getBrIdPar());
        loanEntity.setBrId(loanModel.getBrId());
        loanEntity.setGroupId(loanModel.getGroupId());
        loanEntity.setGroupName(loanModel.getGroupName());
        loanEntity.setDhSpResult(loanModel.getDhSpResult());
        loanEntity.setFileUrl(loanModel.getFileUrl());
        loanEntity.setStatus(loanModel.getStatus());
        loanEntity.setBelType(loanModel.getBelType());
        loanEntity.setPrivateEmployer(loanModel.getPrivateEmployer());
        loanEntity.setPurpose(loanModel.getPurpose());
        loanEntity.setPayType(loanModel.getPayType());
        loanEntity.setScoreGrade(loanModel.getScoreGrade());
        loanEntity.setCarQualification(loanModel.getCarQualification());
        loanEntity.setHouseQualification(loanModel.getHouseQualification());
        loanEntity.setPolicyQualification(loanModel.getPolicyQualification());
        loanEntity.setId(loanModel.getId());
        return loanEntity;
    }
}