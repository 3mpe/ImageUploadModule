package com.example.a3mpe.imageupload;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityManager {
    public static ActivityManager instance;
    private Activity currentActivity;
    private ArrayList<Activity> runningActivities = new ArrayList<>();

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity) {
        this.currentActivity = activity;
        addToRunningActivities(activity);
    }

    private void addToRunningActivities(Activity activity) {
        runningActivities.add(activity);
    }

    public void removeFromActivities(Activity activity) {
        if (runningActivities.contains(activity)) {
            runningActivities.remove(activity);
        }
    }
}
