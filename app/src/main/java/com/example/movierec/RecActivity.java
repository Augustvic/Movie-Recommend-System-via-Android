package com.example.movierec;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.movierec.Adapter.RecItemAdapter;
import com.example.movierec.Helper.DataBaseHelper;
import com.example.movierec.ListItem.RecItem;

import java.util.ArrayList;
import java.util.List;

public class RecActivity extends AppCompatActivity {

    private List<RecItem> MovieListMF = new ArrayList<>();
    private List<RecItem> MovieListBPR = new ArrayList<>();
    private String userID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);

        Intent recI = getIntent();
        Bundle recB = recI.getExtras();
        userID = recB.getString("userID");
        String algo = recB.getString("algo");

        TextView rec_text_head = (TextView) findViewById(R.id.rec_text_head);

        final ListView rec_list_bpr = (ListView) findViewById(R.id.rec_list_bpr);
        final ListView rec_list_mf = (ListView) findViewById(R.id.rec_list_mf);

        if(algo.equals("mf")) {
            rec_text_head.setText("CUNE_MF Recommend");
            initListMF();
            RecItemAdapter adapter = new RecItemAdapter(RecActivity.this,
                    R.layout.rec_item, MovieListMF);
            rec_list_mf.setAdapter(adapter);
        }
        else {
            rec_text_head.setText("CUNE_BPR Recommend");
            initListBPR();
            RecItemAdapter adapter = new RecItemAdapter(RecActivity.this,
                    R.layout.rec_item, MovieListBPR);
            rec_list_bpr.setAdapter(adapter);
        }

        rec_list_bpr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            RecItem movieg = (RecItem)rec_list_bpr.getItemAtPosition(i);
            String titleg = movieg.getTitle();
            Intent recI = new Intent(RecActivity.this, MovieInfoActivity.class);
            Bundle recB = new Bundle();
            recB.putString("title", titleg);
            recB.putString("userID", userID);
            String tag = "noRate";
            recB.putString("tag", tag);
            recI.putExtras(recB);
            startActivity(recI);
            }
        });

        rec_list_mf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecItem movieg = (RecItem)rec_list_mf.getItemAtPosition(i);
                String titleg = movieg.getTitle();
                Intent recI = new Intent(RecActivity.this, MovieInfoActivity.class);
                Bundle recB = new Bundle();
                recB.putString("title", titleg);
                recB.putString("userID", userID);
                String tag = "noRate";
                recB.putString("tag", tag);
                recI.putExtras(recB);
                startActivity(recI);
            }
        });

        Button rec_btn_back = (Button) findViewById(R.id.rec_btn_back);
        rec_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initListBPR() {
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch(SQLException e) {
            throw e;
        }

        String table =  "cunebpr" ;
        String[] columns = new  String[] {"itemID1", "itemID2", "itemID3", "itemID4", "itemID5",
                "itemID6","itemID7", "itemID8", "itemID9", "itemID10"};
        String selection = "userID=?" ;
        String[] selectionArgs = new  String[]{userID};
        String groupBy = null ;
        String having = null ;
        String orderBy = null ;
        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst()) {
            for(int i=1; i<=10; i++) {
                String itemIDi = "itemID" + Integer.toString(i);
                String itemIDs = cursor.getString(cursor.getColumnIndex(itemIDi));
                String titles = getTitleByID(itemIDs);
                int imageID = R.drawable.zoo;
                switch(titles) {
                    case "Godfather, The (1972)":
                        imageID = R.drawable.thegodfather;
                        break;
                    case "Schindler's List (1993)":
                        imageID = R.drawable.schindlerslist;
                        break;
                    case "Godfather: Part II, The (1974)":
                        imageID = R.drawable.thegodfatherpart2;
                        break;
                    case "Star Wars: Episode IV - A New Hope (1977)":
                        imageID = R.drawable.starwarsanewhope;
                        break;
                    case "Fight Club (1999)":
                        imageID = R.drawable.fightclub;
                        break;
                    case "Braveheart (1995)":
                        imageID = R.drawable.braveheart;
                        break;
                    case "Star Wars: Episode VI - Return of the Jedi (1983)":
                        imageID = R.drawable.starwarsreturnofthejedi;
                        break;
                    case "Alien (1979)":
                        imageID = R.drawable.alien;
                        break;
                    case "Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb (1964)":
                        imageID = R.drawable.drstrangelove;
                        break;
                    case "Taxi Driver (1976)":
                        imageID = R.drawable.taxidriver;
                        break;
                    default:
                        imageID = R.drawable.zoo;
                }
                if(i < 4) {
                    RecItem movie = new RecItem(imageID, titles, R.drawable.hot);
                    MovieListBPR.add(movie);
                }
                else {
                    RecItem movie = new RecItem(imageID, titles, R.drawable.rec);
                    MovieListBPR.add(movie);
                }
            }
        }
    }

    public void initListMF() {
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch(SQLException e) {
            throw e;
        }

        String table =  "cunemf" ;
        String[] columns = new  String[] {"itemID", "prediction"};
        String selection = "userID=?" ;
        String[] selectionArgs = new  String[]{userID};
        String groupBy = null ;
        String having = null ;
        String orderBy = null ;
        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst()) {
            do{
                String itemID = cursor.getString(cursor.getColumnIndex("itemID"));
                float prediction = cursor.getFloat(cursor.getColumnIndex("prediction"));
                String title = getTitleByID(itemID);

                int imageID = R.drawable.zoo;
                switch(title) {
                    case "L.A. Confidential (1997)":
                        imageID = R.drawable.laconfidential;
                        break;
                    case "Hitman (2007)":
                        imageID = R.drawable.hitman;
                        break;
                    case "Blade Runner (1982)":
                        imageID = R.drawable.bladerunner;
                        break;
                    case "Back to the Future Part II (1989)":
                        imageID = R.drawable.backtothefuturepart2;
                        break;
                    case "Shrek 2 (2004)":
                        imageID = R.drawable.shrek2;
                        break;
                    case "Sky Captain and the World of Tomorrow (2004)":
                        imageID = R.drawable.skycaptainandtheworldoftomorrow;
                        break;
                    case "Die Hard 2 (1990)":
                        imageID = R.drawable.diehard2;
                        break;
                    case "Kung Fu Panda (2008)":
                        imageID = R.drawable.kungfupanda;
                        break;
                    default:
                        imageID = R.drawable.zoo;
                }

                if(4 < prediction && prediction <= 5) {
                    RecItem movie = new RecItem(imageID, title, R.drawable.star3);
                    MovieListMF.add(movie);
                }
                else if(3 < prediction && prediction <= 4) {
                    RecItem movie = new RecItem(imageID, title, R.drawable.star2);
                    MovieListMF.add(movie);
                }
                else if(2 < prediction && prediction <= 3) {
                    RecItem movie = new RecItem(imageID, title,  R.drawable.star1);
                    MovieListMF.add(movie);
                }
            }while(cursor.moveToNext());
        }
    }

    public String getTitleByID(String itemID) {
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.openDataBase();
        } catch(SQLException e) {
            throw e;
        }
        String table =  "iteminfo" ;
        String[] columns = new  String[] {"title"};
        String selection = "itemID=?" ;
        String[] selectionArgs = new  String[]{itemID};
        String groupBy = null ;
        String having = null ;
        String orderBy = null ;
        Cursor cursor = myDbHelper.getCursor(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
        if (cursor.moveToFirst())
            return cursor.getString(cursor.getColumnIndex("title"));
        else
            return "Toy Story (1995)";
    }
}
