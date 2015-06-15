package com.clock.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * USER: liulei
 * DATE: 2015/6/15
 * TIME: 22:16
 */
public abstract class IBaseCursorAdapter<VH extends IBaseCursorAdapter.ViewHolder>
        extends CursorAdapter {

    private LayoutInflater layoutInflate;
    private Context context;

    public IBaseCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public IBaseCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.context = context;
        init();
    }

    private void init() {
        layoutInflate = LayoutInflater.from(context);
    }

    protected abstract VH onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup parent);

    protected abstract void onBindViewHolder(VH vh, Cursor c);

    @Override
    public final View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = onCreateViewHolder(layoutInflate, parent);
        viewHolder.itemView.setTag(viewHolder);
        return viewHolder.itemView;
    }

    @Override
    public final void bindView(View view, Context context, Cursor cursor) {
        VH vh = (VH) view.getTag();
        onBindViewHolder(vh, cursor);
    }

    public static class ViewHolder {

        private View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}

