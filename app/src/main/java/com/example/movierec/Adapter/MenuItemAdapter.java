package com.example.movierec.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movierec.ListItem.MenuItem;
import com.example.movierec.R;

import java.util.List;

/**
 * Created by August on 2018/5/7.
 */

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    private int resourceId;

    public MenuItemAdapter(Context context, int textViewResourceId, List<MenuItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View covertView, ViewGroup parent) {
        MenuItem menuItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView menuItem_title = (TextView) view.findViewById(R.id.menuitem_title);
        ImageView menuItem_image = (ImageView) view.findViewById(R.id.menuitem_image);
        ImageView menuItem_arrow = (ImageView) view.findViewById(R.id.menuitem_arrow);
        menuItem_title.setText(menuItem.getTitle());
        menuItem_image.setImageResource(menuItem.getImageID());
        menuItem_arrow.setImageResource(menuItem.getArrow());
        return view;
    }
}
