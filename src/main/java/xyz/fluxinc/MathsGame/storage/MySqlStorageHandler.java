package xyz.fluxinc.MathsGame.storage;

import xyz.fluxinc.MathsGame.Session;
import xyz.fluxinc.MathsGame.calculations.Calculation;
import xyz.fluxinc.MathsGame.errors.StorageFailedException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MySqlStorageHandler extends StorageHandler {

    private final String address;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private static final String scoreSaveTable = "CREATE TABLE IF NOT EXISTS scores (id int PRIMARY KEY NOT NULL AUTO_INCREMENT, user int, score int, max_score int, date varchar(255));";
    private static final String calculationLogTable = "CREATE TABLE IF NOT EXISTS calculation (id int PRIMARY KEY NOT NULL AUTO_INCREMENT, user int, calculation varchar(255), expected_score decimal, actual_score decimal, date varchar(255));";
    private static final String userTable = "CREATE TABLE IF NOT EXISTS users (id int PRIMARY KEY NOT NULL AUTO_INCREMENT, username varchar(255), student varchar(255));";

    private static final String preparedCalculationInsertion = "INSERT INTO calculations VALUES (?, ?, ?, ?, ?)";
    private static final String preparedScoreInsertion = "INSERT INTO scores VALUES (?, ?, ?, ?);";
    private static final String preparedUserInsertion = "INSERT INTO users VALUES (?, ?);";

    private final String username;
    private final String password;

    public MySqlStorageHandler(String address, String port, String username, String password) throws StorageFailedException {
        this.address = "jdbc:mysql://" + address + ":" + port;
        this.username = username;
        this.password = password;
        setupDatabase();
    }

    private void setupDatabase() throws StorageFailedException {
        try {
            executeRawStatement(userTable);
            executeRawStatement(scoreSaveTable);
            executeRawStatement(calculationLogTable);
        } catch (SQLException | ClassNotFoundException e) {
            throw new StorageFailedException(e);
        }
    }

    @Override
    public void saveSession(Session session) throws StorageFailedException {
        int score = 0;
        int answered = 0;
        for (Calculation calculation : session.getAnsweredQuestions().keySet()) {
            answered++;
            double givenAnswer = session.getAnsweredQuestions().get(calculation);
            if (givenAnswer == calculation.getResult()) score++;
            saveCalculation(session.getClientId(), calculation, givenAnswer);
        }
        saveScore(session.getClientId(), score, answered);
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(address, username, password);
    }

    private void executeRawStatement(String statement) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        stmt.execute(statement);
        connection.close();
    }

    private void saveCalculation(int userId, Calculation calculation, double answer) throws StorageFailedException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(preparedCalculationInsertion);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, calculation.toString());
            preparedStatement.setDouble(3, calculation.getResult());
            preparedStatement.setDouble(4, answer);
            preparedStatement.setString(5, dateFormat.format(new Date()));
            preparedStatement.execute();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new StorageFailedException(e);
        }
    }

    private void saveScore(int userId, int score, int maxScore) throws StorageFailedException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(preparedScoreInsertion);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, score);
            preparedStatement.setInt(3, maxScore);
            preparedStatement.setString(4, dateFormat.format(new Date()));
            preparedStatement.execute();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new StorageFailedException(e);
        }
    }

    private void saveUser(String username, String realName) throws StorageFailedException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(preparedUserInsertion);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, realName);
            preparedStatement.execute();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new StorageFailedException(e);
        }
    }
}
