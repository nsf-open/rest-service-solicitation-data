package gov.nsf.psm.solicitation.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolicitationDataException extends Exception {

    private static final long serialVersionUID = 1L;
    private final Logger LOGGER = LoggerFactory.getLogger(SolicitationDataException.class);
    private static  String PREFIX = "Solicitation Data: ";
    
    public SolicitationDataException() {
        super();
    }

    public SolicitationDataException(Exception e) {
        super(e.getMessage(), e.getCause());
        String msg = PREFIX + this.getMessage();
        Throwable cause = this.getCause();
        LOGGER.error(msg, cause);
    }

    public SolicitationDataException(String msg) {
        super(msg);
        LOGGER.error(msg);
    }

    public SolicitationDataException(String msg, Throwable cause) {
        super(msg, cause);
        LOGGER.error(PREFIX + msg, cause);
    }

    public String getErrMsg() {
        return PREFIX + this.getMessage();
    }
	
}
