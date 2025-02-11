package com.tco.requests;

import com.tco.misc.BadRequestException;

public abstract class Request {

    protected String requestType;

    public String getRequestType() {
        return requestType;
    }
    
    protected void validateFormula(String formula) throws BadRequestException {
        if (formula != null && !formula.isEmpty()) {
            if (!formula.equals("haversine") && !formula.equals("cosines") && !formula.equals("vincenty")) {
                throw new BadRequestException("Invalid formula: " + formula);
            }
        }
    }
    // Overrideable Methods
    public abstract void buildResponse() throws BadRequestException;
}
