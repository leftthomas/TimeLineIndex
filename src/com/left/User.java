package com.left;

/**
 * Created by left on 15/10/28.
 * 用户实体类定义
 * start_time~end_time为空闲时长，也即有效时间
 * start_time是作为timeline index的索引的
 */
public class User {

    //用户ID，唯一标识
    long id;

    //用户姓名
    String name;

    //空闲开始时间
    long start_time;

    //空闲结束时间
    long end_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
