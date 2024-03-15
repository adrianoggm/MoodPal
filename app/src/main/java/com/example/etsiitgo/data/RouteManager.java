package com.example.etsiitgo.data;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.etsiitgo.navigation.CheckpointDataBase;
import com.example.etsiitgo.navigation.RoutesDataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RouteManager {

    private final String DEBUG_TAG = "RouteManagerDEBUG";
    private final double CHECKPOINT_RADIUS_THRESHOLD = 10; // measured in meters
    private final double CHECKPOINT_HEIGHT_THRESHOLD = 1.5; // measured in meters

    private int accumulatedStaticPosition = 0;

    private final Map<String,Location> checkpointsDatabase;
    private class Route {
        private class Node {
            private String name;
            private String next;
            private String annotation;
            public Node (String name, String next, String annotation) {
                this.name = name;
                this.next = next;
                this.annotation = annotation;
            }

            public String getAnnotation() {
                return annotation;
            }

        }
        private final Map<String,Node> routeNodes;
        private String currentNode;
        private final String destinationNode;
        private boolean active = false;
        public Route (RoutesDataBase.Route route) {

            routeNodes = new HashMap<>();
            currentNode = null;
            destinationNode = route.getDestination();

            for ( String nodeId : route.getNodes().keySet() ) {
                RoutesDataBase.Node node = route.getNodes().get(nodeId);
                routeNodes.put(nodeId,new Node(nodeId,node.getNextNode(),node.getAnnotation()));
            }

        }

        public boolean isActive() {
            return active;
        }
        public boolean isCompleted() {
            return currentNode.equals(destinationNode);
        }
        public void start() {
            active = true;
        }

        public boolean update(Location location) {

            boolean locationChanged = false;

            if ( !active ) return false;

            double minDistance = Double.MAX_VALUE;
            double heightDiff = Double.MAX_VALUE;
            String nearestNode = null;
            for (String id : routeNodes.keySet()) {

                double distance = checkpointsDatabase.get(id).distanceTo(location);
                if ( distance < minDistance ) {
                    nearestNode = id;
                    minDistance = distance;
                    heightDiff = Math.abs(Objects.requireNonNull(checkpointsDatabase.get(id)).getAltitude() - location.getAltitude());
                }

            }

            if ( currentNode == null ) { // if current node is null, then just get the closest node
                currentNode = nearestNode;
                locationChanged = true;
            }

            else if ( minDistance <= CHECKPOINT_RADIUS_THRESHOLD &&  heightDiff <= CHECKPOINT_HEIGHT_THRESHOLD ) { // if you are in a node, change if u are close enough to another node
                currentNode = nearestNode;
                locationChanged = true;
            }

            return locationChanged;

        }

        public void end() {

            currentNode = null;
            active = false;

        }

        public String getCurrentLocation() {
            return currentNode;
        }

        public String getNextLocation() {

            if ( currentNode == null ) return null;
            Log.d(DEBUG_TAG,"currentNodeInRoute:" + currentNode);
            Log.d(DEBUG_TAG,"nextNodeInRoute:" + routeNodes.get(currentNode).next);
            return Objects.requireNonNull(routeNodes.get(currentNode)).next;

        }

        public String getCurrentAnnotation() {
            if ( currentNode == null ) return null;
            return routeNodes.get(currentNode).getAnnotation();
        }

    }
    private final Map<String,Route> routesDatabase;
    private String currentRoute;
    public RouteManager () {
        checkpointsDatabase = new HashMap<>();
        routesDatabase = new HashMap<>();
        currentRoute = null;
    }

    public Map<String,Route> getRoutesDatabase() {
        return routesDatabase;
    }

    public String getCurrentRoute() {
        return currentRoute;
    }

    public Map<String,Location> getCheckpointsDatabase() {
        return checkpointsDatabase;
    }

    public void setCheckpointDatabase(@NonNull CheckpointDataBase data) {
        for ( String checkpointId : data.getData().keySet() ) { // esto es redundante pero me da pereza cambiarlo

            Location location = data.getData().get(checkpointId);
            assert location != null;
            addCheckpoint(checkpointId,location);

        }
    }

    public void addCheckpoint(String name, Location location) {
        checkpointsDatabase.put(name,location);
    }

    public void setRoutesDatabase(RoutesDataBase data) {

        for ( String routeId : data.getData().keySet() ) {
            routesDatabase.put(routeId,new Route(data.getData().get(routeId)));
        }

    }

    public void startRoute(String name) {

        if ( routesDatabase.containsKey(name) ) {
            currentRoute = name;
            routesDatabase.get(name).start();
            Log.d(DEBUG_TAG,routesDatabase.toString());
        }

    }

    public boolean updateRoute(Location currentLocation) {
        boolean locationChanged = false;

        if ( routesDatabase.containsKey(currentRoute) ) {
            locationChanged = routesDatabase.get(currentRoute).update(currentLocation);
        }

        if ( locationChanged ) {
            accumulatedStaticPosition = 0;
        }
        else {
            accumulatedStaticPosition++;
        }

        return locationChanged;

    }
    public boolean isActiveRoute() {

        return currentRoute != null;

    }

    public int getAccumulatedStaticPosition() {
        return accumulatedStaticPosition;
    }

    public void resetAccumulatedStaticPosition() {
        accumulatedStaticPosition = 0;
    }

    public boolean isRouteCompleted() {

        if ( currentRoute == null ) return true;
        return routesDatabase.get(currentRoute).isCompleted();

    }

    public void endRoute() {

        if ( currentRoute != null ) {
            routesDatabase.get(currentRoute).end();
        }
        currentRoute = null;

    }

    public String getCurrentLocation() {

        if ( currentRoute == null ) return null;

        return routesDatabase.get(currentRoute).getCurrentLocation();

    }

    public String getNextLocation() {

        if ( currentRoute == null ) return null;

        Log.d(DEBUG_TAG,"CurrentRoute:" + currentRoute);
        Log.d(DEBUG_TAG,"NextLocation:" + routesDatabase.get(currentRoute).getNextLocation());

        return Objects.requireNonNull(routesDatabase.get(currentRoute)).getNextLocation();

    }

    public String getCurrentAnnotation() {

        if ( currentRoute == null ) return null;
        return routesDatabase.get(currentRoute).getCurrentAnnotation();

    }

}
