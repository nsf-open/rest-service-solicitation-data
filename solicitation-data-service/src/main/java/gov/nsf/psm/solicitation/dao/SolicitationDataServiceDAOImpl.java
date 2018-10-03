package gov.nsf.psm.solicitation.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import com.google.common.base.Joiner;

import gov.nsf.psm.foundation.model.Directorate;
import gov.nsf.psm.foundation.model.Division;
import gov.nsf.psm.foundation.model.FundingOpportunity;
import gov.nsf.psm.foundation.model.FundingOpportunityParams;
import gov.nsf.psm.foundation.model.Pi;
import gov.nsf.psm.foundation.model.ProgramElement;
import gov.nsf.psm.foundation.model.lookup.Country;
import gov.nsf.psm.foundation.model.lookup.Deadline;
import gov.nsf.psm.foundation.model.lookup.Deadlines;
import gov.nsf.psm.foundation.model.lookup.State;
import gov.nsf.psm.solicitation.common.Constants;
import gov.nsf.psm.solicitation.common.QueryString;
import gov.nsf.psm.solicitation.common.utility.SolicitationDataUtils;
import gov.nsf.psm.solicitation.dto.FundingOpportunityDto;
import gov.nsf.psm.solicitation.storedprocedure.extractor.GetDirectorateByFundingOpIdResultSetExtractor;
import gov.nsf.psm.solicitation.storedprocedure.mapper.CountryMapper;
import gov.nsf.psm.solicitation.storedprocedure.mapper.DirectorateMapper;
import gov.nsf.psm.solicitation.storedprocedure.mapper.DivisionMapper;
import gov.nsf.psm.solicitation.storedprocedure.mapper.DueDateFundingOpMapper;
import gov.nsf.psm.solicitation.storedprocedure.mapper.DueDateMapper;
import gov.nsf.psm.solicitation.storedprocedure.mapper.FundingOpportunityMapper;
import gov.nsf.psm.solicitation.storedprocedure.mapper.PiMapper;
import gov.nsf.psm.solicitation.storedprocedure.mapper.ProgramElementMapper;
import gov.nsf.psm.solicitation.storedprocedure.mapper.StateMapper;

public class SolicitationDataServiceDAOImpl implements SolicitationDataServiceDAO {

	@Autowired
	@Qualifier("psmFLJdbcTemplate")
	private JdbcTemplate psmFLJdbcTemplate;

	@Autowired
	@Qualifier("namedPsmJdbcTemplate")
	NamedParameterJdbcTemplate namedPsmJdbcTemplate;

	@Autowired
	@Qualifier("psmPARSJdbcTemplate")
	private JdbcTemplate psmPARSJdbcTemplate;

	@Value("${gpg.fundingOpportunityCode}")
	private String gpgFundingOpportunityCode;

	@Value("${gpg.param.programElementCodeExclusionList}")
	private String gpgProgramElementCodeExclusionList;

	@Value("${gpg.param.divisionCodeMatchWhereClauseExt}")
	private String divisionCodeMatchWhereClauseExt;
	
	@Value("${fundingOpportunity.exclusionList}")
	private String fundingOpportunityCodeExclusionList;
	

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private String resultLog = "%s %s returned in the results.";
	private String directorates = "directorates";
	private String divisions = "divisions";
	private String programElements = "program elements";

	@Override
	public List<FundingOpportunity> getAllFundingOpportunities(String fundingOpId) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getAllFundingOpportunities()");

		List<String> exclusionFundingOpIds = SolicitationDataUtils.convertStringToList(fundingOpportunityCodeExclusionList);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("exclusionFundingOpIds", exclusionFundingOpIds);

		List<FundingOpportunityDto> results = null;
		results = namedPsmJdbcTemplate.query(QueryString.GET_ALL_FUNDING_OPPORTUNITIES_QUERY, params,
		        new FundingOpportunityMapper());

