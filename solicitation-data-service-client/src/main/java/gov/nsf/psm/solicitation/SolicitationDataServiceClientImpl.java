package gov.nsf.psm.solicitation;

import java.util.List;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.COA;
import gov.nsf.psm.foundation.model.Directorate;
import gov.nsf.psm.foundation.model.DirectorateWrapper;
import gov.nsf.psm.foundation.model.Directorates;
import gov.nsf.psm.foundation.model.Division;
import gov.nsf.psm.foundation.model.Divisions;
import gov.nsf.psm.foundation.model.FundingOpportunities;
import gov.nsf.psm.foundation.model.FundingOpportunity;
import gov.nsf.psm.foundation.model.FundingOpportunityParams;
import gov.nsf.psm.foundation.model.Pi;
import gov.nsf.psm.foundation.model.PiWrapper;
import gov.nsf.psm.foundation.model.ProgramElement;
import gov.nsf.psm.foundation.model.ProgramElements;
import gov.nsf.psm.foundation.model.SectionResponse;
import gov.nsf.psm.foundation.model.SectionResponseWrapper;
import gov.nsf.psm.foundation.model.lookup.Countries;
import gov.nsf.psm.foundation.model.lookup.Country;
import gov.nsf.psm.foundation.model.lookup.Deadline;
import gov.nsf.psm.foundation.model.lookup.DeadlineData;
import gov.nsf.psm.foundation.model.lookup.DeadlineDataWrapper;
import gov.nsf.psm.foundation.model.lookup.Deadlines;
import gov.nsf.psm.foundation.model.lookup.State;
import gov.nsf.psm.foundation.model.lookup.States;
import gov.nsf.psm.foundation.model.proposal.UOCInformation;
import gov.nsf.psm.foundation.model.proposal.UOCInformationWrapper;
import gov.nsf.psm.foundation.restclient.NsfRestTemplate;

public class SolicitationDataServiceClientImpl implements SolicitationDataServiceClient {

