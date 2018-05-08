package com.dwa.rybridge.ryebridgedwa.database;

import com.dwa.rybridge.ryebridgedwa.data.Report;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

public class ReportDatabase implements ReportRepository {

    private Context context;

    public ReportDatabase(Context context) {
        this.context = context;
    }

    @Override
    public boolean insert(Report report) throws RepositoryException {
        try {
            Dao<Report, Integer> dao = getDao();
            dao.create(report);
            return true;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            OpenHelperManager.releaseHelper();
        }
    }

    @Override
    public boolean clear() throws RepositoryException {
        try {
            Dao<Report, Integer> dao = getDao();
            dao.delete(getAll());
            return true;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            OpenHelperManager.releaseHelper();
        }
    }

    @Override
    public List<Report> getAll() throws RepositoryException {
        try {
            Dao<Report, Integer> dao = getDao();
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            OpenHelperManager.releaseHelper();
        }
    }

    private Dao<Report, Integer> getDao() throws SQLException {
        DBHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
        return helper.getDao(Report.class);
    }
}
