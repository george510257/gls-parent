package com.gls.quality.inspection.process.web.service.impl;

import com.gls.quality.inspection.process.web.converter.UserPhoneAddressConverter;
import com.gls.quality.inspection.process.web.entity.UserPhoneAddressEntity;
import com.gls.quality.inspection.process.web.model.UserPhoneAddressModel;
import com.gls.quality.inspection.process.web.model.query.QueryUserPhoneAddressModel;
import com.gls.quality.inspection.process.web.repository.UserPhoneAddressRepository;
import com.gls.quality.inspection.process.web.service.UserPhoneAddressService;
import com.gls.starter.data.jpa.base.BaseServiceImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author george
 */
@Service
public class UserPhoneAddressServiceImpl
        extends BaseServiceImpl<UserPhoneAddressRepository, UserPhoneAddressConverter, UserPhoneAddressEntity, UserPhoneAddressModel, QueryUserPhoneAddressModel>
        implements UserPhoneAddressService {
    public UserPhoneAddressServiceImpl(UserPhoneAddressRepository repository, UserPhoneAddressConverter converter) {
        super(repository, converter);
    }

    @Override
    protected Specification<UserPhoneAddressEntity> getSpec(QueryUserPhoneAddressModel queryUserPhoneAddressModel) {
        // todo
        return null;
    }
}
