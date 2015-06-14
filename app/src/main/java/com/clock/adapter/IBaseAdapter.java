package com.clock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.clock.utils.ImageUtils;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * USER: liulei
 * DATA: 2015/1/31
 * TIME: 14:01
 */
@SuppressWarnings("unchecked")
public abstract class IBaseAdapter<VH extends IBaseAdapter.ViewHolder, T> extends BaseAdapter {

    private AdapterView adapterView;
    protected List<T> objectList;
    protected Context context;
    private LayoutInflater layoutInflater;

    public IBaseAdapter(Context context, List<T> tList) {
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
        this.objectList.addAll(tList);
    }

    public void addItem(T t) {
        if (this.objectList == null)
            this.objectList = new ArrayList<T>();
        this.objectList.add(t);
    }

    public void addItem(int index, T t) {
        if (this.objectList == null)
            this.objectList = new ArrayList<T>();
        this.objectList.add(index, t);
    }

    public void removeItem(int index) {
        if (this.objectList == null) {
            this.objectList = new ArrayList<T>();
            return;
        }
        this.objectList.remove(index);
    }

    /**
     * add item in first index
     *
     * @param t
     * @param <T>
     */
    public void addItemFirst(T t) {
        if (this.objectList == null)
            this.objectList = new ArrayList<T>();
        this.objectList.add(0, t);
    }

    public void clear() {
        if (this.objectList != null && this.objectList.size() > 0)
            this.objectList.clear();
    }

    @Override
    public int getCount() {
        return this.objectList != null ? objectList.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return this.objectList != null ? objectList.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        VH vh;
        if (convertView == null) {
            vh = onCreateViewHolder(layoutInflater, parent);
            convertView = vh.getItemView();
            convertView.setTag(vh);
        } else vh = (VH) convertView.getTag();
        onBindViewHolder(position, vh, parent);
        return convertView;
    }

    public List<T> getObjectList() {
        return objectList;
    }

    protected abstract VH onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup parent);

    protected abstract void onBindViewHolder(int position, VH vh, ViewGroup parent);

    protected void setOnClickListener(View.OnClickListener clickListener, View... views) {
        for (View view : views)
            view.setOnClickListener(clickListener);
    }

    protected void setOnLongClickListener(View.OnLongClickListener longClickListener, View... views) {
        for (View view : views)
            view.setOnLongClickListener(longClickListener);
    }

    protected void loadImageFromHttp(String httpUrl, ImageView imageView,
                                     Transformation transformation) {
        ImageUtils.loadImageFromHttp(context, httpUrl, imageView, transformation);
    }

    public static class ViewHolder {
        private View itemView;

        public View getItemView() {
            return itemView;
        }

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }

    public void setAdapterView(AdapterView adapterView) {
        this.adapterView = adapterView;
    }

    /**
     * 更新指定的index的view
     *
     * @param index
     */
    public void updateView(int index) {
        if (adapterView == null) return;
        int visiblePos = adapterView.getFirstVisiblePosition();
        int offset = index - visiblePos;
        if (offset < 0) return;

        View view = adapterView.getChildAt(offset);

        VH vh = (VH) view.getTag();
        onBindViewHolder(offset, vh, adapterView);
    }
}
