package units;

import com.kashi.scoreboard.models.CombinedEvent;
import com.kashi.scoreboard.models.RankingDto;
import com.kashi.scoreboard.models.TrackEvent;
import com.kashi.scoreboard.service.ScoreboardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ScoreboardServiceTest {

    static ScoreboardService scoreboardService;

    @BeforeAll
    public static void init() {
        scoreboardService = new ScoreboardService();
    }

    @Test
    public void rankEvents_3ofCompetitorsAreInSameScore_assignEqualRankForEqualScores() {
        HashSet<CombinedEvent> combinedEvents = new HashSet<>();
        CombinedEvent masonPerformance = new CombinedEvent("Mason");
        masonPerformance.addEvent(new TrackEvent("100M", 25.4347, 18.0, 1.81, 12.0));
        combinedEvents.add(masonPerformance);

        CombinedEvent ashPerformance = new CombinedEvent("Ash");
        ashPerformance.addEvent(new TrackEvent("100M", 25.4347, 18.0, 1.81, 11.0));
        combinedEvents.add(ashPerformance);

        CombinedEvent monaPerformance = new CombinedEvent("Mona");
        monaPerformance.addEvent(new TrackEvent("100M", 25.4347, 18.0, 1.81, 12.0));
        combinedEvents.add(monaPerformance);

        CombinedEvent vidaPerformance = new CombinedEvent("Vida");
        vidaPerformance.addEvent(new TrackEvent("100M", 25.4347, 18.0, 1.81, 13.0));
        combinedEvents.add(vidaPerformance);

        CombinedEvent billPerformance = new CombinedEvent("Bill");
        billPerformance.addEvent(new TrackEvent("100M", 25.4347, 18.0, 1.81, 12.0));
        combinedEvents.add(billPerformance);


        List<RankingDto> rankingDtos = scoreboardService.rankEvents(combinedEvents);
        Assertions.assertEquals(5, rankingDtos.size());
        Assertions.assertEquals("Ash", rankingDtos.get(0).getCompetitorName());
        Assertions.assertEquals("1", rankingDtos.get(0).getRank());

        Assertions.assertEquals(
                new HashSet<>(Arrays.asList("Mason", "Mona", "Bill")),
                new HashSet<>(Arrays.asList(
                        rankingDtos.get(1).getCompetitorName(),
                        rankingDtos.get(2).getCompetitorName(),
                        rankingDtos.get(3).getCompetitorName()
                )));
        Assertions.assertEquals("2-4", rankingDtos.get(1).getRank());
        Assertions.assertEquals("2-4", rankingDtos.get(2).getRank());
        Assertions.assertEquals("2-4", rankingDtos.get(3).getRank());

        Assertions.assertEquals("Vida", rankingDtos.get(4).getCompetitorName());
        Assertions.assertEquals("5", rankingDtos.get(4).getRank());

    }
}
