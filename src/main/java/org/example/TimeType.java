package org.example;

public enum TimeType {
    WH("Regular");

    String timeType;

    TimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getValue() {
        return this.timeType;
    }
}
