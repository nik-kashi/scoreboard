package com.kashi.scoreboard.models;

import java.util.HashSet;
import java.util.Set;

/**
 * Composite design pattern
 */
public class CombinedEvent implements Event {

    private final String competitorName;
    private final Set<TrackAndFieldEvent> events = new HashSet<>();

    public CombinedEvent(String competitor) {
        this.competitorName = competitor;
    }

    @Override
    public Integer score() {
        return events.stream().mapToInt(Event::score).sum();
    }

    public void addEvent(TrackAndFieldEvent event) {
        events.add(event);
    }

    public String getCompetitorName() {
        return competitorName;
    }

    public Set<TrackAndFieldEvent> getEvents() {
        return events;
    }
}
