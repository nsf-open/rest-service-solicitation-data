package gov.nsf.psm.solicitation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.Directorate;
import gov.nsf.psm.foundation.model.Division;
import gov.nsf.psm.foundation.model.FundingOpportunities;
import gov.nsf.psm.foundation.model.FundingOpportunity;
import gov.nsf.psm.foundation.model.FundingOpportunityParams;
import gov.nsf.psm.foundation.model.Pi;
import gov.nsf.psm.foundation.model.ProgramElement;
import gov.nsf.psm.foundation.model.UnitOfConsideration;
import gov.nsf.psm.foundation.model.lookup.Country;
import gov.nsf.psm.foundation.model.lookup.Deadline;
import gov.nsf.psm.foundation.model.lookup.Deadlines;
import gov.nsf.psm.foundation.model.lookup.State;
import gov.nsf.psm.foundation.model.proposal.UOCInformation;
import gov.nsf.psm.solicitation.common.Constants;
import gov.nsf.psm.solicitation.dao.SolicitationDataServiceDAO;

public class SolicitationDataServiceImpl implements SolicitationDataService {

    @Autowired
    SolicitationDataServiceDAO solicitationDao;

    @Value("${gpg.fundingOpportunityCode}")
    private String gpgFundingOpportunityCode;

    private final Logger LOGGER = LoggerFactory.getLogger(SolicitationDataServiceImpl.class);

