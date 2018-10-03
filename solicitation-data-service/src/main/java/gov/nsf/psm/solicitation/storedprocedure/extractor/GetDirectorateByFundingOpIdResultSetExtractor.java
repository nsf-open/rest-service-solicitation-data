package gov.nsf.psm.solicitation.storedprocedure.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import gov.nsf.psm.foundation.model.Directorate;
import gov.nsf.psm.foundation.model.Division;
import gov.nsf.psm.foundation.model.ProgramElement;
import gov.nsf.psm.solicitation.common.Constants;
import gov.nsf.psm.solicitation.common.utility.SolicitationDataUtils;

public class GetDirectorateByFundingOpIdResultSetExtractor implements ResultSetExtractor<Directorate> {

    @Override
    public Directorate extractData(ResultSet rs) throws SQLException {
        Directorate directorate = new Directorate();
        List<Division> divisions = new ArrayList<Division>();
        Division division = null;
        List<ProgramElement> programElements = null;
        String divCode = null;

        while (rs.next()) {
            // execute this for the first record only
            if (rs.isFirst()) {

                // populate directorate only once
                directorate.setDirectorateCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIR_ID)));
                directorate.setDirectorateName(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIR_NAME)));
                directorate
                        .setDirectorateAbbrv(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIR_ABBRV)));
                directorate.setDirectorateLongName(
                        SolicitationDataUtils.getStringValue(rs.getString(Constants.DIR_LONG_NAME)));
            }

            // if record has a different division code...
            if (divCode == null
                    || !divCode.equals(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIV_ID)))) {
                // initialize the first division
                division = new Division();
                division.setDivisionCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIV_ID)));
                division.setDivisionName(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIV_NAME)));
                division.setDivisionAbbrv(SolicitationDataUtils.getStringValue(rs.getString(Constants.DIV_ABBRV)));
                division.setDivisionLongName(
                        SolicitationDataUtils.getStringValue(rs.getString(Constants.DIV_LONG_NAME)));
                divisions.add(division);

                // initialize the division code tracker
                divCode = SolicitationDataUtils.getStringValue(rs.getString(Constants.DIV_ID));

                // initialize the programElements array
                programElements = new ArrayList<ProgramElement>();
            }

            // create a ProgramElement object and populate it with record data.
            // Then add it to the programElement list
            ProgramElement progElement = new ProgramElement();
            progElement.setProgramElementCode(SolicitationDataUtils.getStringValue(rs.getString(Constants.PGM_ELE_ID)));
            progElement
                    .setProgramElementName(SolicitationDataUtils.getStringValue(rs.getString(Constants.PGM_ELE_NAME)));
            progElement.setProgramElementLongName(
                    SolicitationDataUtils.getStringValue(rs.getString(Constants.PGM_ELE_LONG_NAME)));
            programElements.add(progElement);
            division.setProgramElements(programElements.toArray(new ProgramElement[programElements.size()]));

        }

        directorate.setDivisions(divisions.toArray(new Division[divisions.size()]));
        return directorate;
    }

}