	private String serverURL;
	private String username;
	private String password;
	private boolean authenticationRequired;
	private int requestTimeout;
	private String fundingOpsURL = "/fundingOps";
	private String divisionsURL = "/divisions";
	private String directoratesURL = "/directorates";
	private String programElementsURL = "/programElements";
	private String uocsURL = "/uocs";
	private String statesURL = "/states";
	private String countryURL = "/countries";
	private String dueDateURL = "/dueDates";
	private String postalCodeURL = "/postalCode";
	private String piURL = "/pi";

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass()); 

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String solicitationDataServiceServerURL) {
		this.serverURL = solicitationDataServiceServerURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String solicitationDataServiceUsername) {
		this.username = solicitationDataServiceUsername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String solicitationDataServicePassword) {
		this.password = solicitationDataServicePassword;
	}

	public boolean isAuthenticationRequired() {
		return authenticationRequired;
	}

	public void setAuthenticationRequired(boolean authenticationRequired) {
		this.authenticationRequired = authenticationRequired;
	}

	public int getRequestTimeout() {
		return requestTimeout;
	}

	public void setRequestTimeout(int requestTimeout) {
		this.requestTimeout = requestTimeout;
	}
	
	private HttpEntity<String> getHttpEntity(boolean authRequired) {
		return authRequired ? NsfRestTemplate.createHttpEntityWithAuthentication(username, password) : null; 
	}
	
	@Override
	public List<FundingOpportunity> getAllFundingOpportunities() throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(fundingOpsURL);
			ResponseEntity<FundingOpportunities> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        FundingOpportunities.class);
			return response.getBody().getFundingOpportunities();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}

	@Override
	public List<Division> getAllDivisions() throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(divisionsURL);
			ResponseEntity<Divisions> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        Divisions.class);
			return response.getBody().getDivisions();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}

	@Override
	public List<Directorate> getAllDirectorates() throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(directoratesURL);
			ResponseEntity<Directorates> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        Directorates.class);
			return response.getBody().getDirectorates();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}

	@Override
	public List<ProgramElement> getAllProgramElements() throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(programElementsURL);
			ResponseEntity<ProgramElements> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        ProgramElements.class);
			return response.getBody().getProgramElements();
		} catch (CommonUtilException e) {
			throw new CommonUtilException(e);
		}
	}

	@Override
	public List<Directorate> getDirectoratesByFundingOpId(String fundingOpId) throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(fundingOpsURL);
			endpointUrl.append("/" + fundingOpId);
			endpointUrl.append(directoratesURL);
			ResponseEntity<Directorates> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        Directorates.class);
			
			return response.getBody().getDirectorates();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}

	@Override
	public Directorate getDirectorateByFundingOpId(String fundingOpId, String directorateId)
	        throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(fundingOpsURL);
			endpointUrl.append("/" + fundingOpId);
			endpointUrl.append(directoratesURL);
			endpointUrl.append("/" + directorateId);
			ResponseEntity<DirectorateWrapper> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        DirectorateWrapper.class);
			return response.getBody().getDirectorate();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}

	@Override
	public List<Division> getDivisionsByFundingOpIdAndDirectorateId(String fundingOpId, String directorateId)
	        throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(fundingOpsURL);
			endpointUrl.append("/" + fundingOpId);
			endpointUrl.append(directoratesURL);
			endpointUrl.append("/" + directorateId);
			endpointUrl.append(divisionsURL);
			ResponseEntity<Divisions> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        Divisions.class);
			return response.getBody().getDivisions();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}

	@Override
	public List<ProgramElement> getProgramElementsByDivisionId(String fundingOpId, String directorateId,
	        String divisionId) throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(fundingOpsURL);
			endpointUrl.append("/" + fundingOpId);
			endpointUrl.append(divisionsURL);
			endpointUrl.append("/" + divisionId);
			endpointUrl.append(programElementsURL);
			ResponseEntity<ProgramElements> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        ProgramElements.class);
			return response.getBody().getProgramElements();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}

	}

	
	/**
	 * Generates a string of comma separated values from an array of strings
	 * 
	 * @param value
	 */
	public static String arrayToCommaSeparatedString(String[] inputs) {
		StringJoiner joiner = new StringJoiner(",");
		for (int i = 0; i < inputs.length; i++) {
			joiner.add(inputs[i]);
		}
		return joiner.toString();
	}

	@Override
	public List<State> getStates() throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(statesURL);
			ResponseEntity<States> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        States.class);
			return response.getBody().getStates();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}

	@Override
	public List<Country> getCountries() throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(countryURL);
			ResponseEntity<Countries> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        Countries.class);
			return response.getBody().getCountries();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}

	@Override
	public List<Deadline> getDueDates(String fundingOpId) throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(dueDateURL);
			endpointUrl.append("/" + fundingOpId);
			ResponseEntity<Deadlines> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        Deadlines.class);
			return response.getBody().getDeadlines();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}

	@Override
	public DeadlineData getDueDatesForFundingOps(FundingOpportunityParams params) throws CommonUtilException {
	    ResponseEntity<DeadlineDataWrapper> response = null;
	    try {
            RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
                    requestTimeout);
            StringBuilder endpointUrl = new StringBuilder(serverURL);
            endpointUrl.append(dueDateURL);
            endpointUrl.append("/fundingOps");
          
            HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
            HttpEntity<FundingOpportunityParams> httpEntity = new HttpEntity<FundingOpportunityParams>(params, headers);
            
            LOGGER.debug("Executing POST request on: " + endpointUrl);
            response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.POST, httpEntity,
                    DeadlineDataWrapper.class);
            return response.getBody().getDeadlineData();
        } catch (Exception e) {
            throw new CommonUtilException(e);
        }
	}
	
	@Override
	public SectionResponse isPostalCodeValid(String stateCode, String postalCode) throws CommonUtilException {
		try {
			RestTemplate proposalDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);

			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(postalCodeURL);
			endpointUrl.append("/" + stateCode);
			endpointUrl.append("/" + postalCode);

			ResponseEntity<SectionResponseWrapper> response = null;			
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = proposalDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
			        SectionResponseWrapper.class);
			return response.getBody().getSectionResponse();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}

	@Override
	public Pi getPIDetails(String nsfId) throws CommonUtilException {
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(piURL);
			endpointUrl.append("/" + nsfId);
			ResponseEntity<PiWrapper> response = null;
			HttpEntity<String> httpEntity = getHttpEntity(authenticationRequired);
			
			LOGGER.debug("Executing GET request on: " + endpointUrl);
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.GET, httpEntity,
					PiWrapper.class);
			return response.getBody().getPi();
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}

	
    /**
     * Helper method to create headers with basic authentication
     * @param username
     * @param password
     * @return
     */
	private static HttpHeaders createHttpHeaders(boolean authenticationRequired, String username, String password) {
		return authenticationRequired ? NsfRestTemplate.createHeaderswithAuthentication(username, password) : new HttpHeaders();
	}

	@Override
	public UOCInformation getUOCDetails(UOCInformation uocInformation) throws CommonUtilException {

		ResponseEntity<UOCInformationWrapper> response = null;
		try {
			RestTemplate solicitationDataServiceClient = NsfRestTemplate.setupRestTemplate(authenticationRequired,
			        requestTimeout);
			StringBuilder endpointUrl = new StringBuilder(serverURL);
			endpointUrl.append(fundingOpsURL);
			endpointUrl.append(uocsURL);

			HttpHeaders headers = createHttpHeaders(authenticationRequired, username, password);
			HttpEntity<UOCInformation> httpEntity = new HttpEntity<UOCInformation>(uocInformation, headers);
			
			LOGGER.debug("Executing POST request to getUOCDetails : " + endpointUrl.toString());
			response = solicitationDataServiceClient.exchange(endpointUrl.toString(), HttpMethod.POST, httpEntity,
			        UOCInformationWrapper.class);
			return response != null ? response.getBody().getuOCInformation() : new UOCInformation(); // should we return null?
		} catch (Exception e) {
			throw new CommonUtilException(e);
		}
	}
}
