package com.kashi.scoreboard.service;

import com.kashi.scoreboard.models.CombinedEvent;
import com.kashi.scoreboard.models.RankingDto;
import com.kashi.scoreboard.utils.CsvUtils;
import com.kashi.scoreboard.utils.XmlUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreboardService {
    public static final String PARSER_METADATA_FILE_LOCATION = "parse-metadata.xml";

    public String calculateScores(String inputFileAddress) {
        final Set<CombinedEvent> combinedEvents = CsvUtils.parseResults(inputFileAddress, PARSER_METADATA_FILE_LOCATION);
        List<RankingDto> finalRanking = rankEvents(combinedEvents);
        return XmlUtils.generateResponse(finalRanking);
    }

    public List<RankingDto> rankEvents(Set<CombinedEvent> combinedEvents) {
        TreeMap<Integer, List<CombinedEvent>> sortedData = new TreeMap<>(Comparator.reverseOrder());
        List<RankingDto> ranking = new ArrayList<>();
        sortedData.putAll(combinedEvents.stream().collect(Collectors.groupingBy(CombinedEvent::score)));
        sortedData.forEach((score, sameRankEvents) -> {
            String rank;
            int currRank = ranking.size() + 1;
            if (sameRankEvents.size() == 1) {
                rank = String.valueOf(currRank);
            } else {
                rank = String.valueOf(currRank)
                        .concat("-")
                        .concat(String.valueOf(currRank + sameRankEvents.size() - 1));
            }
            sameRankEvents.forEach(combinedEvent -> ranking.add(new RankingDto(
                    combinedEvent.getCompetitorName(), combinedEvent.getEvents(), score, rank
            )));
        });
        return ranking;
    }
}
