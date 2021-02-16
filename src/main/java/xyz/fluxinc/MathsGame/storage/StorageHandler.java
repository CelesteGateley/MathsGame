package xyz.fluxinc.MathsGame.storage;

import xyz.fluxinc.MathsGame.Session;
import xyz.fluxinc.MathsGame.errors.StorageFailedException;

public abstract class StorageHandler {

    public abstract void saveSession(Session session) throws StorageFailedException;
}
