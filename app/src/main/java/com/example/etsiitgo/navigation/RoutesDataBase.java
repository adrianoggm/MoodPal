package com.example.etsiitgo.navigation;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class RoutesDataBase {
    @SerializedName("data")
    private Map<String, Route> data;

    public Map<String, Route> getData() {
        return data;
    }

    public void setData(Map<String, Route> data) {
        this.data = data;
    }

    public static class Route {
        private String destination;
        private Map<String, Node> nodes;

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public Map<String, Node> getNodes() {
            return nodes;
        }

        public void setNodes(Map<String, Node> nodes) {
            this.nodes = nodes;
        }
    }

    public static class Node {
        @SerializedName("next_node")
        private String nextNode;

        private String annotation;

        public String getNextNode() {
            return nextNode;
        }

        public void setNextNode(String nextNode) {
            this.nextNode = nextNode;
        }

        public String getAnnotation() {
            return annotation;
        }

        public void setAnnotation(String annotation) {
            this.annotation = annotation;
        }
    }
}
