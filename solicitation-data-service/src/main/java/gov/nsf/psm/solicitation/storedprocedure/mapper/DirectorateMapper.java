package gov.nsf.psm.solicitation.storedprocedure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gov.nsf.psm.foundation.model.Directorate;
import gov.nsf.psm.solicitation.common.Constants;
import gov.nsf.psm.solicitation.common.utility.SolicitationDataUtils;

public class DirectorateMapper implements RowMapper<Directorate> {

    @Override
    public final Directorate mapRow(ResultSet rs, int rowNum) throws SQLException {

        Directorate directorate = new Directorate();
        directorate.setDirectorateCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIR_ID))); // directorateCode
        directorate.setDirectorateName(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIR_NAME))); // directorateName
        directorate.setDirectorateAbbrv(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIR_ABBRV))); // directorateAbbrv
        directorate.setDirectorateLongName(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIR_LONG_NAME))); // directorateLongName
        return directorate;
    }

}
