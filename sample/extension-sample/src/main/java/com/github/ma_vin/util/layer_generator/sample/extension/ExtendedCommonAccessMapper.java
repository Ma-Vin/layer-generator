package com.github.ma_vin.util.layer_generator.sample.extension;

import com.github.ma_vin.util.layer_generator.annotations.mapper.ExtendingAccessMapper;
import com.github.ma_vin.util.layer_generator.sample.extension.content.dao.ToExtendEntityDao;
import com.github.ma_vin.util.layer_generator.sample.extension.content.domain.ToExtendEntity;
import com.github.ma_vin.util.layer_generator.sample.extension.content.mapper.CommonAccessMapper;

@ExtendingAccessMapper
public class ExtendedCommonAccessMapper extends CommonAccessMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setToExtendEntityDaoValues(ToExtendEntity domain, ToExtendEntityDao dao) {
        super.setToExtendEntityDaoValues(domain, dao);
        if (domain instanceof ExtendedEntity domainExtended && dao instanceof ExtendedEntityDao daoExtended) {
            daoExtended.setAddedToDaoBigDecimal(domainExtended.getAddedToDomainBigDecimal());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setToExtendEntityValues(ToExtendEntityDao dao, ToExtendEntity domain) {
        super.setToExtendEntityValues(dao, domain);
        if (dao instanceof ExtendedEntityDao daoExtended && domain instanceof ExtendedEntity domainExtended) {
            domainExtended.setAddedToDomainBigDecimal(daoExtended.getAddedToDaoBigDecimal());
        }
    }
}
