package com.example.demoeditimage.utils;

import com.example.demoeditimage.model.param.UserParam;

public class MyConst {
//    public UserParam userParam = new UserParam();

    public static final String HOST_IP = "172.104.47.79";
    //    public static final String HOST_IP = "10.0.2.2";
    public static final String HOST_PORT = "8181";
    public static final long partner_id = 842940;

    public static long userid;


    public static String jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYWl0b2tpZDE2MTJAZ21haWwuY29tIiwiZXhwIjoxNTY3MTMxMzU1LCJpYXQiOjE1NjY1MjY1NTUsImp0aSI6IjYyIn0.XHQ5B1GvDwRlFLytalyI9WqMCFoXEtYxZWPb4UJY7xwetgRuQVC8ksbBqyXRDyG6s2UVV4K34Mx2BcEBDgfDMw";

    public static String getHostAddr() {
        return "http://" + HOST_IP + ":" + HOST_PORT + "/api/v1/";
    }

    public static String getJwtToken() {
        return jwtToken;
    }

    public static void setJwtToken(String token) {
        jwtToken = token;
    }

    public static long getUserid() {
        return userid;
    }

    public static void setUserid(long uid) {
        userid = uid;
    }
}


