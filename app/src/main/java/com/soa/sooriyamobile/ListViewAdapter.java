package com.soa.sooriyamobile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    Activity context;
    ArrayList<SaleObj> saleObjArrayList;

    public ListViewAdapter(Activity context, ArrayList<SaleObj> saleObjArrayList) {
        super();
        this.context = context;
        this.saleObjArrayList = saleObjArrayList;
    }

    public int getCount() {
        return saleObjArrayList.size();
    }

    public void updateData(ArrayList<SaleObj> saleObjArrayList) {
        this.saleObjArrayList = saleObjArrayList;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return saleObjArrayList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtViewItemId;
        TextView txtViewItemName;
        TextView txtViewItemQty;
        TextView unitPrice;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_row, null);
            holder = new ViewHolder();
            holder.txtViewItemId = (TextView) convertView.findViewById(R.id.listItemIdTxt);
            holder.txtViewItemName = (TextView) convertView.findViewById(R.id.listItemNameTxt);
            holder.txtViewItemQty = (TextView) convertView.findViewById(R.id.listItemQtyTxt);
            holder.unitPrice = (TextView) convertView.findViewById(R.id.listItemUnitPriceTxt);
            TextView unitPrice;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewItemId.setText(String.valueOf(saleObjArrayList.get(position).getId()));
        holder.txtViewItemName.setText(saleObjArrayList.get(position).getItemName());
        holder.txtViewItemQty.setText(String.valueOf(saleObjArrayList.get(position).getQty()));
        holder.unitPrice.setText(String.valueOf(saleObjArrayList.get(position).getUnitPrice()));

        return convertView;
    }

}