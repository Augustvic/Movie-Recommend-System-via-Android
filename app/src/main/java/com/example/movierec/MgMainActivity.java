package com.example.movierec;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.movierec.Adapter.MenuItemAdapter;
import com.example.movierec.ListItem.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MgMainActivity extends AppCompatActivity {

    private Toolbar tb_bar;
    private DrawerLayout mgmain_layout;
    private List<MenuItem> listMenu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mg_main);

        initView();

        //frame menu
        ListView mgmain_frame_list_menu = (ListView) findViewById(R.id.mgmain_frame_list_menu);
        initMenu();
        MenuItemAdapter adapter = new MenuItemAdapter(getApplicationContext(), R.layout.menu_item, listMenu);
        mgmain_frame_list_menu.setAdapter(adapter);
        mgmain_frame_list_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (l == 0) {   //watched list
                    if (mgmain_layout.isDrawerOpen(Gravity.LEFT)) //如果此时抽屉窗口打开，就给他关闭
                        mgmain_layout.closeDrawer(Gravity.LEFT);
                } else if (l == 1) {
                    Intent mainI1 = new Intent(MgMainActivity.this, MgMFActivity.class);
                    startActivity(mainI1);
                } else if (l == 2) {
                    Intent mainI2 = new Intent(MgMainActivity.this, MgBPRActivity.class);
                    startActivity(mainI2);
                }
            }
        });

        Button mgmain_frame_button_out = (Button) findViewById(R.id.mgmain_frame_button_out);
        mgmain_frame_button_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initMenu() {
        com.example.movierec.ListItem.MenuItem menuItem0 = new com.example.movierec.ListItem.MenuItem("Dataset Information", R.drawable.dataset, R.drawable.arrow);
        listMenu.add(menuItem0);
        com.example.movierec.ListItem.MenuItem menuItem1 = new com.example.movierec.ListItem.MenuItem("CUNE_MF Algorithm", R.drawable.cune_mf, R.drawable.arrow);
        listMenu.add(menuItem1);
        com.example.movierec.ListItem.MenuItem menuItem2 = new com.example.movierec.ListItem.MenuItem("CUNE-BPR Recommend", R.drawable.cune_bpr, R.drawable.arrow);
        listMenu.add(menuItem2);
    }

    public void initView() {
        tb_bar = (Toolbar) findViewById(R.id.tb_bar);
        mgmain_layout = (DrawerLayout) findViewById(R.id.mgmain_layout);
        tb_bar.setNavigationIcon(R.mipmap.menu);
        tb_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果抽屉关闭就打开，如果抽屉打开就关闭
                if (mgmain_layout.isDrawerOpen(Gravity.LEFT)) {
                    mgmain_layout.closeDrawer(Gravity.LEFT);
                } else {//如果已经是关闭状态
                    mgmain_layout.openDrawer(Gravity.LEFT);
                }
            }
        });
    }
}
