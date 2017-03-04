package org.wtiger.inno.litportal.dbtools.exceptions;

/**
 * DAO level exception
 */
public class DBException extends Exception {
    public DBException() {
        super();
    }

    public DBException(Exception e) {
        super(e);
    }
}
