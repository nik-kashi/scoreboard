package com.kashi.scoreboard.models;

public class TrackEvent extends TrackAndFieldEvent {
    public TrackEvent(String eventName, Double paramA, Double paramB, Double paramC, Double performance) {
        super(eventName, paramA, paramB, paramC, performance);
    }

    @Override
    public Integer score() {
        return ((Double) (paramA * Math.pow(paramB - performance, paramC))).intValue();
    }
}
