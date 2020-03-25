/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.chataja.usrdb.error;

/**
 *
 * @author ahmad
 */
public class UserManagerException extends Exception {
    
    private final UserManagerError errorType;

    public UserManagerException(UserManagerError errorType) {
        super(errorType.toString());
        this.errorType = errorType;
    }

    public UserManagerException(UserManagerError errorType, String message) {
        super("[" + errorType.toString() + "] " + message);
        this.errorType = errorType;
    }

    public UserManagerException(UserManagerError errorType, Throwable cause) {
        super(errorType.toString(),cause);
        this.errorType = errorType;
    }

    public UserManagerError getErrorType() {
        return errorType;
    }
    
}
