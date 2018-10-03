package gov.nsf.psm.solicitation.dto;

import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nsf.psm.foundation.model.FundingOpportunity;

/**
 * Extends the FundingOpportunity model class by adding three fields which are
 * used for data sorting. Implements an internal comparator to sort funding
 * opportunities in "reverse numerical" order.
 * 
 * @author JOHFLORE
 *
 */
public class FundingOpportunityDto extends FundingOpportunity {

    /**
     * 
     */
    private static final long serialVersionUID = -2731439396939469108L;

    private String  fundingOpportunityType;
    private Date    fundingOpportunityYear;
    private Integer fundingOpportunityNumber;

    /*
     * Constructor
     */
    public FundingOpportunityDto() {
        // empty constructor
    }

    public FundingOpportunityDto(String fundingOpportunityId, String fundingOpportunityTitle,
            String fundingOpportunityType, Date fundingOpportunityYear, Integer fundingOpportunityNumber) {
        this.setFundingOpportunityId(fundingOpportunityId);
        this.setFundingOpportunityTitle(fundingOpportunityTitle);
        this.fundingOpportunityType = fundingOpportunityType;
        this.fundingOpportunityYear = new Date(fundingOpportunityYear.getTime());
        this.fundingOpportunityNumber = fundingOpportunityNumber;
    }

    @JsonIgnore
    public String getFundingOpportunityType() {
        return fundingOpportunityType;
    }

    @JsonProperty
    public void setFundingOpportunityType(String fundingOpportunityType) {
        this.fundingOpportunityType = fundingOpportunityType;
    }

    @JsonIgnore
    public Date getFundingOpportunityYear() {
        return (Date) fundingOpportunityYear.clone();
    }

    @JsonProperty
    public void setFundingOpportunityYear(Date fundingOpportunityYear) {
        this.fundingOpportunityYear = fundingOpportunityYear == null ? null
                : new Date(fundingOpportunityYear.getTime());
    }

    @JsonIgnore
    public Integer getFundingOpportunityNumber() {
        return fundingOpportunityNumber;
    }

    @JsonProperty
    public void setFundingOpportunityNumber(Integer fundingOpportunityNumber) {
        this.fundingOpportunityNumber = fundingOpportunityNumber;
    }

    @Override
    public boolean equals(Object o) {
        boolean retVal = false;
        if (o == null)
            retVal = false;
        else {
            if (this.getClass() != o.getClass())
                retVal = false;
            if (((FundingOpportunityDto) o).getFundingOpportunityId().trim()
                    .equalsIgnoreCase(this.getFundingOpportunityId().trim())) {
                retVal = true;
            } else {
                retVal = false;
            }
        }
        return retVal;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.getFundingOpportunityId())
                .append(this.getFundingOpportunityTitle()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("fundingOpportunityId", this.getFundingOpportunityId())
                .append("fundingOpportunityTitle", this.getFundingOpportunityTitle())
                .append("fundingOpportunityType", this.fundingOpportunityType)
                .append("fundingOpportunityYear", this.fundingOpportunityYear.toString())
                .append("fundingOpportunityNum", this.fundingOpportunityNumber.toString()).toString();
    }

    /**
     * FundingOpportunityDtoComparator Comparator that implements reverse
     * numerical sorting order on funding opportunities
     * 
     * @author JOHFLORE
     *
     */
    public static class FundingOpportunityDtoComparator implements Comparator<FundingOpportunityDto> {

        /**
         * This method compares two funding opportunities by type, year and
         * number to determine which one has precedence in the listing order ex.
         * funding opportunity: NSF 16-123 type: NSF year: 16 number: 123
         */
        @Override
        public int compare(final FundingOpportunityDto fundingOp1, final FundingOpportunityDto fundingOp2) {
            int compareResult;

            // first check - compares the funding opportunity type (i.e., NSF,
            // PD, etc)
            compareResult = fundingOp1.getFundingOpportunityType().compareTo(fundingOp2.getFundingOpportunityType());

            // second check - compares the funding opportunity year
            if (compareResult == 0) {
                compareResult = fundingOp2.getFundingOpportunityYear()
                        .compareTo(fundingOp1.getFundingOpportunityYear());
            }

            // final check - compares the funding opportunity number
            if (compareResult == 0)
                compareResult = fundingOp2.getFundingOpportunityNumber()
                        .compareTo(fundingOp1.getFundingOpportunityNumber());
            return compareResult;
        }

    }
}
