package com.clock.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.clock.R;
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
    protected ViewHoldrer onCreateViewHolder(
            ViewGroup parent,
            LayoutInflater layoutInflater,
            int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_item_clock, parent, false);
        return new ViewHoldrer(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoldrer holder, int position) {
        super.onBindViewHolder(holder, position);
        AlarmClockBean clockBean = (AlarmClockBean) getItemAtPosition(position);
        holder.clockNameTxt.setText(clockBean.getName());
        holder.startTimeTxt.setText(clockBean.getStarttime() + " ");
        holder.endTimeTxt.setText(clockBean.getEndtime() + " ");
    }

    public class ViewHoldrer extends RecyclerView.ViewHolder {

        TextView clockNameTxt;
        TextView startTimeTxt;
        TextView endTimeTxt;

        public ViewHoldrer(View itemView) {
            super(itemView);
            clockNameTxt = (TextView) itemView.findViewById(R.id.nameText);
            startTimeTxt = (TextView) itemView.findViewById(R.id.startText);
            endTimeTxt = (TextView) itemView.findViewById(R.id.endText);
        }
    }
}
