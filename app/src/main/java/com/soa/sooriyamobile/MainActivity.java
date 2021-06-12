package com.soa.sooriyamobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView obj;
    DBHelper mydb;
    TextView itemNameDialog;
    TextView qty;
    TextView totalPrice;
    ListViewAdapter lviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ArrayList array_list = mydb.getAllItems();
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);

        itemNameDialog = (TextView) dialogView.findViewById(R.id.editTxtItemDialog);
        qty = (TextView) dialogView.findViewById(R.id.editTxtQtyDialog);
        totalPrice = (TextView) dialogView.findViewById(R.id.editTxtTotalPriceDialog);

        mydb = new DBHelper(this);
        obj = (ListView) findViewById(R.id.listView1);

        ArrayList<SaleObj> array_list = mydb.getAllItemsForSales();
        lviewAdapter = new ListViewAdapter(this, array_list);
        obj.setAdapter(lviewAdapter);

        //obj = (ListView) findViewById(R.id.listView1);
        //obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                int id_To_Search = ((SaleObj) adapter.getItemAtPosition(position)).getId();
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);
                Intent intent = new Intent(getApplicationContext(), ViewInventory.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.item1:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(), ViewInventory.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }

    public void sales(View view) {
        Intent intent = new Intent(getApplicationContext(), SalesActivity.class);
        startActivity(intent);
    }
}