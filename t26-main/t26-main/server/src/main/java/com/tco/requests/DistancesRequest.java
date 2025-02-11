package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.*;

public class DistancesRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);
    private Places places;
    private double earthRadius;
    private String formula;
    private Distances distances;

    public DistancesRequest(double earthRadius, String formula, Places places) {
        // super();
        this.places = places;
        this.formula = formula;
        this.earthRadius = earthRadius;
    }

    public DistancesRequest() {
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

    public Distances earthdistances() {
        return this.distances;
    }

    @Override
    public void buildResponse() throws BadRequestException {
        validateFormula(this.formula);
        distances = buildDistanceList();
        log.trace("buildResponse -> {}", this);
    }

    private Distances buildDistanceList() {
        Distances distances = new Distances();
        DistanceCalculator calculator = CalculatorFactory.getCalculator(this.formula);

        for(int i = 0; i < places.size(); i++) {
            Place curr = places.get(i);
            Place next = places.get((i + 1) % places.size()); // removes need for if statement
            long distance = (long) calculator.between(curr, next, earthRadius);
            distances.add(distance);
        }

        return distances;
    }
}