		if (results != null && !results.isEmpty()) {
			LOGGER.info(results.size() + " funding opportunities returned in the results.");

			// sort dto list
			Collections.sort(results, new FundingOpportunityDto.FundingOpportunityDtoComparator());

			// convert Dto list ref to model object list ref
			List<FundingOpportunity> fundingOps = new ArrayList<FundingOpportunity>();
			for (FundingOpportunityDto dto : results) {
				FundingOpportunity fundingOp = new FundingOpportunity(dto.getFundingOpportunityId(),
				        dto.getFundingOpportunityTitle());
				fundingOps.add(fundingOp);
			}
			return fundingOps;

		} else {
			LOGGER.info("No funding opportunities returned in the results.");
			return new ArrayList<FundingOpportunity>();
		}
	}

	
	@Override
	public List<Directorate> getAllDirectorates(String fundingOpId) {
		if (fundingOpIdIsGPG(fundingOpId))
			return getAllDirectoratesGPG(divisionCodeMatchWhereClauseExt);
		else {
			LOGGER.info("SolicitationDataServiceDaoImpl.getAllDirectorates()");
			List<Directorate> results = null;
			results = psmFLJdbcTemplate.query(QueryString.GET_ALL_DIRECTORATES_QUERY, new DirectorateMapper());
			if (results != null && !results.isEmpty()) {
				LOGGER.info(String.format(resultLog, Integer.toString(results.size()), directorates));
				return results;
			} else {
				LOGGER.info(String.format(resultLog, "No", directorates));
				return new ArrayList<Directorate>();
			}
		}
	}

	@Override
	public List<Division> getAllDivisions(String fundingOpId) {
		if (fundingOpIdIsGPG(fundingOpId))
			return getAllDivisionsGPG(divisionCodeMatchWhereClauseExt);
		else {
			LOGGER.info("SolicitationDataServiceDaoImpl.getAllDivisions()");
			List<Division> results = null;
			results = psmFLJdbcTemplate.query(QueryString.GET_ALL_DIVSIONS_QUERY, new DivisionMapper());

			if (results != null && !results.isEmpty()) {
				LOGGER.info(String.format(resultLog, Integer.toString(results.size()), divisions));
				return results;
			} else {
				LOGGER.info(String.format(resultLog, "No", divisions));
				return new ArrayList<Division>();
			}
		}
	}

	@Override
	public List<ProgramElement> getAllProgramElements(String fundingOpId) {
		if (fundingOpIdIsGPG(fundingOpId))
			return getAllProgramElementsGPG(gpgProgramElementCodeExclusionList);
		else {
			LOGGER.info("SolicitationDataServiceDaoImpl.getAllProgramElements()");
			List<ProgramElement> results = null;
			results = psmFLJdbcTemplate.query(QueryString.GET_ALL_PROGRAM_ELEMENTS_QUERY, new ProgramElementMapper());

			if (results != null && !results.isEmpty()) {
				LOGGER.info(String.format(resultLog, Integer.toString(results.size()), programElements));
				return results;
			} else {
				LOGGER.info(String.format(resultLog, "No", programElements));
				return new ArrayList<ProgramElement>();
			}
		}
	}

	@Override
	public List<ProgramElement> getProgramElementsByDivisionId(String fundingOpId, String divisionId) {

		if (fundingOpIdIsGPG(fundingOpId))
			return getProgramElementsByDivisionIdGPG(divisionId, gpgProgramElementCodeExclusionList);
		else {
			LOGGER.info("SolicitationDataServiceDaoImpl.getProgramElementsByDivisionId()");
			List<ProgramElement> results = null;

			results = psmFLJdbcTemplate.query(QueryString.GET_PROGRAM_ELEMENTS_BY_DIVISION_ID_QUERY,
			        new Object[] { fundingOpId, divisionId }, new ProgramElementMapper());

			if (results != null && !results.isEmpty()) {
				LOGGER.info(String.format(resultLog, Integer.toString(results.size()), programElements));
				return results;
			} else {
				LOGGER.info(String.format(resultLog, "No", programElements));
				return new ArrayList<ProgramElement>();
			}
		}
	}

	@Override
	public List<Directorate> getDirectoratesByFundingOpId(String fundingOpId) {
		if (fundingOpIdIsGPG(fundingOpId))
			return getAllDirectoratesGPG(divisionCodeMatchWhereClauseExt);
		else {
			LOGGER.info("SolicitationDataServiceDaoImpl.getDirectoratesByFundingOpId()");
			List<Directorate> results = null;
			results = psmFLJdbcTemplate.query(QueryString.GET_DIRECTORATES_LIST_BY_FUND_OPP_ID_QUERY,
			        new Object[] { fundingOpId }, new DirectorateMapper());

			if (results != null && !results.isEmpty()) {
				LOGGER.info(String.format(resultLog, Integer.toString(results.size()), directorates));
				return results;
			} else {
				LOGGER.info(String.format(resultLog, "No", directorates));
				return new ArrayList<Directorate>();
			}
		}
	}

	@Override
	public List<Division> getDivisionsByFundingOpIdAndDirectorateId(String fundingOpId, String directorateId) {
		if (fundingOpIdIsGPG(fundingOpId))
			return getDivisionsByFundingOpIdAndDirectorateIdGPG(directorateId, divisionCodeMatchWhereClauseExt);
		else {
			LOGGER.info("SolicitationDataServiceDaoImpl.getDivisionsByFundingOpIdAndDirectorateId()");
			List<Division> results = null;
			results = psmFLJdbcTemplate.query(QueryString.GET_DIVISION_LIST_BY_FUND_OPP_ID_QUERY,
			        new Object[] { fundingOpId, directorateId }, new DivisionMapper());
			if (results != null && !results.isEmpty()) {
				LOGGER.info(String.format(resultLog, Integer.toString(results.size()), divisions));
				return results;
			} else {
				LOGGER.info(String.format(resultLog, "No", divisions));
				return new ArrayList<Division>();
			}
		}
	}

	@Override
	public Directorate getDirectorateByFundingOpId(String fundingOpId, String directorateId) {

		if (fundingOpIdIsGPG(fundingOpId))
			return getDirectorateByFundingOpIdGPG(directorateId);
		else {
			LOGGER.info("SolicitationDataServiceDaoImpl.getDirectorateByFundingOpId()");
			Directorate result = null;

			result = psmFLJdbcTemplate.query(QueryString.GET_DIR_DIV_PGM_BY_DIR_AND_FUND_ID_QUERY,
			        new Object[] { fundingOpId, directorateId, fundingOpId, directorateId, directorateId },
			        new GetDirectorateByFundingOpIdResultSetExtractor());

			if (result != null && !StringUtils.isEmpty(result.getDirectorateName())) {
				LOGGER.info("1 directorate was returned in the results.");
				return result;
			} else {
				LOGGER.info("No directorate was returned in the results.");
				return new Directorate();
			}
		}
	}

	@Override
	public List<Division> getDivisionsByFundingOpIdAndDirectorateIdGPG(String directorateId, String whereClause) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getDivisionsByFundingOpIdAndDirectorateIdGPG()");
		List<Division> results = null;

		results = psmFLJdbcTemplate.query(QueryString.GET_DIVISIONS_BY_DIR_ID_GPG_QUERY, new Object[] { directorateId },
		        new DivisionMapper());

		if (results != null && !results.isEmpty()) {
			LOGGER.info(String.format(resultLog, Integer.toString(results.size()), divisions));
			return results;
		} else {
			LOGGER.info(String.format(resultLog, "No", divisions));
			return new ArrayList<Division>();
		}
	}

	@Override
	public List<ProgramElement> getProgramElementsByDivisionIdGPG(String divisionId, String exclusionList) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getProgramElementsByDivisionIdGPG()");

		List<ProgramElement> results = null;

		results = psmFLJdbcTemplate.query(QueryString.GET_PROGRAM_ELEMENTS_BY_DIV_CODE_GPG_QUERY,
		        new Object[] { divisionId }, new ProgramElementMapper());

		if (results != null && !results.isEmpty()) {
			LOGGER.info(String.format(resultLog, Integer.toString(results.size()), programElements));
			return results;
		} else {
			LOGGER.info(String.format(resultLog, "No", programElements));
			return new ArrayList<ProgramElement>();
		}
	}

	@Override
	public Directorate getDirectorateByFundingOpIdGPG(String directorateId) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getDirectorateByFundingOpIdGPG()");
		Directorate result = null;

		result = psmFLJdbcTemplate.query(QueryString.GET_DIR_DIV_PGM_BY_DIR_ID_GPG_QUERY,
		        new Object[] { directorateId, directorateId, directorateId },
		        new GetDirectorateByFundingOpIdResultSetExtractor());

		if (result != null && !StringUtils.isEmpty(result.getDirectorateName())) {
			LOGGER.info("1 directorate was returned in the results.");
			return result;
		} else {
			LOGGER.info("No directorate was returned in the results.");
			return new Directorate();
		}
	}

	
	@Override
	public List<Directorate> getAllDirectoratesGPG(String whereClause) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getAllDirectoratesGPG()");

		List<Directorate> results = null;
		results = psmFLJdbcTemplate.query(QueryString.GET_ALL_DIRECTORATES_GPG_QUERY, new DirectorateMapper());
		if (results != null && !results.isEmpty()) {
			LOGGER.info(String.format(resultLog, Integer.toString(results.size()), directorates));
			return results;
		} else {
			LOGGER.info(String.format(resultLog, "No", directorates));
			return new ArrayList<Directorate>();
		}
	}

	@Override
	public List<Division> getAllDivisionsGPG(String whereClause) {
		List<Division> results = null;
		results = psmFLJdbcTemplate.query(QueryString.GET_ALL_DIVISIONS_GPG_QUERY, new DivisionMapper());

		if (results != null && !results.isEmpty()) {
			LOGGER.info(String.format(resultLog, Integer.toString(results.size()), divisions));
			return results;
		} else {
			LOGGER.info(String.format(resultLog, "No", divisions));
			return new ArrayList<Division>();
		}
	}

	@Override
	public List<ProgramElement> getAllProgramElementsGPG(String exclusionList) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getAllProgramElementsGPG()");
		List<ProgramElement> results = null;

		results = psmFLJdbcTemplate.query(QueryString.GET_ALL_PROGRAM_ELEMENTS_GPG_QUERY, new ProgramElementMapper());

		if (results != null && !results.isEmpty()) {
			LOGGER.info(String.format(resultLog, Integer.toString(results.size()), programElements));
			return results;
		} else {
			LOGGER.info(String.format(resultLog, "No", programElements));
			return new ArrayList<ProgramElement>();
		}

	}

	@Override
	public void setGpgFundingOpportunityCode(String gpgFundingOpportunityCode) {
		this.gpgFundingOpportunityCode = gpgFundingOpportunityCode;
	}

	@Override
	public void setDivisionCodeMatchWhereClauseExt(String divisionCodeMatchWhereClauseExt) {
		this.divisionCodeMatchWhereClauseExt = divisionCodeMatchWhereClauseExt;
	}

	private boolean fundingOpIdIsGPG(String fundingOpId) {
		if (!StringUtils.isEmpty(fundingOpId)
		        && fundingOpId.trim().equalsIgnoreCase(gpgFundingOpportunityCode.trim())) {
			return true;
		}
		return false;
	}

	@Override
	public List<State> getStates() {
		LOGGER.info("SolicitationDataServiceDaoImpl.getStates()");
		List<State> states = new ArrayList<State>();
		states = this.psmFLJdbcTemplate.query(QueryString.GET_STATES_QUERY, new StateMapper());
		return states;
	}

	@Override
	public List<Country> getCountries() {
		LOGGER.info("SolicitationDataServiceDaoImpl.getCountries()");
		return this.psmFLJdbcTemplate.query(QueryString.GET_COUNTRIES_QUERY, new CountryMapper());

	}

	@Override
	public List<Deadline> getDueDates(String fundingOpId) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getDueDates()");
		List<Deadline> deadlines = new ArrayList<Deadline>();
		if (getDeadlineDatesCount(fundingOpId).isEmpty()) {
			Deadline deadline = setDefaultDueDate();
			deadlines.add(deadline);
			return deadlines;
		}
		deadlines = this.psmFLJdbcTemplate.query(QueryString.GET_DUEDATES_QUERY, new Object[] { fundingOpId },
		        new DueDateMapper());
		return deadlines;
	}
	
	@Override
    public Map<String, Deadlines> getDueDatesByFundingOpIds(FundingOpportunityParams params) {
        LOGGER.info("SolicitationDataServiceDaoImpl.getDueDates()");
        Map<String, Deadlines> deadlineMap = new LinkedHashMap<>();
        List<String> opIds = params.getFundingOpportunityIds();
        if(opIds != null && !opIds.isEmpty()) { // Append where clause to avoid using slower "in" syntax
            opIds.removeAll(Arrays.asList("", null));
            StringBuilder qryStr = new StringBuilder(QueryString.GET_DUEDATES_QUERY_WITHOUT_FUNDING_OP_ID);
            qryStr.append(" and (ddln.pgm_annc_id='");
            qryStr.append(Joiner.on("' or ddln.pgm_annc_id='").join(opIds));
            qryStr.append("')");
            List<Deadline> deadlineList = this.psmFLJdbcTemplate.query(qryStr.toString(),
                new DueDateFundingOpMapper());
            List<Deadline> deadlines = new ArrayList<>();
            String fundingOpId = null;
            String prevFundingOpId = null;
            for(int i=0; i < deadlineList.size(); i++) {
                Deadline deadline = deadlineList.get(i);
                fundingOpId = deadline.getFundingOppId().trim();
                if(prevFundingOpId != null && !fundingOpId.trim().equalsIgnoreCase(prevFundingOpId.trim())) {
                    Deadlines dLines = new Deadlines();
                    dLines.setDeadlines(deadlines);
                    deadlineMap.put(prevFundingOpId, dLines);
                    deadlines = new ArrayList<>();
                    deadlines.add(deadline);
                } else {
                    deadlines.add(deadline);
                }
                if(i == (deadlineList.size()-1)) {
                    Deadlines dLines = new Deadlines();
                    dLines.setDeadlines(deadlines);
                    deadlineMap.put(fundingOpId, dLines);
                }
                prevFundingOpId = fundingOpId;
            }
        }
        return deadlineMap;
    }

	private Deadline setDefaultDueDate() {
		Deadline deadline = new Deadline();
		deadline.setDeadlineTypeCode(Constants.DUE_DATE_ACCEPTED_ANYTIME_CODE);
		return deadline;

	}

	private List<Deadline> getDeadlineDatesCount(String fundingOpId) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getDeadlineDatesCount()");
		List<Deadline> deadlines = new ArrayList<Deadline>();
		deadlines = this.psmFLJdbcTemplate.query(QueryString.GET_DEADLINEDATES_QUERY, new Object[] { fundingOpId },
		        new DueDateMapper());
		return deadlines;
	}

	@Override
	public String getPostalCode(String stateCode, String postalCode) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getPostalCode()");

		try {
			return (String) psmPARSJdbcTemplate.queryForObject(QueryString.GET_POSTALCODE_QUERY,
			        new Object[] { stateCode, postalCode }, String.class);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public Pi getPIDetails(String nsfId) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getPIDetails()");
		try {
			return this.psmFLJdbcTemplate.queryForObject(QueryString.GET_PI_DETAILS_QUERY,
			        new Object[] { getUserSSN(nsfId) }, new PiMapper());
		} catch (EmptyResultDataAccessException e) {
			return new Pi();
		}

	}

	@Override
	public String getUserSSN(String nsfId) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getUserSSN()");
		try {
			return psmFLJdbcTemplate.queryForObject(QueryString.GET_USER_SSN_QUERY, new Object[] { nsfId },
			        String.class);
		} catch (EmptyResultDataAccessException e) {
			return nsfId;
		}

	}

	@Override
	public ProgramElement getProgramElementDetails(String code) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getProgramElementDetails()");
		try {
			return this.psmFLJdbcTemplate.queryForObject(QueryString.GET_PROGRAM_ELEMENT_DETAILS_QUERY,
			        new Object[] { code }, new ProgramElementMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ProgramElement();
		}
	}

	@Override
	public Directorate getDirectorateDetails(String code) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getDirectorateDetails()");
		try {
			return this.psmFLJdbcTemplate.queryForObject(QueryString.GET_DIRECTORATE_DETAILS_QUERY,
			        new Object[] { code }, new DirectorateMapper());
		} catch (EmptyResultDataAccessException e) {
			return new Directorate();
		}
	}

	@Override
	public Division getDivisionDetails(String code) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getDivisionDetails()");
		try {
			return this.psmFLJdbcTemplate.queryForObject(QueryString.GET_DIVISION_DETAILS_QUERY,
			        new Object[] { code }, new DivisionMapper());
		} catch (EmptyResultDataAccessException e) {
			return new Division();
		}
	}

	@Override
	public FundingOpportunity getFundingOpportunityDetails(String code) {
		LOGGER.info("SolicitationDataServiceDaoImpl.getFundingOpportunityDetails()");
		try {
			return this.psmFLJdbcTemplate.queryForObject(QueryString.GET_FUNDING_OPP_DETAILS_QUERY,
			        new Object[] { code }, new FundingOpportunityMapper());
		} catch (EmptyResultDataAccessException e) {
			return new FundingOpportunity();
		}
	}
	

	

}