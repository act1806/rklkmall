/**
 * Copyright 2018 bejson.com
 */
package org.linlinjava.litemall.core.deppon.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Auto-generated: 2018-07-19 22:27:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TraceList {

    @JsonProperty("city")
    private String city;
    @JsonProperty("description")
    private String description;
    @JsonProperty("site")
    private String site;
    @JsonProperty("time")
    private String time;
    @JsonProperty("status")
    private String status;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}