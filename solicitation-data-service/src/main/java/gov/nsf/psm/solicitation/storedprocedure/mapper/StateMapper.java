package gov.nsf.psm.solicitation.storedprocedure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gov.nsf.psm.foundation.model.lookup.State;
import gov.nsf.psm.solicitation.common.Constants;
import gov.nsf.psm.solicitation.common.utility.SolicitationDataUtils;

public class StateMapper implements RowMapper<State> {

	@Override
	public final State mapRow(ResultSet rs, int rowNum) throws SQLException {

		State state = new State();
		state.setStateCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.STATE_CODE))); // state
		                                                                                         // Code
		state.setStateName(SolicitationDataUtils.getStringValue(rs.getString(Constants.STATE_DESC))); // state
		                                                                                                // Desc
		return state;
	}

}