package com.example.catmanager.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catmanager.R;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CustomGridAdapter extends BaseAdapter {

    private Context context;
    private List<GridData> list;


    public CustomGridAdapter(Context context, List<GridData> list) {

        this.context        = context;
        this.list     = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView = null;

        if (convertView == null) {

            gridView = new View(context);
            gridView = inflater.inflate( R.layout.griditem , null);

        } else {
            gridView = (View) convertView;
        }

        TextView nameView = (TextView) gridView.findViewById(R.id.grid_name);
        TextView monthView = (TextView) gridView.findViewById(R.id.grid_month);
        TextView priceView = (TextView) gridView.findViewById(R.id.grid_price);
        ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_image);

        int month_i;
        month_i = list.get(position).month;
        if (month_i == 0)
        {
            monthView.setText("전연령");
        }
        else
        {
            String month_s = month_i/100+"개월 ~ "+month_i%100+"개월";
            monthView.setText(month_s);
        }
        nameView.setText(list.get(position).name);
        int price = list.get(position).price;
        String symbol = Currency.getInstance(Locale.KOREA).getSymbol();
        String price_s = String.format("%,d", price);
        priceView.setText(symbol+price_s);

        int imageName = list.get(position).image;

        String test= "/sdcard/Download/";
        String path  = test+Integer.toString(imageName)+".PNG";
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Log.d("imagetest", path);
        imageView.setImageBitmap(bitmap);
        return gridView;
    }
}
