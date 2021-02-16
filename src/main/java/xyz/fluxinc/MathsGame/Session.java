package xyz.fluxinc.MathsGame;

import xyz.fluxinc.MathsGame.calculations.Calculation;

import java.util.LinkedHashMap;
import java.util.Map;

public class Session {

    private final Map<Calculation, Double> answeredQuestions;
    private final String clientName;
    private final int clientId;
    private final long timestamp;

    public Session(String clientName, int clientId) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.timestamp = System.currentTimeMillis();
        this.answeredQuestions = new LinkedHashMap<>();
    }

    public Map<Calculation, Double> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void addAnsweredQuestion(Calculation calculation, double answer) {
        answeredQuestions.put(calculation, answer);
    }

    public String getClientName() {
        return clientName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getClientId() {
        return clientId;
    }
}
