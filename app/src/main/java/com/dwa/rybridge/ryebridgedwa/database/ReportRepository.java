package com.dwa.rybridge.ryebridgedwa.database;

import com.dwa.rybridge.ryebridgedwa.data.Report;

import java.util.List;

public interface ReportRepository {

    boolean insert(Report report) throws RepositoryException;

    boolean clear() throws RepositoryException;

    List<Report> getAll() throws RepositoryException;
}
