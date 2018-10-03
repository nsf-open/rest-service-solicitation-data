package gov.nsf.psm.solicitation.service;

import java.util.List;
import java.util.Map;

import gov.nsf.psm.foundation.exception.CommonUtilException;
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
import gov.nsf.psm.foundation.model.proposal.UOCInformation;


public interface SolicitationDataService {

	public List<FundingOpportunity> getAllFundingOpportunities(String fundingOpId) throws CommonUtilException;

	public List<Division> getAllDivisions(String fundingOpId) throws CommonUtilException;

	public List<Directorate> getAllDirectorates(String fundingOpId) throws CommonUtilException;

	public List<ProgramElement> getAllProgramElements(String fundingOpId) throws CommonUtilException;

	public List<Directorate> getDirectoratesByFundingOpId(String fundingOpId) throws CommonUtilException;

	public List<Division> getDivisionsByFundingOpIdAndDirectorateId(String fundingOpId, String directorateId)
	        throws CommonUtilException;

	public List<ProgramElement> getProgramElementsByDivisionId(String fundingOpId, String divisionId)
	        throws CommonUtilException;

	public Directorate getDirectorateByFundingOpId(String fundingOpId, String directorateId) throws CommonUtilException;

	public List<State> getStates() throws CommonUtilException;
	
	public List<Country> getCountries() throws CommonUtilException;
	
	public List<Deadline> getDueDates(String fundingOpId) throws CommonUtilException;
	
	public boolean isPostalCodeValid(String stateCode, String postalCode) throws CommonUtilException;
	
	public Pi getPIDetails( String nsfId) throws CommonUtilException;
	
	public UOCInformation getUOCDetails(UOCInformation uocInformation) throws CommonUtilException;
	
	public Map<String, Deadlines> getDueDatesForFundingOps(FundingOpportunityParams params) throws CommonUtilException;
	
}
