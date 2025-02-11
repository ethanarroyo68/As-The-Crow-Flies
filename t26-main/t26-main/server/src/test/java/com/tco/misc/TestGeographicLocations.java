package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;

public class TestGeographicLocations {
   private GeographicLocations locations;
   private Place testPlace;

   @BeforeEach
   public void setUp() {
       locations = new GeographicLocations();
       testPlace = new Place("40.5734", "-105.0865");
   }

   @Test
   @DisplayName("mboin: Test near() returns empty Places with invalid input") 
   public void testNearReturnsEmptyPlacesOnInvalidInput() {
       Place invalidPlace = new Place("invalid", "-105.0865");
       Places results = locations.near(invalidPlace, 10, 6371.0, "vincenty");
       results = locations.limitPlaces(results, 5);
       assertNotNull(results);
       assertEquals(0, results.size());
   }

   @Test
   @DisplayName("mboin: Test near() returns valid Places object")
   public void testNearReturnsValidPlaces() {
       Places results = locations.near(testPlace, 10, 6371.0, "vincenty");
       results = locations.limitPlaces(results, 5);
       assertNotNull(results);
   }

   @Test
   @DisplayName("mboin: Test near() respects the limit parameter")
   public void testNearRespectsLimit() {
       int limit = 3;
       Places results = locations.near(testPlace, 50, 6371.0, "vincenty");
       results = locations.limitPlaces(results, limit);
       assertTrue(results.size() <= limit);
   }

   @Test
   @DisplayName("mboin: Test distances() matches filtered places count")
   public void testDistancesMatchesFilteredPlaces() {
       Places places = locations.near(testPlace, 10, 6371.0, "vincenty");
       places = locations.limitPlaces(places, 5);
       Distances distances = locations.distances(testPlace, places);
       assertNotNull(distances);
       assertEquals(places.size(), distances.size());
   }

   @Test
   @DisplayName("c835266433: Test near() returns places in order of distance")
   public void testNearOrderReturn() {
       Places places = locations.near(testPlace, 10, 6371.0, "vincenty");
       places = locations.limitPlaces(places, 5);
       Distances distances = locations.distances(testPlace, places);
       for (int i = 0; i < distances.size() - 1; i++) {
           assertTrue(distances.get(i) <= distances.get(i + 1));
       }
   }

   @Test
   @DisplayName("dampierj: Test find() handles multiple values in type")
   public void testFindMultipleTypeValues() {
       List<String> type = new ArrayList<>();
       List<String> where = new ArrayList<>();
       type.add("small_airport");
       type.add("large_airport");
       where.add("United States");

       Places results = locations.find("denver", type, where, 30);
       assertNotNull(results);

       for (Place place : results) {
           assertTrue(type.contains(place.get("type")),
                   "Expected type to be either 'small_airport' or 'large_airport'");
           assertEquals("United States", place.get("country"),
                   "Expected country to be 'United States'");
       }
   }

   @Test
   @DisplayName("dampierj: Test found() not null")
   public void testFoundNotNull() {
       List<String> type = new ArrayList<>();
       List<String> where = new ArrayList<>();
       type.add("small_airport");
       type.add("large_airport");
       where.add("United States");
       locations.find("denver", type, where, 30);

       Integer totalFound = locations.found("denver", type, where);
       assertNotNull(totalFound);
   }

   @Test
   @DisplayName("dampierj: Test search rad cross pole")
   void testSearchRadiusCrossesPole() {
       Place place = new Place("89.0", "0.0");
       boolean result = GeographicLocations.searchRadiusCrossesPole(place, 200, 6371.0, "haversine", 89.0);
       assertTrue(result);
   }

   @Test
   @DisplayName("dampierj: Test search diam exceeds circumference")
   void testSearchDiameterExceedsCircumference() {
       boolean result = GeographicLocations.searchDiameterExceedsCircum(0, 6371.0, 40000);
       assertTrue(result);
   }

   @Test
   @DisplayName("dampierj: Test addUniquePlaces")
   void testAddUniquePlaces() throws Exception {
       Places uniquePlaces = new Places();
       Places newPlaces = new Places();

       Place place1 = new Place("40.0", "-105.0");
       place1.put("id", "1");
       uniquePlaces.add(place1);

       Place place2 = new Place("41.0", "-106.0");
       place2.put("id", "2");
       newPlaces.add(place2);

       Place duplicatePlace = new Place("40.0", "-105.0");
       duplicatePlace.put("id", "1");
       newPlaces.add(duplicatePlace);

       Method addUniquePlacesMethod = GeographicLocations.class.getDeclaredMethod("addUniquePlaces", Places.class, Places.class);
       addUniquePlacesMethod.setAccessible(true);

       addUniquePlacesMethod.invoke(locations, uniquePlaces, newPlaces);
       assertEquals(2, uniquePlaces.size());
       assertTrue(uniquePlaces.contains(place1));
       assertTrue(uniquePlaces.contains(place2));
   }
}