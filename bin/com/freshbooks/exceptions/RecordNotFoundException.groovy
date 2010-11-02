package com.freshbooks.exceptions

/**
 *
 * @author varnadoj
 */
class RecordNotFoundException extends APIException{


    public RecordNotFoundException() {
        super();
    }

    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(Throwable cause) {
        super(cause);
    }


}

