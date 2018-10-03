package gov.nsf.psm.solicitation.storedprocedure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gov.nsf.psm.foundation.model.Division;
import gov.nsf.psm.solicitation.common.Constants;
import gov.nsf.psm.solicitation.common.utility.SolicitationDataUtils;

public class DivisionMapper implements RowMapper<Division> {

    @Override
    public final Division mapRow(ResultSet rs, int rowNum) throws SQLException {

        Division division = new Division();
        division.setDivisionCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIV_ID))); // divisionCode
        division.setDivisionName(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIV_NAME))); // divisionName
        division.setDivisionAbbrv(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIV_ABBRV))); // divisionAbbrv
        division.setDivisionLongName(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIV_LONG_NAME))); // divisionLongName
        return division;

    }

}
