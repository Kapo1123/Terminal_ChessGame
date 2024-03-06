package dataAccess;

/**
 * Indicates there was an error connecting to the database
 */
public class DataAccessException extends Exception {
    private int errorCode;

    public DataAccessException(String message) {
        super(message);
        this.errorCode = determineErrorCode(message);
    }

    public int getErrorCode() {
        return errorCode;
    }

    private int determineErrorCode(String errorMessage) {
        int code = 0;
        if (errorMessage.equals("Error: unauthorized")) {
            code = 401;
        } else if (errorMessage.equals("Error: bad request")) {
            code = 400;
        } else if (errorMessage.equals("Error: already taken")) {
            code = 403;
        }
        else{
            code = 500;
        }
        return code;
    }
}


