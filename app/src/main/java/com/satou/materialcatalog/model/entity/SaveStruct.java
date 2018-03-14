package com.satou.materialcatalog.model.entity;

import java.util.List;

/**
 * Created by Mitsuki on 2018/3/10.
 */

public class SaveStruct {
    private String name;
    private String id;
    private String episode;
    private String season;
    private String time;
    private List<String> type;
    private List<String> scene;
    private String getTime;

    public void reset(){
        name = "";
        id = "";
        episode = "";
        season = "";
        time = "";
        type.clear();
        scene.clear();
        getTime = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getScene() {
        return scene;
    }

    public void setScene(List<String> scene) {
        this.scene = scene;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }
}
