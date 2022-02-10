package be.kdg.java2.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NO_CONTENT, reason="No such Location" )
public class LocationNotFoundException extends RuntimeException{ //<-- A Runtime exception
    private String location;
    public LocationNotFoundException(String message, String location) {
        super(message);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
