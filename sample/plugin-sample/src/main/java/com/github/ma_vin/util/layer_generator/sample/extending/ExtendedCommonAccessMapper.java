package com.github.ma_vin.util.layer_generator.sample.extending;

import com.github.ma_vin.util.layer_generator.annotations.mapper.ExtendingAccessMapper;
import com.github.ma_vin.util.layer_generator.sample.content.dao.RootExtDao;
import com.github.ma_vin.util.layer_generator.sample.content.domain.RootExt;
import com.github.ma_vin.util.layer_generator.sample.content.mapper.CommonAccessMapper;

@ExtendingAccessMapper
public class ExtendedCommonAccessMapper extends CommonAccessMapper {

    public static final String ADDITIONAL_POSTFIX = "Postfix";

    @Override
    protected void setRootExtValues(RootExtDao dao, RootExt domain) {
        super.setRootExtValues(dao, domain);
        domain.setSomeName(domain.getSomeName() + ADDITIONAL_POSTFIX);
    }

    @Override
    protected void setRootExtDaoValues(RootExt domain, RootExtDao dao) {
        super.setRootExtDaoValues(domain, dao);
        dao.setSomeName(dao.getSomeName() + ADDITIONAL_POSTFIX);
    }
}
