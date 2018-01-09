package ru.roma.vk.wall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ilan on 02.01.2018.
 */

public class Reposts {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("user_reposted")
    @Expose
    private Integer userReposted;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getUserReposted() {
        return userReposted;
    }

    public void setUserReposted(Integer userReposted) {
        this.userReposted = userReposted;
    }

    @Override
    public String toString() {
        return "Reposts{" +
                "count=" + count +
                ", userReposted=" + userReposted +
                '}';
    }
}
