package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.*;

public class TourRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);
    private Places places;
    private double earthRadius;
    private String formula;
    private double response;

    public TourRequest(double earthRadius, String formula, Places places) {
        // super();
        this.places = places;
        this.formula = formula;
        this.earthRadius = earthRadius;
    }

    public TourRequest() {
        this.formula = null;
        this.earthRadius = 0.0;
    }

    public double getearthRadius() {
        return this.earthRadius;
    }

    public String getformula() {
        return this.formula;
    }

    public Places getplaces() {
        return this.places;
    }

    @Override
    public void buildResponse() throws BadRequestException{
        validateFormula(this.formula);
        TourConstruction tour = OptimizerFactory.getOptimizer(places.size(), response);
        this.places = tour.construct(this.places, this.earthRadius, this.formula, this.response);
        log.trace("buildResponse -> {}", this);
    }

}
