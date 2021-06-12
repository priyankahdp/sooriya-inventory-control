package com.soa.sooriyamobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesActivity extends AppCompatActivity {
    private ListView obj;
    ListViewAdapter lviewAdapter;
    TextView itemNameDialog;
    TextView qty;
    TextView totalPrice;
    DBHelper mydb;
    RelativeLayout parent_layout;
    private Button btnTakeout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        parent_layout = new RelativeLayout(getApplicationContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        //View dialogView = inflater.inflate(R.layout.dialog_layout,  null);
        View dialogView = inflater.inflate(R.layout.dialog_layout, parent_layout, false);
        //View view = inflater.inflate(R.layout.child_layout_to_merge, parent_layout, false);

        itemNameDialog = (TextView) dialogView.findViewById(R.id.editTxtItemDialog);
        qty = (TextView) dialogView.findViewById(R.id.editTxtQtyDialog);
        totalPrice = (TextView) dialogView.findViewById(R.id.editTxtTotalPriceDialog);

        mydb = new DBHelper(this);
        obj = (ListView) findViewById(R.id.listView2);

        ArrayList<SaleObj> array_list = mydb.getAllItemsForSales();
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list);
        lviewAdapter = new ListViewAdapter(this, array_list);
        obj.setAdapter(lviewAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                if (dialogView.getParent() != null) {
                    ((ViewGroup) dialogView.getParent()).removeView(dialogView);
                }
                builder.setView(dialogView).setTitle("Sales & Order").setMessage("Type quantity and do the transaction");
                Integer recordId = ((SaleObj) adapter.getItemAtPosition(position)).getId();
                itemNameDialog.setText(((SaleObj) adapter.getItemAtPosition(position)).getItemName());
                int existingQty = ((SaleObj) adapter.getItemAtPosition(position)).getQty();
                qty.setText("0");

                Double itemsQty = Double.parseDouble(qty.getText().toString());
                Double itemUnitPrice = ((SaleObj) adapter.getItemAtPosition(position)).getUnitPrice();
                Double result = itemsQty * itemUnitPrice;
                totalPrice.setText(result.toString());


                qty.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() > 0) {
                            Double itemsQty = Double.parseDouble(qty.getText().toString());
                            Double itemUnitPrice = ((SaleObj) adapter.getItemAtPosition(position)).getUnitPrice();
                            Double result = itemsQty * itemUnitPrice;
                            totalPrice.setText(result.toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Qty couldn't be an empty ", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });


                builder.setPositiveButton("Cancel Order", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }

                }).setNegativeButton("Make Order", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int requestedQtyInt = Integer.parseInt(qty.getText().toString());
                                if (requestedQtyInt > existingQty) {
                                    Toast.makeText(getApplicationContext(), "Order Qty exceeds stock ", Toast.LENGTH_SHORT).show();
                                } else {
                                    mydb.deductInventory(existingQty - requestedQtyInt, recordId);
                                    lviewAdapter.updateData(mydb.getAllItemsForSales());

                                }

                                //
                                //Intent intent = new Intent(getApplicationContext(), SalesActivity.class);
                                //startActivity(intent);
                            }
                        }
                );

                //builder.create();
                builder.show();

            }
        });


        btnTakeout=findViewById(R.id.btnTakeoutBundle);
        btnTakeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TakeOutActivity.class);
                startActivity(intent);
            }
        });

    }

    public void report(View view) {
        File file = new File(SalesActivity.this.getFilesDir(), "report");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File reportFile = new File(file, "dailyReport");
            FileWriter writer = new FileWriter(reportFile);
            writer.append("Balance Stock\n");
            writer.append("Date : "+new Date()+"\n");
            writer.append("Name : Dilum\n");
            writer.append("Area : \n");
            List<SaleObj> listForReport= mydb.getAllItemsForSales();
            for(int i=0;i<listForReport.size();i++){
                writer.append(listForReport.get(i).getItemName()+" : "+listForReport.get(i).getQty()+"\n");
            }
            writer.flush();
            writer.close();
            Toast.makeText(SalesActivity.this, "Daily sales report is ready.!", Toast.LENGTH_LONG).show();
        } catch (Exception e) { }

    }
}