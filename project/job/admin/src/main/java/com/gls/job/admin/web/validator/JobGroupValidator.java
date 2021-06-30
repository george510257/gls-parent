package com.gls.job.admin.web.validator;

import com.gls.job.admin.web.model.JobGroup;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class JobGroupValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return JobGroup.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JobGroup jobGroup = (JobGroup) target;
        if (!jobGroup.getAddressType()) {
            if (!StringUtils.hasText(jobGroup.getAddressList())) {
                errors.rejectValue("addressList", "jobGroup.addressList.required", "手动录入注册方式，机器地址不可为空。");
            }
            if (jobGroup.getAddressList().contains(">") || jobGroup.getAddressList().contains("<")) {
                errors.rejectValue("addressList", "jobGroup.addressList.ipaddress", "机器地址格式非法");
            }
            String[] addresses = jobGroup.getAddressList().split(",");
            for (String address : addresses) {
                if (!StringUtils.hasText(address)) {
                    errors.rejectValue("addressList", "jobGroup.addressList.ipaddress", "机器地址格式非法");
                }
            }
        }
    }
}
