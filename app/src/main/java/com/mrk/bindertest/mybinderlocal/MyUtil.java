package com.mrk.bindertest.mybinderlocal;

/**
 * Created by mariko on 15/11/22.
 */
public class MyUtil {
    public static String getProessInfo() {
        String msg = "";
        msg = "pid=" + Integer.toString(android.os.Process.myPid());
        msg += ",tid=" + Integer.toString(android.os.Process.myTid());
        msg += ",uid=" + Integer.toString(android.os.Process.myUid());
        return msg;
    }
}
