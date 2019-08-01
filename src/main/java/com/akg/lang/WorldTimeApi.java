package com.akg.lang;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldTimeApi {

    private Integer weekNumber;
    private String utcOffset;
    private String utcDatetime;
    private Long unixtime;
    private String timezone;
    private Integer rawOffset;
    private Integer dayOfYear;
    private Integer dayOfWeek;
    private Date datetime;
    private String clientIp;
    private String abbreviation;

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getUtcDatetime() {
        return utcDatetime;
    }

    public void setUtcDatetime(String utcDatetime) {
        this.utcDatetime = utcDatetime;
    }

    public Long getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(Long unixtime) {
        this.unixtime = unixtime;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getRawOffset() {
        return rawOffset;
    }

    public void setRawOffset(Integer rawOffset) {
        this.rawOffset = rawOffset;
    }

    public Integer getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(Integer dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorldTimeApi)) return false;
        WorldTimeApi that = (WorldTimeApi) o;
        return Objects.equals(getWeekNumber(), that.getWeekNumber()) &&
                Objects.equals(getUtcOffset(), that.getUtcOffset()) &&
                Objects.equals(getUtcDatetime(), that.getUtcDatetime()) &&
                Objects.equals(getUnixtime(), that.getUnixtime()) &&
                Objects.equals(getTimezone(), that.getTimezone()) &&
                Objects.equals(getRawOffset(), that.getRawOffset()) &&
                Objects.equals(getDayOfYear(), that.getDayOfYear()) &&
                Objects.equals(getDayOfWeek(), that.getDayOfWeek()) &&
                Objects.equals(getDatetime(), that.getDatetime()) &&
                Objects.equals(getClientIp(), that.getClientIp()) &&
                Objects.equals(getAbbreviation(), that.getAbbreviation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeekNumber(), getUtcOffset(), getUtcDatetime(), getUnixtime(), getTimezone(), getRawOffset(), getDayOfYear(), getDayOfWeek(), getDatetime(), getClientIp(), getAbbreviation());
    }
}
