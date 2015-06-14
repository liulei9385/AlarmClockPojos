package com.clock.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.clock.model.AlarmClockBean;

import java.util.List;

/**
 * USER: liulei
 * DATE: 2015/6/15
 * TIME: 1:21
 */
public class ClockAdapter extends IBaseRecyclerAdapter<ClockAdapter.ViewHoldrer, AlarmClockBean> {


    public ClockAdapter(Context context, List<AlarmClockBean> list) {
        super(context, list);
    }

    @Override
    protected ViewHoldrer onCreateViewHolder(ViewGroup parent, LayoutInflater layoutInflater,
                                             int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHoldrer holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    public class ViewHoldrer extends RecyclerView.ViewHolder {

        public ViewHoldrer(View itemView) {
            super(itemView);
        }
    }
}
