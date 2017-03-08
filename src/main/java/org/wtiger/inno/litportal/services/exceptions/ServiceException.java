package org.wtiger.inno.litportal.services.exceptions;

/**
 * Service level exception
 */
public class ServiceException extends Exception {

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        super(msg);

    }
}
