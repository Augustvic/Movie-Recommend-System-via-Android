package com.example.movierec;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.movierec.Helper.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private String userID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent searchI = getIntent();
        Bundle searchB = searchI.getExtras();
        userID = searchB.getString("userID");

        Button search_button_search = (Button) findViewById(R.id.search_button_search);
        final EditText search_edit_search = (EditText) findViewById(R.id.search_edit_search);
        final ListView search_list_res = (ListView) findViewById(R.id.search_list_res);

        search_button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = search_edit_search.getText().toString();

                List<String> list_search = searchList(itemName);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        SearchActivity.this, android.R.layout.simple_list_item_1, list_search);

                search_list_res.setAdapter(adapter);
            }
        });

        search_list_res.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = (String)search_list_res.getItemAtPosition(i);
                Intent searchI = new Intent(SearchActivity.this, MovieInfoActivity.class);
                Bundle searchB = new Bundle();
                searchB.putString("title", title);
                searchB.putString("userID", userID);
                String itemID = getIDByTitle(title);
                String tag = "noRate";
                if(isRate(userID, itemID))
                    tag = "Rate";
                searchB.putString("tag", tag);
                searchI.putExtras(searchB);
                startActivity(searchI);
            }
        });

        Button search_btn_back = (Button) findViewById(R.id.search_btn_back);
        search_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public boolean isRate(String userID, String itemID) {
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch(SQLException e) {
            throw e;
        }
        String table =  "rated" ;
        String[] columns = new  String[] {"score"};
        String selection = "userID=? and itemID=?" ;
        String[] selectionArgs = new  String[]{userID, itemID};
        String groupBy = null ;
        String having = null ;
        String orderBy = null ;
        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst())
            return true;
        else
            return false;
    }

    public String getIDByTitle(String title) {
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch(SQLException e) {
            throw e;
        }
        String table =  "iteminfo" ;
        String[] columns = new  String[] {"itemID"};
        String selection = "title=?" ;
        String[] selectionArgs = new  String[]{title};
        String groupBy = null ;
        String having = null ;
        String orderBy = null ;
        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst())
            return cursor.getString(cursor.getColumnIndex("itemID"));
        else
            return "2";
    }

    public List<String> searchList(String itemName) {
        List<String> res = new ArrayList<>();
        String titles = "%" + itemName + "%";

        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch(SQLException e) {
            throw e;
        }

        String table =  "iteminfo" ;
        String[] columns = new String[] {"title"};
        String selection = "title like ?" ;
        String[] selectionArgs = new String[]{titles};
        String groupBy = null ;
        String having = null ;
        String orderBy = null ;

        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                res.add(title);
            } while (cursor.moveToNext());
        }
        return res;
    }
}
