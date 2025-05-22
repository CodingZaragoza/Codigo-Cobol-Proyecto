package gov.cms.fiss.pricers.hha.resources.mapping;

import gov.cms.fiss.pricers.common.api.CbsaWageIndexData;
import gov.cms.fiss.pricers.hha.core.tables.CbsaWageIndexEntry;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CbsaWageIndexDataMapper {
  CbsaWageIndexDataMapper INSTANCE = Mappers.getMapper(CbsaWageIndexDataMapper.class);

  CbsaWageIndexData mapToCbsaWageIndexData(CbsaWageIndexEntry data);
}
