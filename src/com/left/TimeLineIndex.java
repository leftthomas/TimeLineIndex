package com.left;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.*;

/**
 * Created by left on 15/10/30.
 */
public class TimeLineIndex {

    //记录所有version信息
    BiMap<Long,Integer> versionmap= new HashBiMap<>();

    //记录所有event信息
    Event[] events=null;

    /**
     * 构建timeline index
     */
    public void constructionTimelineIndex(List<User> users){

        //记录到某个具体version时共发生的event数
        Integer count=0;

        //存储中间数据
        TreeMap<Long,Integer> intermediate=new TreeMap<>();

        //第一次扫描所有数据时构建中间表，记录所有某个版本时发生的事件数
        for (int i = 0; i < users.size(); i++) {
            User user=users.get(i);
            //先判断下要插入的数据是否已经有了
            if(intermediate.containsKey(user.getStart_time())){
                //有了就更新下这个地方的count值，即＋1
                intermediate.replace(user.getStart_time(),
                        intermediate.get(user.getStart_time())+1);
            }else{
                //没有的话就插入一条数据,count为1
                intermediate.put(user.getStart_time(),1);
            }

            //同理，对end time一样操作
            if(intermediate.containsKey(user.getEnd_time())){
                intermediate.replace(user.getEnd_time(),
                        intermediate.get(user.getEnd_time())+1);
            }else{
                intermediate.put(user.getEnd_time(),1);
            }
        }
//        System.out.println(intermediate);

        //获取hashMap的键值，并进行遍历
        Iterator ir=intermediate.keySet().iterator();
        //构建versionmap
        while(ir.hasNext()){
            Long key= (Long)ir.next();
            count+=intermediate.get(key);
            versionmap.put(key,count);
        }
//        System.out.println("version map数据：");
//        for (Map.Entry entry : versionmap.entrySet()) {
//            System.out.println("version= " + entry.getKey() + " and event_id= " + entry.getValue());
//        }
//        System.out.println("--------------------");

        //第二次扫描所有数据,构建list<event>，构建完成的表
        //先按照version排序，对于同一version，按照user_id排序
        //这时候可以确定events的大小了
        //给events分配下空间
        events=new Event[count];

        for (int i = 0; i <users.size(); i++) {
            User user = users.get(i);
            Event start_event=new Event();
            start_event.setUser_id(user.getId());
            start_event.setStatus(true);
            //记录插入的位置
            Integer position=versionmap.get(user.getStart_time()) -
                    //中间表的作用体现出来了，这里记住，一定要用啊，不然没法定位位置，
                    //想了好久才想到利用中间表来确定插入的位置
                    intermediate.get(user.getStart_time());
            while (events[position]!=null){
                position++;
            }
            //找到空位了
            events[position]=start_event;

            //同理，对end time一样操作，只是要将status置为false
            Event end_event=new Event();
            end_event.setUser_id(user.getId());
            end_event.setStatus(false);
            //记录插入的位置
            position=versionmap.get(user.getEnd_time())  -
                    intermediate.get(user.getEnd_time());
            while (events[position]!=null){
                position++;
            }
            //找到空位了
            events[position]=end_event;
        }
//        System.out.println("event list数据：");
//        for (int i = 0; i <events.length; i++) {
//            System.out.println("user_id:"+events[i].getUser_id()+";status:"
//                    +events[i].isStatus());
//        }
//        System.out.println("--------------------");

    }

    /**
     * 统计到每个version时status处于1的user数量
     * @return 一个按照version排好序的map
     */
    public Map<Long,Integer> count(){

        Map<Long,Integer> counts=new HashMap<>();
        Integer count=0;
        Integer position=0;
        for (Map.Entry entry : versionmap.entrySet()) {
            //记录到这个version时总event数
            Integer num=(Integer)entry.getValue();
            while (position<num){
                if(events[position].isStatus()){
                    count++;
                }else{
                    count--;
                }
                position++;
            }
            counts.put((Long) entry.getKey(), count);
        }
        return counts;
    }

    /**
     * 计算出满足聚会要求的结果
     * @param interval 聚会要求的最短时长
     * @return 满足条件的duration列表
     */
    public List<Duration> calculateBestInterval(long interval){

        List<Duration> durations=new ArrayList<>();

        for (Map.Entry entry : versionmap.entrySet()) {

            Duration duration=new Duration();
            List<Long> user_ids=new ArrayList<>();
            duration.setStart_time((long)entry.getKey());
            //一开始将结束时间置为近乎无穷大，方便后续更正操作
            //Long.MAX_VALUE = 9223372036854775807
            duration.setEnd_time(Long.MAX_VALUE);
            //找到这个版本时一共有多少事件
            Integer num=(Integer)entry.getValue();
            Integer position=0;

            while (position<num){
                Event event=events[position];
                //只需要找激活点，失效点肯定不在满足条件的duration里面，所以不需要做进一步的操作
                if(event.isStatus()){
                    for (int i = 0; i < events.length; i++) {
                        //找到这时候的event失效的时间
                        if(events[i].getUser_id()==event.getUser_id() && events[i].isStatus()==false){
                            //用来记录失效时间
                            long version;
                            //用来标记是否在versionmap中找到了对应的version
                            boolean status=false;
                            while (!status){
                                //记得＋1，因为event list里面是从0开始算起的
                                if(versionmap.containsValue(i+1)){
                                    status=true;
                                    //反转一下versionmap根据value查找key
                                    version=versionmap.inverse().get(i+1);
                                    //满足聚会最短时间条件，添加到duration的list里面去
                                    if(version-duration.getStart_time()>=interval){
                                        //如果新的满足条件的user的时长比这时候已经具有的结束时间短一些
                                        if(version<duration.getEnd_time()){
                                            //需要修正一下结束时间，确保都满足
                                            duration.setEnd_time(version);
                                        }
                                        user_ids.add(event.getUser_id());
                                    }
                                }else{
                                    //继续往下找，因为有可能这个event不是刚好对应的
                                    i++;
                                }
                            }
                            //可以跳出循环了，不用再往下找了，因为一个event只有可能有两次出现
                            break;
                        }
                    }
                }
                position++;
            }
            //做下优化，如果没有满足的user，就不要加入最后的list<duration>了
            if(user_ids.size()!=0) {
                //千万不要忘了将user_ids赋给duration
                duration.setUser_ids(user_ids);
                //不要忘了将duration加入durations
                durations.add(duration);
            }
        }
        return durations;
    }
}
