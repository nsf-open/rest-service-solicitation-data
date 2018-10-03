package gov.nsf.psm.solicitation.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.nsf.psm.foundation.controller.PsmBaseController;
import gov.nsf.psm.foundation.ember.model.EmberModel;
import gov.nsf.psm.foundation.exception.CommonUtilException;
import gov.nsf.psm.foundation.model.FundingOpportunities;
import gov.nsf.psm.foundation.model.FundingOpportunityParams;
import gov.nsf.psm.foundation.model.PersonnelData;
import gov.nsf.psm.foundation.model.Pi;
import gov.nsf.psm.foundation.model.SectionResponse;
import gov.nsf.psm.foundation.model.lookup.Country;
import gov.nsf.psm.foundation.model.lookup.Deadline;
import gov.nsf.psm.foundation.model.lookup.DeadlineData;
import gov.nsf.psm.foundation.model.lookup.Deadlines;
import gov.nsf.psm.foundation.model.lookup.State;
import gov.nsf.psm.foundation.model.proposal.UOCInformation;
import gov.nsf.psm.solicitation.service.SolicitationDataService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1")
@ApiResponses(value = { @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error") })
public class ReferenceDataServiceController extends PsmBaseController {

	@Autowired
	SolicitationDataService solicitationDataService;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass()); 

	@ApiOperation(value = "Get all states in US", notes = "This API returns the list of states", response = State.class, responseContainer = "List")
	@RequestMapping(path = "/states", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getStates() throws CommonUtilException {
		LOGGER.info("ReferenceDataServiceController.getStates()");			
		
		List<State> states = solicitationDataService.getStates();
		return new EmberModel.Builder<>(State.class, states).build();
	}

	@ApiOperation(value = "Get all countries", notes = "This API returns the list of countries", response = State.class, responseContainer = "List")
	@RequestMapping(path = "/countries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getCountries() throws CommonUtilException {
		LOGGER.info("ReferenceDataServiceController.getCountries()");
		
		List<Country> countries = solicitationDataService.getCountries();
		return new EmberModel.Builder<>(Country.class, countries).build();
	}

	@ApiOperation(value = "Get all deadline dates for a funding opportunity id", notes = "This API returns the list of deadlines for a funding opportunity id", response = State.class, responseContainer = "List")
	@RequestMapping(path = "/dueDates/{fundingOpId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getDueDates(@PathVariable String fundingOpId) throws CommonUtilException {
		LOGGER.info("ReferenceDataServiceController.getDueDates()");
		
		List<Deadline> deadlines = solicitationDataService.getDueDates(fundingOpId);
		return new EmberModel.Builder<>(Deadline.class, deadlines).build();
	}
	
	@ApiOperation(value = "Get all deadline dates for various funding opportunity ids", notes = "This API returns the list of deadlines for a funding opportunity id", response = DeadlineData.class)
    @RequestMapping(path = "/dueDates/fundingOps", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmberModel getDueDatesForFundingOps(@RequestBody FundingOpportunityParams params) throws CommonUtilException {
        LOGGER.info("ReferenceDataServiceController.getDueDatesForFundingOps()");
        
        Map<String, Deadlines> deadlineMap = solicitationDataService.getDueDatesForFundingOps(params);
        DeadlineData data = new DeadlineData();
        data.setDeadlineMap(deadlineMap);
        return new EmberModel.Builder<>(DeadlineData.getClassCamelCaseName(), data).build();
    }

	@ApiOperation(value = "Check Postal Code Validation", notes = "This API returns postal code validation ", response = State.class, responseContainer = "String")
	@RequestMapping(path = "/postalCode/{stateCode}/{postalCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel isPostalCodeValid(@PathVariable String stateCode, @PathVariable String postalCode)
	        throws CommonUtilException {
		LOGGER.info("ReferenceDataServiceController.isPostalCodeValid()");
		
		boolean status = false;
		SectionResponse sectionResponse = new SectionResponse();
		status = solicitationDataService.isPostalCodeValid(stateCode, postalCode);
		sectionResponse.setIsPostalCodeValid(status);
		return new EmberModel.Builder<>("sectionResponse", sectionResponse).build();
	}
	
	@ApiOperation(value = "Get PI details ", notes = "This API returns the PI information for a given nsf id", response = Pi.class)
	@RequestMapping(path = "/pi/{nsfId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getPIDetails(@PathVariable String nsfId) throws CommonUtilException {
		LOGGER.info("ReferenceDataServiceController.getPIDetails()");		
		Pi pi = solicitationDataService.getPIDetails(nsfId);
		return new EmberModel.Builder<>(Pi.getClassCamelCaseName(), pi).build();
	}
	
}