    @Override
    public List<FundingOpportunity> getAllFundingOpportunities(String fundingOpId) throws CommonUtilException {
        LOGGER.info("SolicitationDataServiceImpl.getAllFundingOpportunities()");
        try {
            FundingOpportunities fundingOpportunities = new FundingOpportunities();
            List<FundingOpportunity> newFundingOpportunities = new ArrayList<FundingOpportunity>();
            fundingOpportunities.setFundingOpportunities(solicitationDao.getAllFundingOpportunities(fundingOpId));
            FundingOpportunity gpgFundingOpportunity = fundingOpportunities
                    .getFundingOpportunityByCode(gpgFundingOpportunityCode);
            if (gpgFundingOpportunity != null) {
                fundingOpportunities.remove(new FundingOpportunity(gpgFundingOpportunityCode, ""));
                newFundingOpportunities.add(gpgFundingOpportunity);
            }
            newFundingOpportunities.addAll(fundingOpportunities.getFundingOpportunities());
            return newFundingOpportunities;
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_FUNDING_OPPORTUNITIES_ERROR, e);
        }
    }

    @Override
    public List<Division> getAllDivisions(String fundingOpId) throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getAllDivisions()");
            return solicitationDao.getAllDivisions(fundingOpId);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_DIVISIONS_ERROR, e);
        }
    }

    @Override
    public List<Directorate> getAllDirectorates(String fundingOpId) throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getAllDirectorates()");
            return solicitationDao.getAllDirectorates(fundingOpId);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_DIRECTORATES_ERROR, e);
        }
    }

    @Override
    public List<ProgramElement> getAllProgramElements(String fundingOpId) throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getAllProgramElements()");
            return solicitationDao.getAllProgramElements(fundingOpId);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_ALL_PROGRAM_ELEMENTS_ERROR, e);
        }
    }

    @Override
    public List<Directorate> getDirectoratesByFundingOpId(String fundingOpId) throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getDirectoratesByFundingOpId()");
            return solicitationDao.getDirectoratesByFundingOpId(fundingOpId);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_DIRECTORATES_BY_FUND_OP_ID_ERROR, e);
        }
    }

    @Override
    public List<Division> getDivisionsByFundingOpIdAndDirectorateId(String fundingOpId, String directorateId)
            throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getDivisionsByFundingOpIdAndDirectorateId()");
            return solicitationDao.getDivisionsByFundingOpIdAndDirectorateId(fundingOpId, directorateId);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_DIVISIONS_BY_FUND_OP_ID_ERROR, e);
        }
    }

    @Override
    public List<ProgramElement> getProgramElementsByDivisionId(String fundingOpId, String divisionId)
            throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getProgramElementsByDivisionId()");
            return solicitationDao.getProgramElementsByDivisionId(fundingOpId, divisionId);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_PROGRAM_ELEMENTS_BY_DIV_CODE_ERROR, e);
        }
    }

    @Override
    public Directorate getDirectorateByFundingOpId(String fundingOpId, String divisionId) throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getDirectorateByFundingOpId()");
            return solicitationDao.getDirectorateByFundingOpId(fundingOpId, divisionId);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.GET_DIRECTORATE_BY_FUND_OP_ID_ERROR, e);
        }
    }

    public void setGpgFundingOpportunityCode(String gpgFundingOpportunityCode) {
        this.gpgFundingOpportunityCode = gpgFundingOpportunityCode;
    }

    @Override
    public List<State> getStates() throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getStates()");
            return solicitationDao.getStates();
        } catch (Exception e) {
            throw new CommonUtilException(Constants.STATES_NO_RESULTS_ERROR, e);
        }
    }

    @Override
    public List<Country> getCountries() throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getCountries()");
            return solicitationDao.getCountries();
        } catch (DataAccessException e) {
            throw new CommonUtilException(Constants.COUNTRY_NO_RESULTS_ERROR, e);
        }
    }

    @Override
    public List<Deadline> getDueDates(String fundingOpId) throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getDueDates()");
            return solicitationDao.getDueDates(fundingOpId);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DUE_DATE_NO_RESULTS_ERROR, e);
        }
    }
    
    @Override
    public Map<String, Deadlines> getDueDatesForFundingOps(FundingOpportunityParams params) throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getDueDatesForFundingOps()");
            return solicitationDao.getDueDatesByFundingOpIds(params);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.DUE_DATE_NO_RESULTS_ERROR, e);
        }
    }

    @Override
    public boolean isPostalCodeValid(String stateCode, String postalCode) throws CommonUtilException {

        boolean isValid = false;
        try {
            LOGGER.info("SolicitationDataServiceImpl.isPostalCodeValid()");
            if (postalCode != null) {
                postalCode = postalCode.replace("-", "");
            }
            String zipCode = solicitationDao.getPostalCode(stateCode, postalCode);
            if (zipCode != null) {
                isValid = true;
            }
        } catch (Exception e) {
            throw new CommonUtilException(Constants.POSTAL_CODE_NO_RESULTS_ERROR, e);
        }
        return isValid;
    }

    @Override
    public Pi getPIDetails(String nsfId) throws CommonUtilException {
        try {
            LOGGER.info("SolicitationDataServiceImpl.getPIDetails()");
            return solicitationDao.getPIDetails(nsfId);
        } catch (Exception e) {
            throw new CommonUtilException(Constants.PI_NO_RESULTS_ERROR, e);
        }
    }

    @Override
    public UOCInformation getUOCDetails(UOCInformation uocInformation) throws CommonUtilException {
        LOGGER.info("SolicitationDataServiceImpl.getUOCDetails()");

        UOCInformation returnUocInformation = new UOCInformation();

        List<UnitOfConsideration> returnUocList = new ArrayList<UnitOfConsideration>();

        for (UnitOfConsideration inputUoc : uocInformation.getUocs()) {
            UnitOfConsideration uoc = new UnitOfConsideration();
            Directorate dir = getDirectorateInformation(inputUoc.getDirectorate().getDirectorateCode());
            Division div = getDivisionInformation(inputUoc.getDivision().getDivisionCode());
            ProgramElement pgmEle = getProgramElementInformation(inputUoc.getProgramElement().getProgramElementCode());
            uoc.setDirectorate(dir);
            uoc.setDivision(div);
            uoc.setProgramElement(pgmEle);
            uoc.setUocOrdrNum(inputUoc.getUocOrdrNum());

            returnUocList.add(uoc);
        }
        returnUocInformation.setUocs(returnUocList.toArray(new UnitOfConsideration[returnUocList.size()]));

        returnUocInformation.setFundingOp(
                getFundingOpportunityInformation(uocInformation.getFundingOp().getFundingOpportunityId()));

        return returnUocInformation;
    }

    private FundingOpportunity getFundingOpportunityInformation(String fundCode) {

        return solicitationDao.getFundingOpportunityDetails(fundCode);

    }

    private Directorate getDirectorateInformation(String dirCode) {
        return solicitationDao.getDirectorateDetails(dirCode);
    }

    private Division getDivisionInformation(String divCode) {
        return solicitationDao.getDivisionDetails(divCode);

    }

    private ProgramElement getProgramElementInformation(String peCode) {
        return solicitationDao.getProgramElementDetails(peCode);

    }

}
