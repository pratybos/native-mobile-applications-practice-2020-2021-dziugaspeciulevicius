package com.dziugaspeciulevicius.to_do;

public class Model {

    private String task, additionalInformation, id, date;

    public Model() {

    }

    public Model(String task, String additionalInformation, String id, String date) {
        this.task = task;
        this.additionalInformation = additionalInformation;
        this.id = id;
        this.date = date;
    }


    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
