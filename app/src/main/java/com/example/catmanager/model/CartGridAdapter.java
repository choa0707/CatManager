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

public class CartGridAdapter extends BaseAdapter {

    private Context context;
    private List<GridData> list;


    public CartGridAdapter(Context context, List<GridData> list) {

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
        int num;
        String num_s;
        num = list.get(position).num;

        View gridView = null;

        if (convertView == null) {

            gridView = new View(context);
            gridView = inflater.inflate( R.layout.cartitem , null);

        } else {
            gridView = (View) convertView;
        }


        if (num!=0)
        {
            TextView nameView = (TextView) gridView.findViewById(R.id.grid_name);
            TextView numView = (TextView) gridView.findViewById(R.id.grid_num);
            TextView priceView = (TextView) gridView.findViewById(R.id.grid_price);
            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_image);


            num_s = "수량 : "+num;
            numView.setText(num_s);
            Log.d("numtest", Integer.toString(num));
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
        }

        return gridView;
    }
}
