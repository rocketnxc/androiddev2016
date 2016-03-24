package vn.edu.usth.firstblood.FacebookClasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vutngson on 3/25/16.
 */
public class UserInfo {
    private String id;
    private String picture;
    private String about;
    private String age_range;
    private String birthday;
    private String cover;
    private String email;
    private String education;
    private String gender;
    private String hometown;
    private String name;

    public UserInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() throws JSONException {
        JSONObject jsonObjectPicture = new JSONObject(picture);
        String url = jsonObjectPicture.getJSONObject("data").getString("url");
        return url;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAge_range() throws JSONException {
        JSONObject jsonObjectAgeRange = new JSONObject(age_range);
        String info = jsonObjectAgeRange.getString("min");
        return info;
    }

    public void setAge_range(String age_range) {
        this.age_range = age_range;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCover() throws JSONException {
        JSONObject jsonObjectCover = new JSONObject(cover);
        String source = jsonObjectCover.getString("source");
        return source;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEducation() throws JSONException {
        JSONArray jsonArrayEducation = new JSONArray(education);
        String info = jsonArrayEducation.getJSONObject(0).getJSONObject("school").getString("name");
        return info;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHometown() throws JSONException {
        JSONObject jsonObjectHometown = new JSONObject(hometown);
        String info = jsonObjectHometown.getString("name");
        return info;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
