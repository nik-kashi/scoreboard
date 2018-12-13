package com.kashi.scoreboard.models;

public class EventMetadata {

    private final String eventName;
    private final Double paramA;
    private final Double paramB;
    private final Double paramC;
    private final EventType eventType;

    public EventMetadata(String eventName, Double paramA, Double paramB, Double paramC, EventType eventType) {
        this.eventName = eventName;
        this.paramA = paramA;
        this.paramB = paramB;
        this.paramC = paramC;
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public Double getParamA() {
        return paramA;
    }

    public Double getParamB() {
        return paramB;
    }

    public Double getParamC() {
        return paramC;
    }

    public EventType getEventType() {
        return eventType;
    }
}
