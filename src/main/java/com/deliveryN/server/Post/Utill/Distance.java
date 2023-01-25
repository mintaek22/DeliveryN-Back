package com.deliveryN.server.Post.Utill;


import com.deliveryN.server.Post.Entitiy.Post;

import java.util.Comparator;

public class Distance implements Comparator<Post> {

    //사용자 위치
    private final double x;
    private final double y;

    public Distance(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double num1 = Math.abs(lat1 - lat2)*0.8;
        double num2 = Math.abs(lon1-lon2);
        return (num1+num2)*1000000;
    }

    @Override
    public int compare(Post post1, Post post2) {

        double num1 = getDistance(x,y,post1.getX(),post1.getY());
        double num2 = getDistance(x,y,post2.getX(),post2.getY());

        double result = num1 - num2;

        return (int)result;
    }
}

