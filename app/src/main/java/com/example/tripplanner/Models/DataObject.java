package com.example.tripplanner.Models;

import java.io.Serializable;

public class DataObject implements Serializable {

    String status, description, guid, link, publishDate, title, reference, road, region, county, latitude, longitude, overallStart, overallEnd, eventStart, eventEnd;

    public DataObject() {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", guid='" + guid + '\'' +
                ", link='" + link + '\'' +
                ", pubDate='" + publishDate + '\'' +
                ", title='" + title + '\'' +
                ", reference='" + reference + '\'' +
                ", road='" + road + '\'' +
                ", region='" + region + '\'' +
                ", county='" + county + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", overallStart='" + overallStart + '\'' +
                ", overallEnd='" + overallEnd + '\'' +
                ", eventStart='" + eventStart + '\'' +
                ", eventEnd='" + eventEnd + '\'' +
                '}';
    }
}
