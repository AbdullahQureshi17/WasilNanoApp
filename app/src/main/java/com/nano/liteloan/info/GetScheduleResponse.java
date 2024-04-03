package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad
 */
public class GetScheduleResponse {

    @SerializedName("user")
    public User user;

    @SerializedName("Schedule")
    public List<Schedule> schedule = null;

    @SerializedName("outstanding")
    public OutStanding outstanding;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}
