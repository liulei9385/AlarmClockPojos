package com.clock.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * USER: liulei
 * DATE: 2015/5/17
 * TIME: 16:12
 */
public class BaseActivity extends AppCompatActivity {

    protected FragmentManager fragmentManager;
    protected AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        activity = this;
    }

    public Intent setUpIntent(Class<? extends BaseActivity> clazz) {
        return new Intent(this, clazz).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    public Intent setUpIntent(@NonNull String action) {
        return new Intent(action).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    public void setOnClickListener(View.OnClickListener clickListener,
                                   View... views) {
        for (View view : views) {
            if (view == null) continue;
            view.setOnClickListener(clickListener);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(@IdRes int resId) {
        return (T) this.findViewById(resId);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(View rootView, @IdRes int resId) {
        return (T) rootView.findViewById(resId);
    }

}
