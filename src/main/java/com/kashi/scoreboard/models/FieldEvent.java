package com.kashi.scoreboard.models;

public class FieldEvent extends TrackAndFieldEvent {
    public FieldEvent(String eventName, Double paramA, Double paramB, Double paramC, Double performance) {
        super(eventName, paramA, paramB, paramC, performance);
    }

    @Override
    public Integer score() {
        return ((Double) (paramA * Math.pow(performance - paramB, paramC))).intValue();
    }
}
