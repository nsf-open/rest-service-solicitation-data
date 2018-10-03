package gov.nsf.psm.solicitation.storedprocedure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import gov.nsf.psm.foundation.model.InstitutionAddress;
import gov.nsf.psm.foundation.model.Pi;
import gov.nsf.psm.solicitation.common.Constants;
import gov.nsf.psm.solicitation.common.utility.SolicitationDataUtils;

public class PiMapper implements RowMapper<Pi> {

	@Override
	public final Pi mapRow(ResultSet rs, int rowNum) throws SQLException {

		Pi pi = new Pi();
		pi.setNsfId(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_NSF_ID)));
		pi.setFirstName(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_FIRST_NAME)));
		pi.setLastName(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_LAST_NAME)));
		pi.setMiddleInitial(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_MIDDLE_INITIAL)));
		pi.setDegree(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_ACADEMIC_DEGREE)));
		pi.setYearofDegree(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_DEGREE_YEAR)));
		pi.setPhoneNumber(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_PHONE_NUMBER)));
		pi.setEmail(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_EMAIL)));
		pi.setDepartmentName(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_DEPARTMENT_NAME)));
		pi.setFaxNumber(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_FAX_NUMBER)));
		InstitutionAddress address = new InstitutionAddress();
		address.setStreetAddress(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_STREET_ADDR)));
		address.setStreetAddress2(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_STREET_ADDR_1)));
		address.setCityName(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_CITY_NAME)));
		address.setStateCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_STATE_CODE)));
		address.setPostalCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_ZIP_CODE)));
		address.setCountryCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.PI_COUNTRY_CODE)));
		pi.setAddress(address);
		return pi;
	}

}
