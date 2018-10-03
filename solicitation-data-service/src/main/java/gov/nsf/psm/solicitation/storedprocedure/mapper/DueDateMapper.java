package gov.nsf.psm.solicitation.storedprocedure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import org.springframework.jdbc.core.RowMapper;

import gov.nsf.psm.foundation.model.lookup.Deadline;
import gov.nsf.psm.solicitation.common.Constants;
import gov.nsf.psm.solicitation.common.utility.SolicitationDataUtils;

public class DueDateMapper implements RowMapper<Deadline> {

	@Override
	public final Deadline mapRow(ResultSet rs, int rowNum) throws SQLException {

		Deadline deadlines = new Deadline();
		deadlines.setDeadlineTypeCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.DEADLINE_TYPE_CODE)));
		deadlines.setDeadlineTypeDesc(SolicitationDataUtils.getStringValue(rs.getString(Constants.DEADLINE_TYPE_DESC)));
		String deadlineDate = rs.getString(Constants.DEADLINE_DATE);
		try {
			deadlines.setDeadlineDate(SolicitationDataUtils.convertStringtoDate(deadlineDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deadlines;
	}

	
}
