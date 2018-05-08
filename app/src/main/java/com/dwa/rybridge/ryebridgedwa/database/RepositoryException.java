package com.dwa.rybridge.ryebridgedwa.database;

public class RepositoryException extends Exception {

    public RepositoryException(Throwable throwable) {
        super(throwable);
    }

    public RepositoryException(String detailMessage) {
        super(detailMessage);
    }

    public RepositoryException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}