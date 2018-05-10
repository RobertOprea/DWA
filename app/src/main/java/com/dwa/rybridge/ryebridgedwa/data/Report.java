package com.dwa.rybridge.ryebridgedwa.data;

import com.google.firebase.database.Exclude;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "report")
public class Report {

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String COMPANY_COLUMN = "company";
    public static final String ACTION_COLUMN = "action";
    public static final String CATEGORY_COLUMN = "category";
    public static final String DATE_COLUMN = "date";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String PROJECT_COLUMN = "project";
    public static final String USER_DETAILS_COLUMN = "user_details";
    public static final String PHOTO_PATH_COLUMN = "photo_path";
    public static final String LONGITUDE_COLUMN = "longitude";
    public static final String LATITUDE_COLUMN = "latitude";

    @DatabaseField(generatedId = true, columnName = ID_COLUMN)
    public int id;
    @DatabaseField(columnName = NAME_COLUMN)
    public String name;
    @DatabaseField(columnName = COMPANY_COLUMN)
    public String company;
    @DatabaseField(columnName = ACTION_COLUMN)
    public String actionTaken;
    @DatabaseField(columnName = CATEGORY_COLUMN)
    public String category;
    @DatabaseField(columnName = DATE_COLUMN)
    public String dateTaken;
    @DatabaseField(columnName = DESCRIPTION_COLUMN)
    public String description;
    @DatabaseField(columnName = PROJECT_COLUMN)
    public String project;
    @DatabaseField(columnName = USER_DETAILS_COLUMN)
    public String userDetails;
    @DatabaseField(columnName = PHOTO_PATH_COLUMN)
    public String photoPath;
    @DatabaseField(columnName = LONGITUDE_COLUMN)
    public double longitude;
    @DatabaseField(columnName = LATITUDE_COLUMN)
    public double latitude;

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    @Exclude
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Exclude
    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
