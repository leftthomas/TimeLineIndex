package com.left;

import jdk.nashorn.internal.runtime.BitVector;

/**
 * Created by left on 15/10/28.
 * 检查点实体类定义
 * 其记录某个时间点，即某个版本时所有user此时的status，
 * 方便后续做temporal aggregations而服务
 */
public class Checkpoint {

    /**
     * 与Version的ID相对应
     */
    long version_id;

    /**
     * 记录某个version在map<version>中的位置
     * 之所以要记录这个，是因为出于从查询速度上来考虑
     * 费一点空间换取更快的查询速度
     */
    int version_position;

    /**
     * 记录当前时间点所有user的status
     * 为了节省空间使用了BitVector，每一位记录对应ID的user的status
     */
    BitVector visible_user;

    public long getVersion_id() {
        return version_id;
    }

    public void setVersion_id(long version_id) {
        this.version_id = version_id;
    }

    public int getVersion_position() {
        return version_position;
    }

    public void setVersion_position(int version_position) {
        this.version_position = version_position;
    }

    public BitVector getVisible_user() {
        return visible_user;
    }

    public void setVisible_user(BitVector visible_user) {
        this.visible_user = visible_user;
    }
}
