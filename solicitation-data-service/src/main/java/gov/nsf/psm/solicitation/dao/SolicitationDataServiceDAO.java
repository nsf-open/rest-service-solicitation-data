package gov.nsf.psm.solicitation.dao;

import java.util.List;
import java.util.Map;

import gov.nsf.psm.foundation.model.Directorate;
import gov.nsf.psm.foundation.model.Division;
import gov.nsf.psm.foundation.model.FundingOpportunities;
import gov.nsf.psm.foundation.model.FundingOpportunity;
import gov.nsf.psm.foundation.model.FundingOpportunityParams;
import gov.nsf.psm.foundation.model.Pi;
import gov.nsf.psm.foundation.model.ProgramElement;
import gov.nsf.psm.foundation.model.lookup.Country;
import gov.nsf.psm.foundation.model.lookup.Deadline;
import gov.nsf.psm.foundation.model.lookup.Deadlines;
import gov.nsf.psm.foundation.model.lookup.State;


public interface SolicitationDataServiceDAO {

    // Funding Opportunities
    public List<FundingOpportunity> getAllFundingOpportunities(String fundingOpId);

    // Divisions
    public List<Division> getAllDivisionsGPG(String whereClause);

    public List<Division> getAllDivisions(String fundingOpId);

    // Program Elements
    public List<ProgramElement> getAllProgramElementsGPG(String exclusionList);

    public List<ProgramElement> getAllProgramElements(String fundingOpId);

    // Directorates
    public List<Directorate> getAllDirectoratesGPG(String whereClause);

    public List<Directorate> getAllDirectorates(String fundingOpId);

    // Directorates by Funding Op
    public List<Directorate> getDirectoratesByFundingOpId(String fundingOpId);

    // Divisions by Funding Op and Directorate Id
    public List<Division> getDivisionsByFundingOpIdAndDirectorateId(String fundingOpId, String directorateId);

    public List<Division> getDivisionsByFundingOpIdAndDirectorateIdGPG(String directorateId, String whereClause);

    // Program Elements by Division id
    public List<ProgramElement> getProgramElementsByDivisionId(String fundingOpId, String divisionId);

    public List<ProgramElement> getProgramElementsByDivisionIdGPG(String divisionId, String exclusionList);

    // Directorates by Funding Op
    public Directorate getDirectorateByFundingOpId(String fundingOpId, String directorateId);

    public Directorate getDirectorateByFundingOpIdGPG(String directorateId);
    
    public void setGpgFundingOpportunityCode(String gpgFundingOpportunityCode);
    
    public void setDivisionCodeMatchWhereClauseExt(String divisionCodeMatchWhereClauseExt);
     
    public List<State> getStates();
    
    public List<Country> getCountries();
    
    public List<Deadline> getDueDates(String fundingOpId);
    
    public String getPostalCode(String stateCode, String postalCode);
    
    public Pi getPIDetails(String nsfId);
    
    public String getUserSSN(String nsfId);
    
	public ProgramElement getProgramElementDetails(String code);

	public Directorate getDirectorateDetails(String code);

	public Division getDivisionDetails(String code);
	
	public FundingOpportunity getFundingOpportunityDetails(String code);
	
	public Map<String, Deadlines> getDueDatesByFundingOpIds(FundingOpportunityParams params);
    
}
