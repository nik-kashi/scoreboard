package com.kashi.scoreboard.models;

public abstract class TrackAndFieldEvent implements Event {
    final String eventName;
    final Double paramA;
    final Double paramB;
    final Double paramC;
    final Double performance;

    protected TrackAndFieldEvent(String eventName, Double paramA, Double paramB, Double paramC, Double performance) {
        this.eventName = eventName;
        this.paramA = paramA;
        this.paramB = paramB;
        this.paramC = paramC;
        this.performance = performance;
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

    public Double getPerformance() {
        return performance;
    }
}
