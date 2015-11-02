package com.left;

/**
 * Created by left on 15/10/28.
 * 事件实体类定义
 * 其个体将组成一个按照时间排序下来的list
 */
public class Event {

    /**
     * 与User的ID相对应
     */
    long user_id;

    /**
     * 用来标识一个user在某一时间点的状态，
     * 即用来标识某个时刻具有此ID的user是否已经失效了
     * start_time刚到时status被赋予true，生效
     * end_time超过时status被赋予false，失效
     */
    boolean status;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
