package com.clock.act;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.clock.R;
import com.clock.adapter.ClockAdapter;
import com.clock.helper.DbCurdHelper;
import com.clock.model.AlarmClockBean;
import com.clock.sqlite.DatabaseHelper;
import com.clock.utils.Constant;
import com.clock.utils.DrawableUtils;
import com.clock.widget.ListDividerItemDecoration;

import java.util.List;

/**
 * USER: liulei
 * DATE: 2015/6/14
 * TIME: 16:19
 */
public class MainActivity extends BaseActivity {

    private DatabaseHelper databaseHelper;
    private DbCurdHelper curdHelper;
    private String tableName;
    private RecyclerView recyclerView;
    private ClockAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableName = Constant.TABLENAME;

        initView();
        initDB();
    }

    private void initView() {
        recyclerView = findView(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Drawable selectDrawable = DrawableUtils.getAttrDrawable(activity,
                android.R.attr.listDivider);
        RecyclerView.ItemDecoration decoration = new
                ListDividerItemDecoration(selectDrawable);
        recyclerView.addItemDecoration(decoration);
        adapter = new ClockAdapter(this, null);
        recyclerView.setAdapter(adapter);
    }

    private void initDB() {
        // insert a table
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        curdHelper = new DbCurdHelper(db);

        AlarmClockBean clock = new AlarmClockBean();
        clock.setName("leilei12");
        clock.setStarttime(System.currentTimeMillis());
        clock.setEndtime(System.currentTimeMillis() + 60 * 1000);
        curdHelper.insert(clock);

        List<AlarmClockBean> list = curdHelper.queryAll(AlarmClockBean.class);
        adapter.addItemAll(list);
        adapter.notifyDataSetChanged();
    }

}

