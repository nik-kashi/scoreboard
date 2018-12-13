package com.kashi.scoreboard;

import com.kashi.scoreboard.service.ScoreboardService;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScoreboardApp {
    private static final Logger LOGGER = Logger.getLogger(ScoreboardApp.class.getName());

    static final ScoreboardService scoreboardService = new ScoreboardService();

    public static void main(String[] args) {

        LOGGER.log(Level.INFO, "started with input: {0}", Arrays.toString(args));

        final String inputFileAddress;

        if (args.length < 1) {
            inputFileAddress = ScoreboardApp.class.getResource("/results.csv").getFile();
            LOGGER.log(Level.INFO, "missing input file address. so default one used: {0}", inputFileAddress);
        } else {
            inputFileAddress = args[0];
            LOGGER.log(Level.INFO, "input file address: {0}", inputFileAddress);
        }

        LOGGER.log(Level.INFO, "output is ready:{0}",
                scoreboardService.calculateScores(inputFileAddress)
        );
    }


}
