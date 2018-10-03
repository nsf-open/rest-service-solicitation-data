package gov.nsf.psm.solicitation.storedprocedure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.jdbc.core.RowMapper;

import gov.nsf.psm.solicitation.common.Constants;
import gov.nsf.psm.solicitation.common.utility.SolicitationDataUtils;
import gov.nsf.psm.solicitation.dto.FundingOpportunityDto;

public class FundingOpportunityMapper implements RowMapper<FundingOpportunityDto> {

	@Override
	public final FundingOpportunityDto mapRow(ResultSet rs, int rowNum) throws SQLException {

		FundingOpportunityDto dto = new FundingOpportunityDto();
		dto.setFundingOpportunityId(SolicitationDataUtils.getStringValue(rs.getString(Constants.FUND_OP_ID)));
		dto.setFundingOpportunityTitle(SolicitationDataUtils.getStringValue(rs.getString(Constants.FUND_OP_TITLE)));
		dto.setFundingOpportunityType(SolicitationDataUtils.getStringValue(rs.getString(Constants.FUND_OP_TYPE)));
		dto.setFundingOpportunityYear(SolicitationDataUtils.convertStringToYear(rs.getString(Constants.FUND_OP_YEAR)));
		dto.setFundingOpportunityNumber(
		        SolicitationDataUtils.extractIntegerFromString(rs.getString(Constants.FUND_OP_NUM)));
		String effectiveDate = rs.getString(Constants.FUND_OP_EFFECTIVE_DATE);
		String expireDate = rs.getString(Constants.FUND_OP_EXPIRE_DATE);
		
		try {
			dto.setFundingOpportnityEffectiveDate(SolicitationDataUtils.convertStringtoDate(effectiveDate));
			if(expireDate!= null){
			dto.setFundingOpportnityExpirationDate(SolicitationDataUtils.convertStringtoDate(expireDate));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dto;
	}

}
