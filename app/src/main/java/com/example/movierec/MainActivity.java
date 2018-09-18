package com.example.movierec;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.movierec.Adapter.MenuItemAdapter;
import com.example.movierec.Adapter.NormalItemAdapter;
import com.example.movierec.Helper.DataBaseHelper;
import com.example.movierec.ListItem.NormalItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String userID = "1";
    private NormalItem selectedTitle;
    private List<com.example.movierec.ListItem.MenuItem> listMenu = new ArrayList<>();
    private List<NormalItem> list_watched = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        Intent mainI = getIntent();
        Bundle mainB = mainI.getExtras();
        userID = mainB.getString("userOnline");

        TextView main_text_account = (TextView) findViewById(R.id.main_text_account);
        TextView main_text_count = (TextView) findViewById(R.id.main_text_count);
        TextView main_text_rated = (TextView) findViewById(R.id.main_text_rated);
        TextView main_frame_text_watched = (TextView) findViewById(R.id.main_frame_text_watched);
        TextView main_frame_text_rated = (TextView) findViewById(R.id.main_frame_text_rated);

        String text_account = userID;
//      main_text_account.setText(text_account);
        String text_count = watchedCount(userID);
        main_text_count.setText(text_count);
        main_text_rated.setText(text_count);
        main_frame_text_watched.setText(text_count);
        main_frame_text_rated.setText(text_count);


        //watched list
        initWatchedList(userID);

        NormalItemAdapter adapter = new NormalItemAdapter(
                MainActivity.this, R.layout.normal_item, list_watched);
        final ListView main_list_watched = (ListView) findViewById(R.id.main_list_watched);
        main_list_watched.setAdapter(adapter);
        main_list_watched.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NormalItem item = (NormalItem) main_list_watched.getItemAtPosition(i);
                String title = item.getTitle();
                Intent mainI = new Intent(MainActivity.this, MovieInfoActivity.class);
                Bundle mainB = new Bundle();
                mainB.putString("title", title);
                mainB.putString("userID", userID);
                String tag = "Rate";
                mainB.putString("tag", tag);
                mainI.putExtras(mainB);
                startActivity(mainI);
            }
        });

        //frame menu
        ListView main_frame_list_menu = (ListView) findViewById(R.id.main_frame_list_menu);
        initMenu();
        MenuItemAdapter adapter2 = new MenuItemAdapter(getApplicationContext(), R.layout.menu_item, listMenu);
        main_frame_list_menu.setAdapter(adapter2);
        main_frame_list_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (l == 0) {   //watched list
                    if (dl_layout.isDrawerOpen(Gravity.LEFT)) //如果此时抽屉窗口打开，就给他关闭
                        dl_layout.closeDrawer(Gravity.LEFT);
                } else if (l == 1) {    //search movie
                    Intent mainI1 = new Intent(MainActivity.this, SearchActivity.class);
                    Bundle mainB1 = new Bundle();
                    mainB1.putString("userID", userID);
                    mainI1.putExtras(mainB1);
                    startActivity(mainI1);
                } else if (l == 2) {      //cune-mf recommend
                    Intent mainI2 = new Intent(MainActivity.this, RecActivity.class);
                    Bundle mainB2 = new Bundle();
                    mainB2.putString("userID", userID);
                    mainB2.putString("algo", "mf");
                    mainI2.putExtras(mainB2);
                    startActivity(mainI2);
                } else {       //cune-bpr recommend
                    Intent mainI3 = new Intent(MainActivity.this, RecActivity.class);
                    Bundle mainB3 = new Bundle();
                    mainB3.putString("userID", userID);
                    mainB3.putString("algo", "bpr");
                    mainI3.putExtras(mainB3);
                    startActivity(mainI3);
                }
            }
        });

        main_list_watched.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View
                    view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;
                selectedTitle = (NormalItem) main_list_watched.getItemAtPosition(info.position);
                contextMenu.setHeaderIcon(R.drawable.web);
                contextMenu.setHeaderTitle("More information on the Web?");
                contextMenu.add(0, 1, 1, "OK");
                contextMenu.add(0, 2, 1, "Cancel");
            }
        });

        Button main_frame_button_out = (Button) findViewById(R.id.main_frame_button_out);
        main_frame_button_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initMenu() {
        com.example.movierec.ListItem.MenuItem menuItem0 = new com.example.movierec.ListItem.MenuItem("Watched List", R.drawable.watched_list, R.drawable.arrow);
        listMenu.add(menuItem0);
        com.example.movierec.ListItem.MenuItem menuItem1 = new com.example.movierec.ListItem.MenuItem("Search Movie", R.drawable.search_movie, R.drawable.arrow);
        listMenu.add(menuItem1);
        com.example.movierec.ListItem.MenuItem menuItem2 = new com.example.movierec.ListItem.MenuItem("CUNE-MF Recommend", R.drawable.cune_mf, R.drawable.arrow);
        listMenu.add(menuItem2);
        com.example.movierec.ListItem.MenuItem menuItem3 = new com.example.movierec.ListItem.MenuItem("CUNE-BPR Recommend", R.drawable.cune_bpr, R.drawable.arrow);
        listMenu.add(menuItem3);
    }

    private Toolbar tb_bar;
