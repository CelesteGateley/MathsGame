package xyz.fluxinc.MathsGame.errors;

public class StorageFailedException extends Exception {
    public StorageFailedException(String s) {
        super(s);
    }

    public StorageFailedException(Exception e) {
        super(e);
    }
}
