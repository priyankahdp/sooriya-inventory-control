package com.soa.sooriyamobile;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewInventory extends AppCompatActivity {
    private DBHelper mydb;

    TextView itemName;
    TextView qty;
    TextView unitPrice;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        itemName = (TextView) findViewById(R.id.editItem);
        qty = (TextView) findViewById(R.id.editQty);
        unitPrice = (TextView) findViewById(R.id.editUnitPrice);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String itemNameCursor = rs.getString(rs.getColumnIndex(DBHelper.INVENTORY_COLUMN_ITEM_NAME));
                String qtyCursor = rs.getString(rs.getColumnIndex(DBHelper.INVENTORY_COLUMN_QTY));
                String unitPriceCursor = rs.getString(rs.getColumnIndex(DBHelper.INVENTORY_COLUMN_UNIT_PRICE));

                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.btnSubmit);
                //b.setVisibility(View.INVISIBLE);
                b.setClickable(false);
                b.setBackgroundColor(Color.GRAY);
                b.setTextColor(Color.WHITE);

                itemName.setText((CharSequence) itemNameCursor);
                itemName.setFocusable(false);
                itemName.setClickable(false);

                qty.setText((CharSequence) qtyCursor);
                qty.setFocusable(false);
                qty.setClickable(false);

                unitPrice.setText((CharSequence) unitPriceCursor);
                unitPrice.setFocusable(false);
                unitPrice.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                getMenuInflater().inflate(R.menu.display_contact, menu);
            } else {
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button) findViewById(R.id.btnSubmit);
                b.setBackgroundColor(Color.BLUE);
                b.setTextColor(Color.WHITE);
                b.setClickable(true);

                itemName.setEnabled(true);
                itemName.setFocusableInTouchMode(true);
                itemName.setClickable(true);

                qty.setEnabled(true);
                qty.setFocusableInTouchMode(true);
                qty.setClickable(true);

                unitPrice.setEnabled(true);
                unitPrice.setFocusableInTouchMode(true);
                unitPrice.setClickable(true);

                return true;

            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteItem).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mydb.deleteItem(id_To_Update);
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

                AlertDialog d = builder.create();
                d.setTitle("Delete item ");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb.updateItem(id_To_Update, itemName.getText().toString(), Integer.parseInt(qty.getText().toString()), Double.parseDouble(unitPrice.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Item updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Item not updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mydb.insertItem(itemName.getText().toString(), Integer.parseInt(qty.getText().toString()), Double.parseDouble(unitPrice.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "Added to the inventory", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error. Not added to the inventory", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }

    public void reset(View view) {
        itemName.setText("");
        qty.setText("");
        unitPrice.setText("");
    }
}