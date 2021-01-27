package xyz.fluxinc.MathsGame;

import xyz.fluxinc.MathsGame.calculations.Calculation;

import java.util.LinkedHashMap;
import java.util.Map;

public class Session {

    private final Map<Calculation, Double> answeredQuestions;
    private final String clientName;
    private final long timestamp;

    public Session(String clientName) {
        this.clientName = clientName;
        this.timestamp = System.currentTimeMillis();
        this.answeredQuestions = new LinkedHashMap<Calculation, Double>();
    }

    public Map<Calculation, Double> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public String getClientName() {
        return clientName;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
