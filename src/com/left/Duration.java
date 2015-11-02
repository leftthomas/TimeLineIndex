package com.left;

import java.util.List;

/**
 * Created by left on 15/11/2.
 * 最后返回的满足条件的实体类
 * 包括一个时间区间和一个user_id list
 */
public class Duration {

    //活动开始时间
    long start_time;

    //活动结束时间
    long end_time;

    //在此活动时间区间内的用户ID
    List<Long> user_ids;

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public List<Long> getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(List<Long> user_ids) {
        this.user_ids = user_ids;
    }
}
