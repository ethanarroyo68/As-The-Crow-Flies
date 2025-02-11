package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NearRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(NearRequest.class);
    
    private Place place;
    private Integer distance;
    private Double earthRadius;
    private String formula;
    private Integer limit;
    private Places places;
    private Distances distances;

    public NearRequest(Place place, Integer distance, Double earthRadius, String formula, Integer limit) {
        this.place = place;
        this.distance = distance;
        this.earthRadius = earthRadius;
        this.formula = formula;
        this.limit = limit;
    }

    public NearRequest() {
        this.place = null;
        this.distance = null;
        this.earthRadius = null;
        this.formula = null;
        this.limit = null;
        this.places = null;
        this.distances = null;
    }

    public Place getPlace() {
        return this.place;
    }

    public Integer getDistance() {
        return this.distance;
    }

    public Double getEarthRadius() {
        return this.earthRadius;
    }

    public String getFormula() {
        return this.formula;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public Places getPlaces() {
        return this.places;
    }

    public Distances getDistances() {
        return this.distances;
    }

    @Override
    public void buildResponse() throws BadRequestException{
        validateFormula(this.formula);
        GeographicLocations locations = new GeographicLocations();
        Places allPlaces = locations.near(place, distance, earthRadius, formula);
        this.places = locations.limitPlaces(allPlaces, limit);
        this.distances = locations.distances(place, places);
        syncSort();
        log.trace("buildResponse -> {}", this);
    }

    public void syncSort() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < this.distances.size(); i++) {
            indices.add(i);
        }
        Collections.sort(indices, Comparator.comparingLong(this.distances::get));
        Distances sortedDistances = new Distances();
        Places sortedPlaces = new Places();
        for (int i : indices) {
            sortedDistances.add(this.distances.get(i));
            sortedPlaces.add(this.places.get(i));
        }
        this.distances.clear();
        this.distances.addAll(sortedDistances);
        this.places.clear();
        this.places.addAll(sortedPlaces);
    }
}
