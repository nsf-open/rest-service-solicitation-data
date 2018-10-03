package gov.nsf.psm.solicitation.storedprocedure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import org.springframework.jdbc.core.RowMapper;

import gov.nsf.psm.foundation.model.lookup.Deadline;
import gov.nsf.psm.solicitation.common.Constants;
import gov.nsf.psm.solicitation.common.utility.SolicitationDataUtils;

public class DueDateFundingOpMapper implements RowMapper<Deadline> {

	@Override
	public final Deadline mapRow(ResultSet rs, int rowNum) throws SQLException {

		Deadline deadline = new Deadline();
		deadline.setDeadlineTypeCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.DEADLINE_TYPE_CODE)));
		deadline.setDeadlineTypeDesc(SolicitationDataUtils.getStringValue(rs.getString(Constants.DEADLINE_TYPE_DESC)));
		deadline.setFundingOppId(SolicitationDataUtils.getStringValue(rs.getString(Constants.FUNDING_OP_ID)));
		String deadlineDate = rs.getString(Constants.DEADLINE_DATE);
		try {
			deadline.setDeadlineDate(SolicitationDataUtils.convertStringtoDate(deadlineDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deadline;
	}

	
}
