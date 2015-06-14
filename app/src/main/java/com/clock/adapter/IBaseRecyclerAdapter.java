package com.clock.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nameforcast.pojo.util.ImageUtils;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * USER: liulei
 * DATE: 2015/3/20.
 * TIME: 16:33
 */
public abstract class IBaseRecyclerAdapter<VH extends RecyclerView.ViewHolder, T>
        extends RecyclerView.Adapter<VH> {
    protected Context context;
    protected List<T> objectList;
    private LayoutInflater layoutInflater;

    @SuppressWarnings("unchecked")
    public IBaseRecyclerAdapter(Context context, List<T> tList) {
        this.objectList = new ArrayList<T>();
        if (tList != null) {
            this.objectList.addAll(tList);
        }
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void addItemAll(List<T> tList) {
        if (this.objectList == null)
            this.objectList = new ArrayList<T>();
        if (tList == null) return;
        this.objectList.addAll(tList);
    }


    public void addItem(T t) {
        if (this.objectList == null)
            this.objectList = new ArrayList<T>();
        if (t == null) return;
        this.objectList.add(t);
    }

    @SuppressWarnings("unchecked")
    public void addItem(int index, T t) {
        if (this.objectList == null)
            this.objectList = new ArrayList<T>();
        if (t == null) return;
        ((List<T>) this.objectList).add(index, t);
    }

    @SuppressWarnings("unchecked")
    public void removeItem(int index) {
        if (this.objectList == null) {
            this.objectList = new ArrayList<T>();
            return;
        }
        ((List<T>) this.objectList).remove(index);
    }

    public Object getItemAtPosition(int postion) {
        return this.objectList.get(postion);
    }

    public void clear() {
        if (this.objectList != null && this.objectList.size() > 0)
            this.objectList.clear();
    }

    @Override
    public int getItemCount() {
        return objectList != null ? objectList.size() : 0;
    }

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(parent, layoutInflater, viewType);
    }

    protected abstract VH onCreateViewHolder(ViewGroup parent, LayoutInflater layoutInflater, int viewType);

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performItemClick(v, position);
            }
        });
    }

    protected void loadImageFromHttp(String httpUrl, ImageView imageView,
                                     Transformation transformation) {
        ImageUtils.loadImageFromHttp(context, httpUrl, imageView, transformation);
    }


    public interface OnItemClickListener {
        void onItemClick(View convertView, int position);
    }

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private void performItemClick(View convertView, int position) {
        if (itemClickListener != null)
            itemClickListener.onItemClick(convertView, position);
    }

}