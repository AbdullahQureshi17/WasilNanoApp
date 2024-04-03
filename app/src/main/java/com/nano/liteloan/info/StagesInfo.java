package com.nano.liteloan.info;

/**
 * Created by Muhammad Ahmad on 07/04/2020.
 */
public class StagesInfo {

    private String name;
    private String heading;
    private String title;
    private CharSequence titleChar;
    private String logo;
    private int id;

    public void setTitleChar(CharSequence titleChar) {
        this.titleChar = titleChar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StagesInfo(String name, String heading, String title, CharSequence titleChar, String logo, int id) {
        this.name = name;
        this.heading = heading;
        this.title = title;
        this.titleChar = titleChar;
        this.logo = logo;
        this.id = id;
    }

    public StagesInfo(String name, String heading, String title, String logo) {
        this.name = name;
        this.heading = heading;
        this.title = title;
        this.logo = logo;

    }

    public StagesInfo(String name, String heading, String title, String logo, CharSequence titleChar) {
        this.name = name;
        this.heading = heading;
        this.title = title;
        this.logo = logo;
        this.titleChar = titleChar;

    }

    public CharSequence getTitleChar() {
        return titleChar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StagesInfo() {

    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
