package com.kashi.scoreboard.models;

import java.util.Set;

public class RankingDto {

    private final String competitorName;
    private final Set<TrackAndFieldEvent> events;
    private final Integer score;
    private final String rank;

    public RankingDto(String competitorName, Set<TrackAndFieldEvent> events, Integer score, String rank) {
        this.competitorName = competitorName;
        this.events = events;
        this.score = score;
        this.rank = rank;
    }

    public String getCompetitorName() {
        return competitorName;
    }

    public Set<TrackAndFieldEvent> getEvents() {
        return events;
    }

    public Integer getScore() {
        return score;
    }

    public String getRank() {
        return rank;
    }
}
