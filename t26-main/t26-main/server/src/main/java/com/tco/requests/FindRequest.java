package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tco.misc.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FindRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(FindRequest.class);
    
    private String match;
    private List<String> type;
    private List<String> where;
    private Integer limit;
    private Integer found = 0;
    private Places places;

    public FindRequest(String match, List<String> type, List<String> where, Integer limit) {
        this.match = match;
        this.type = type;
        this.where = where;
        this.limit = limit;
    }

    public FindRequest() {
        this.match = null;
        this.type = null;
        this.where = null;
        this.limit = null;
    }

    public String getMatch() {
        return this.match;
    }

    public java.util.List<String> getType() {
        return this.type;
    }

    public java.util.List<String> getWhere() {
        return this.where;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public Places getPlaces() {
        return this.places;
    }

    public Integer getFound() {
        return this.found;
    }

    @Override
    public void buildResponse() {
        GeographicLocations locations = new GeographicLocations();
        this.places = locations.find(match, type, where, limit);
        this.found = locations.found(match, type, where);
        log.trace("buildResponse -> {}", this);
    }
}
