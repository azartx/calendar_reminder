package com.solo4.core.calendar.model;

enum class Millis(val millis: Long) {

    NONE(0),

    MINUTES_15(900000),
    MINUTES_1(60000),

    SECONDS_15(15000),

    HOUR_1(3600000);

    fun toMinutes(): Long {
        return this.millis / 60000
    }
}