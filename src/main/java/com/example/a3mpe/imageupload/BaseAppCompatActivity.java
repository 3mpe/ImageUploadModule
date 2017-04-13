package com.example.a3mpe.imageupload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.adjust.sdk.Adjust;

import java.util.ArrayList;
import java.util.List;

class BaseAppCompatActivity extends AppCompatActivity {

    public ProgressDialogFragment mProgress;
    private List<ProgressDialogFragment> dialogFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ActivityManager.getInstance().setCurrentActivity(this);
        mProgress = new ProgressDialogFragment();
        mProgress.setCancelable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Adjust.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Adjust.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeFromActivities(this);
    }

    public void ShowProgressBar() {
        try {
            getSupportFragmentManager().beginTransaction().remove(mProgress).commit();
            mProgress.showProgress(getSupportFragmentManager());
            dialogFragmentList.add(mProgress);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void HideProgressBar() {
        try {
            for (ProgressDialogFragment dialogFragment : dialogFragmentList) {
                dialogFragment.dissmissProgress();
            }

            mProgress = new ProgressDialogFragment();
            mProgress.setCancelable(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
