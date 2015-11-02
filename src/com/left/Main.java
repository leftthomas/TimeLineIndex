package com.left;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        //模拟100个user数据
        int size=100;
        List<User> users=new ArrayList<>();

        for (int i = 0; i <size; i++) {
            User user = new User();
            user.setId(i);
            user.setName("test" + i);
            long start_time = (long) (Math.random() * size);
            user.setStart_time(start_time);
            long end_time = start_time + (long) (Math.random() * size / 10);
            user.setEnd_time(end_time);
            users.add(user);
        }

//        User Ann=new User();
//        Ann.setId(1);
//        Ann.setName("Ann");
//        Ann.setStart_time(102);
//        Ann.setEnd_time(107);
//        users.add(Ann);
//        User Alice=new User();
//        Alice.setId(2);
//        Alice.setName("Alice");
//        Alice.setStart_time(103);
//        Alice.setEnd_time(106);
//        users.add(Alice);
//        User John=new User();
//        John.setId(3);
//        John.setName("John");
//        John.setStart_time(105);
//        John.setEnd_time(108);
//        users.add(John);
//        User Carl=new User();
//        Carl.setId(4);
//        Carl.setName("Carl");
//        Carl.setStart_time(103);
//        Carl.setEnd_time(109);
//        users.add(Carl);
//        User Ellen=new User();
//        Ellen.setId(5);
//        Ellen.setName("Ellen");
//        Ellen.setStart_time(105);
//        Ellen.setEnd_time(107);
//        users.add(Ellen);

//        System.out.println("Users：");
//        for (int i = 0; i < users.size(); i++) {
//            System.out.println("id:"+users.get(i).getId()+" name:"+ users.get(i).getName()+
//                    " duration:"+users.get(i).getStart_time()+"~"+users.get(i).getEnd_time());
//        }
//        System.out.println("--------------------");

        //测试timeline index
        TimeLineIndex timeLineIndex=new TimeLineIndex();
        //测试timeline index的构建
        long start=System.currentTimeMillis();
        timeLineIndex.constructionTimelineIndex(users);
        long end=System.currentTimeMillis();
        System.out.println("constructionTimelineIndex(size="+users.size()+") cost:"+(double)(end-start)/1000+"s");

        //测试计算最佳聚会时间
        start=System.currentTimeMillis();
        List<Duration> durations=timeLineIndex.calculateBestInterval(5);
        end=System.currentTimeMillis();
        System.out.println("calculateBestInterval(size="+users.size()+") cost:"+(double)(end-start)/1000+"s");
        System.out.println("------------");

//        for (int i = 0; i < durations.size(); i++) {
//            Duration duration=durations.get(i);
//            System.out.println("duration:"+duration.getStart_time()+"~"+duration.getEnd_time());
//            System.out.print("ids: ");
//            for (int j = 0; j < duration.getUser_ids().size(); j++) {
//                System.out.print(duration.getUser_ids().get(j) + " ");
//            }
//            System.out.println();
//            System.out.println("------------");
//        }

        //测试timeline index的Temporal Aggregation
        //timeLineIndex.count();

    }

}
