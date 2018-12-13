package com.kashi.scoreboard.utils;

import com.kashi.scoreboard.models.*;

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CsvUtils {
    public static Set<CombinedEvent> parseResults(String resultsLocation, String metadataLocation) {
        try {
            FileReader resultsFile = new FileReader(resultsLocation);
            BufferedReader reader = new BufferedReader(resultsFile);
            List<EventMetadata> metadatas = XmlUtils.parseMetadata(metadataLocation);
            return reader.lines().map(s -> {
                String[] csvFields = s.split(";");
                CombinedEvent combinedEvent = new CombinedEvent(csvFields[0]);
                for (int i = 1; i < csvFields.length; i++) {
                    EventMetadata eventMetadata = metadatas.get(i - 1);
                    if (eventMetadata.getEventType().equals(EventType.TRACK)) {
                        combinedEvent.addEvent(new TrackEvent(eventMetadata.getEventName()
                                , eventMetadata.getParamA()
                                , eventMetadata.getParamB()
                                , eventMetadata.getParamC()
                                , new Double(csvFields[i])));
                    } else if (eventMetadata.getEventType().equals(EventType.FIELD)) {
                        combinedEvent.addEvent(new FieldEvent(eventMetadata.getEventName()
                                , eventMetadata.getParamA()
                                , eventMetadata.getParamB()
                                , eventMetadata.getParamC()
                                , new Double(csvFields[i])));
                    }
                }
                return combinedEvent;
            }).collect(Collectors.toSet());
        } catch (Exception e) {
            throw new ParseException("failed to parse results", e);
        }
    }
}