//    private TextView tv_content;
//    private ListView lv;
    private DrawerLayout dl_layout;
//    private String[] menus = new String[]{"item1", "item2", "item3", "item4", "item5", "item6"};

    public void initView() {
        tb_bar = (Toolbar) findViewById(R.id.tb_bar);
//        lv= (ListView) findViewById(R.id.lv_menu);
        dl_layout = (DrawerLayout) findViewById(R.id.dl_layout);
//        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,menus);
//        lv.setAdapter(adapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(dl_layout.isDrawerOpen(Gravity.LEFT)){//如果此时抽屉窗口打开，就给他关闭
//                    dl_layout.closeDrawer(Gravity.LEFT);
//                }
//            }
//        });
        tb_bar.setNavigationIcon(R.mipmap.menu);
        tb_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果抽屉关闭就打开，如果抽屉打开就关闭
                if (dl_layout.isDrawerOpen(Gravity.LEFT)) {
                    dl_layout.closeDrawer(Gravity.LEFT);
                } else {//如果已经是关闭状态
                    dl_layout.openDrawer(Gravity.LEFT);
                }
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:   //OK
            {
                String title = selectedTitle.getTitle();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://www.baidu.com/s?wd=" + title);
                intent.setData(content_url);
                startActivity(intent);
            }
            case 2:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //四个参数的含义。1，group的id,2,item的id,3,是否排序，4，将要显示的内容
        menu.add(0, 1, 0, "Recommend");
        menu.add(0, 2, 0, "Search");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent mainI1 = new Intent(MainActivity.this, RecActivity.class);
                Bundle mainB1 = new Bundle();
                mainB1.putString("userID", userID);
                mainI1.putExtras(mainB1);
                startActivity(mainI1);
                break;
            case 2:
                Intent mainI2 = new Intent(MainActivity.this, SearchActivity.class);
                Bundle mainB2 = new Bundle();
                mainB2.putString("userID", userID);
                mainI2.putExtras(mainB2);
                startActivity(mainI2);
                break;
        }
        return true;
    }

    public String watchedCount(String userID) {
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch (SQLException e) {
            throw e;
        }

        String table = "rated";
        String[] columns = new String[]{"count(itemID)"};
        String selection = "userID=?";
        String[] selectionArgs = new String[]{userID};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex("count(itemID)"));
        } else {
            return Integer.toString(0);
        }
    }

    public void initWatchedList(String userID) {

        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch (SQLException e) {
            throw e;
        }

        String table = "rated";
        String[] columns = new String[]{"itemID", "score"};
        String selection = "userID=?";
        String[] selectionArgs = new String[]{userID};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);

        if (cursor.moveToFirst()) {
            do {
                String itemID = cursor.getString(cursor.getColumnIndex("itemID"));

                String table2 = "iteminfo";
                String[] columns2 = new String[]{"title"};
                String selection2 = "itemID=?";
                String[] selectionArgs2 = new String[]{itemID};
                String groupBy2 = null;
                String having2 = null;
                String orderBy2 = null;

                Cursor cursor2 = myDbHelper.getCursor(table2, columns2, selection2, selectionArgs2,
                        groupBy2, having2, orderBy2);

                if (cursor2.moveToFirst()) {
                    String title = cursor2.getString(cursor2.getColumnIndex("title"));

                    switch(title) {
                        case "Toy Story (1995)":
                            list_watched.add(new NormalItem(R.drawable.toystory, title, R.drawable.arrow));
                            break;
                        case "Beauty and the Beast (1991)":
                            list_watched.add(new NormalItem(R.drawable.beautyandthebeast, title, R.drawable.arrow));
                            break;
                        case "Cinderella (1950)":
                            list_watched.add(new NormalItem(R.drawable.cinderella, title, R.drawable.arrow));
                            break;
                        case "Jurassic Park (1993)":
                            list_watched.add(new NormalItem(R.drawable.jurassicpark, title, R.drawable.arrow));
                            break;
                        case "Titanic (1997)":
                            list_watched.add(new NormalItem(R.drawable.titanic, title, R.drawable.arrow));
                            break;
                        case "Witness for the Prosecution (1957)":
                            list_watched.add(new NormalItem(R.drawable.witness, title, R.drawable.arrow));
                            break;
                        case "12 Angry Men (1957)":
                            list_watched.add(new NormalItem(R.drawable.angrymen, title, R.drawable.arrow));
                            break;
                        case "Tom and Huck (1995)":
                            list_watched.add(new NormalItem(R.drawable.tomandhuck, title, R.drawable.arrow));
                            break;
                        default:
                            list_watched.add(new NormalItem(R.drawable.zoo, title, R.drawable.arrow));
                    }
                }
            } while (cursor.moveToNext());
        }
    }
}
