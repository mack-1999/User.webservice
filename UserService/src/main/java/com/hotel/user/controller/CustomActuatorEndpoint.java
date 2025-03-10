package com.hotel.user.controller;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "customEndpoint") // Defines the endpoint URL as /actuator/customEndpoint
public class CustomActuatorEndpoint {

    @ReadOperation  // Handles GET requests
    public String customInfo() {
        return "This is a custom actuator endpoint!";
    }
    
    @ReadOperation
    public String getSelectedInfo(@Selector String param) {
        return "Executed " + param;
    }

    @WriteOperation  // Handles POST requests
    public String updateInfo(String param) {
        return "Executed " + param;
    }

    @DeleteOperation  // Handles DELETE requests
    public String deleteInfo() {
        return "Custom endpoint data deleted!";
    }
}

