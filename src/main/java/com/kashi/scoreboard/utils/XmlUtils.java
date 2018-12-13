package com.kashi.scoreboard.utils;

import com.kashi.scoreboard.models.EventMetadata;
import com.kashi.scoreboard.models.EventType;
import com.kashi.scoreboard.models.RankingDto;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlUtils {
    private static final Logger LOGGER = Logger.getLogger(XmlUtils.class.getName());
    public static final String METADATA_FIELD_EVENT = "event";
    public static final String METADATA_FIELD_NAME = "name";
    public static final String METADATA_FIELD_PARAM_A = "param-a";
    public static final String METADATA_FIELD_PARAM_B = "param-b";
    public static final String METADATA_FIELD_PARAM_C = "param-c";
    public static final String METADATA_FIELD_TYPE = "type";

    public static List<EventMetadata> parseMetadata(final String xmlFileAddress) {
        List<EventMetadata> metadataList = new ArrayList<>();
        try {
            InputStream resource = XmlUtils.class.getClassLoader().getResourceAsStream(xmlFileAddress);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(resource);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName(METADATA_FIELD_EVENT);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    metadataList.add(new EventMetadata(
                            eElement.getAttribute(METADATA_FIELD_NAME),
                            new Double(eElement.getElementsByTagName(METADATA_FIELD_PARAM_A).item(0).getTextContent()),
                            new Double(eElement.getElementsByTagName(METADATA_FIELD_PARAM_B).item(0).getTextContent()),
                            new Double(eElement.getElementsByTagName(METADATA_FIELD_PARAM_C).item(0).getTextContent()),
                            EventType.valueOf(eElement.getElementsByTagName(METADATA_FIELD_TYPE).item(0).getTextContent())
                    ));
                }
            }
            return metadataList;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "fail to parse metadata", e);
            throw new ParseException("fail to parse metadata", e);
        }
    }

    public static String generateResponse(List<RankingDto> ranking) {
        String rankingFilePath = System.getProperty("java.io.tmpdir").concat(File.separator).concat("ranking-").concat(UUID.randomUUID().toString()).concat(".xml");

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("competitors");
            document.appendChild(root);

            ranking.forEach(rankingDto -> {

                Element competitorElement = document.createElement("competitor");
                root.appendChild(competitorElement);

                Element competitorNameElement = document.createElement("competitor-name");
                competitorNameElement.appendChild(document.createTextNode(rankingDto.getCompetitorName()));
                competitorElement.appendChild(competitorNameElement);

                Element competitorRankElement = document.createElement("rank");
                competitorRankElement.appendChild(document.createTextNode(rankingDto.getRank()));
                competitorElement.appendChild(competitorRankElement);

                Element scoreElement = document.createElement("score");
                scoreElement.appendChild(document.createTextNode(String.valueOf(rankingDto.getScore())));
                competitorElement.appendChild(scoreElement);

                Element eventsElement = document.createElement("events");
                competitorElement.appendChild(eventsElement);

                rankingDto.getEvents().forEach(event -> {
                    Element eventElement = document.createElement("event");
                    eventsElement.appendChild(eventElement);

                    Element eventNameElement = document.createElement("event-name");
                    eventNameElement.appendChild(document.createTextNode(event.getEventName()));
                    eventElement.appendChild(eventNameElement);

                    Element performanceElement = document.createElement("performance");
                    performanceElement.appendChild(document.createTextNode(String.valueOf(event.getPerformance())));
                    eventElement.appendChild(performanceElement);

                });


            });
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(rankingFilePath));

            transformer.transform(domSource, streamResult);

            return rankingFilePath;

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "ranking file generation failed");
            throw new XmlGenerationException("failed to generate xml file", e);
        }

    }
}
