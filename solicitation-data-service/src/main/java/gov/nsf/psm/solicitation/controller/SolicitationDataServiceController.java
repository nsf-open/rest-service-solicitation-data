package gov.nsf.psm.solicitation.controller;

import java.util.List;

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
import gov.nsf.psm.foundation.model.Directorate;
import gov.nsf.psm.foundation.model.Division;
import gov.nsf.psm.foundation.model.FundingOpportunity;
import gov.nsf.psm.foundation.model.ProgramElement;
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
public class SolicitationDataServiceController extends PsmBaseController {

	@Autowired
	SolicitationDataService solicitationDataService;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@ApiOperation(value = "Get all funding opportunity objects", notes = "This API returns the list of all funding opportunity objects", response = FundingOpportunity.class, responseContainer = "List")
	@RequestMapping(path = "/fundingOps", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getAllFundingOpportunities() throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getAllFundingOpportunities()");

		List<FundingOpportunity> fundingOpsList = solicitationDataService.getAllFundingOpportunities(null);
		return new EmberModel.Builder<>(FundingOpportunity.class, fundingOpsList).build();
	}

	@ApiOperation(value = "Get all funding opportunity objects", notes = "This API returns the list of all funding opportunity objects", response = FundingOpportunity.class, responseContainer = "List")
	@RequestMapping(path = "/fundingOps/{fundingOpId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getAllFundingOpportunitiesGPG(@PathVariable String fundingOpId) throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getAllFundingOpportunities()");

		List<FundingOpportunity> fundingOpsList = solicitationDataService.getAllFundingOpportunities(fundingOpId);
		return new EmberModel.Builder<>(FundingOpportunity.class, fundingOpsList).build();
	}

	@ApiOperation(value = "Get all division objects", notes = "This API returns the list of all division objects", response = Division.class, responseContainer = "List")
	@RequestMapping(path = "/divisions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getAllDivisions() throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getAllDivisions()");

		List<Division> divisionsList = solicitationDataService.getAllDivisions(null);
		return new EmberModel.Builder<>(Division.class, divisionsList).build();
	}

	@ApiOperation(value = "Get all division objects for a given funding opportunity id", notes = "This API returns the list of division objects", response = Division.class, responseContainer = "List")
	@RequestMapping(path = "/divisions/{fundingOpId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getAllDivisionsGPG(@PathVariable String fundingOpId) throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getAllDivisionsGPG()");
		
		List<Division> divisionsList = solicitationDataService.getAllDivisions(fundingOpId);
		return new EmberModel.Builder<>(Division.class, divisionsList).build();

	}

	@ApiOperation(value = "Get all directorate objects", notes = "This API returns the list of all directorate objects", response = Directorate.class, responseContainer = "List")
	@RequestMapping(path = "/directorates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getAllDirectorates() throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getAllDirectorates()");

		List<Directorate> directoratesList = solicitationDataService.getAllDirectorates(null);
		return new EmberModel.Builder<>(Directorate.class, directoratesList).build();
	}

	@ApiOperation(value = "Get all directorate objects for a given funding opportunity id", notes = "This API returns the list of directorate objects", response = Directorate.class, responseContainer = "List")
	@RequestMapping(path = "/directorates/{fundingOpId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getAllDirectoratesGPG(@PathVariable String fundingOpId) throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getAllDirectoratesGPG()");

		List<Directorate> directoratesList = solicitationDataService.getAllDirectorates(fundingOpId);
		return new EmberModel.Builder<>(Directorate.class, directoratesList).build();
	}

	@ApiOperation(value = "Get all program element objects", notes = "This API returns the list of all program element objects", response = ProgramElement.class, responseContainer = "List")
	@RequestMapping(path = "/programElements", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getAllProgramElements() throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getAllProgramElements()");

		List<ProgramElement> programElementsList = solicitationDataService.getAllProgramElements(null);
		return new EmberModel.Builder<>(ProgramElement.class, programElementsList).build();

	}

	@ApiOperation(value = "Get all program element objects for a given funding opportunity id", notes = "This API returns the list of program element objects", response = ProgramElement.class, responseContainer = "List")
	@RequestMapping(path = "/programElements/{fundingOpId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getAllProgramElementsGPG(@PathVariable String fundingOpId) throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getAllProgramElements()");

		List<ProgramElement> programElementsList = solicitationDataService.getAllProgramElements(fundingOpId);
		return new EmberModel.Builder<>(ProgramElement.class, programElementsList).build();
	}

	@ApiOperation(value = "Get all directorates objects for a given funding opportunity id", notes = "This API returns the list of program element objects", response = Directorate.class, responseContainer = "List")
	@RequestMapping(path = "/fundingOps/{fundingOpId}/directorates", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getDirectoratesByFundingOpId(@PathVariable String fundingOpId) throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getDirectoratesByFundingOpId()");

		List<Directorate> directoratesList = solicitationDataService.getDirectoratesByFundingOpId(fundingOpId);
		return new EmberModel.Builder<>(Directorate.class, directoratesList).build();
	}

	@ApiOperation(value = "Get a directorate object for a given funding opportunity id", notes = "This API returns a directorate object", response = Directorate.class)
	@RequestMapping(path = "/fundingOps/{fundingOpId}/directorates/{directorateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getDirectorateByFundingOpId(@PathVariable String fundingOpId, @PathVariable String directorateId)
	        throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getDirectorateByFundingOpId()");

		Directorate directorate = solicitationDataService.getDirectorateByFundingOpId(fundingOpId, directorateId);
		return new EmberModel.Builder<>(Directorate.class.getSimpleName().toLowerCase(), directorate).build();
	}

	@ApiOperation(value = "Get all division objects for a given funding opportunity id and directorate id", notes = "This API returns the list of division objects for a given opportunity id and directorate id", response = Division.class, responseContainer = "List")
	@RequestMapping(path = "/fundingOps/{fundingOpId}/directorates/{directorateId}/divisions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getDivisionsByFundingOpIdAndDirectorateId(@PathVariable String fundingOpId,
	        @PathVariable String directorateId) throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getDivisionsByFundingOpIdAndDirectorateId()");

		List<Division> divisionsList = solicitationDataService.getDivisionsByFundingOpIdAndDirectorateId(fundingOpId,
		        directorateId);
		return new EmberModel.Builder<>(Division.class, divisionsList).build();
	}

	@ApiOperation(value = "Get all program element objects for a given funding opportunity id and division id", notes = "This API returns a list of program element objects", response = ProgramElement.class, responseContainer = "List")
	@RequestMapping(path = "/fundingOps/{fundingOpId}/divisions/{divisionId}/programElements", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getProgramElementsByDivisionId(@PathVariable String fundingOpId, @PathVariable String divisionId)
	        throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getProgramElementsByDivisionId()");

		List<ProgramElement> programElementsList = solicitationDataService.getProgramElementsByDivisionId(fundingOpId,
		        divisionId);
		return new EmberModel.Builder<>(ProgramElement.class, programElementsList).build();
	}
		
	@ApiOperation(value = "Get all UOC details for a given directorate, division and program element code ", notes = "This API returns the list of UOCs with description", response = UOCInformation.class)
	@RequestMapping(path = "/fundingOps/uocs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmberModel getUOCDetails(@RequestBody UOCInformation uocInformation) throws CommonUtilException {
		LOGGER.info("SolicitationDataServiceController.getUOCDetails()");

		UOCInformation uOCInformation = solicitationDataService.getUOCDetails(uocInformation);
		return new EmberModel.Builder<>(UOCInformation.getClassCamelCaseName(), uOCInformation).build();
	}
  
    
    
}
