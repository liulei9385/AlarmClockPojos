package com.clock.act;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.clock.R;
import com.clock.helper.DbCurdHelper;
import com.clock.model.AlarmClockBean;
import com.clock.sqlite.DatabaseHelper;
import com.clock.utils.Constant;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableName = Constant.TABLENAME;

        initView();
        initDB();
    }

    private void initView() {

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
        System.out.println("MainActivity.initDB# \t" + list.size());
        for (AlarmClockBean bean : list) {
            System.out.println("MainActivity.initDB# \t" + bean.getName() + "\t" + bean.getStarttime() + "\t");
        }
    }

}

