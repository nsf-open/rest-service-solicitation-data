package gov.nsf.psm.solicitation.storedprocedure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gov.nsf.psm.foundation.model.lookup.Country;
import gov.nsf.psm.solicitation.common.Constants;
import gov.nsf.psm.solicitation.common.utility.SolicitationDataUtils;

public class CountryMapper implements RowMapper<Country> {

	@Override
	public final Country mapRow(ResultSet rs, int rowNum) throws SQLException {

		Country country = new Country();
		country.setCountryCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.COUNTRY_CODE)));
		country.setCountryName(SolicitationDataUtils.getStringValue(rs.getString(Constants.COUNTRY_NAME)));
		return country;
	}

}
