package com.soa.sooriyamobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TakeOutActivity extends AppCompatActivity {
    DBHelper mydb;

    private EditText txtBundle;
    private EditText txtGross;
    private EditText txtDozen;
    private EditText txtUnits;

    Button btnPerform;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeout);

        mydb = new DBHelper(this);

        txtBundle = (EditText) findViewById(R.id.editTxtBundle);
        txtGross = (EditText) findViewById(R.id.editTxtGross);
        txtDozen = (EditText) findViewById(R.id.editTxtDozen);
        txtUnits = (EditText) findViewById(R.id.editTxtUnits);

        txtBundle.setFilters(new InputFilter[]{new InputFilterMinMax(0, 1)});
        txtGross.setFilters(new InputFilter[]{new InputFilterMinMax(0, 5)});
        txtDozen.setFilters(new InputFilter[]{new InputFilterMinMax(0, 12)});
        //txtUnits.setFilters(new InputFilter[]{new InputFilterMinMax(0, 12)});

        btnPerform = (Button) findViewById(R.id.btnTakeoutPerform);
        btnReset = (Button) findViewById(R.id.btnTakeoutReset);

        txtDozen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    Integer dozenCount = Integer.parseInt(txtDozen.getText().toString());
                    Integer unitCountQty = dozenCount * 12;
                    txtUnits.setText(unitCountQty.toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Qty couldn't be an empty ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });


        btnPerform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countMatched()) {
                    showAlert();
                } else {
                    Toast.makeText(getApplicationContext(), "Please double check takeout values ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmpty();
            }
        });


    }

    protected void showAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(TakeOutActivity.this);
        builder1.setMessage("Do you want to takeout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        Integer bundleCount = 0;
                        if (txtBundle.getText() != null && !txtBundle.getText().toString().equals("")){
                            bundleCount = Integer.parseInt(txtBundle.getText().toString());
                        }
                        Integer grossCount = 0;
                        if (txtGross.getText() != null && !txtGross.getText().toString().equals("")) {
                            grossCount = Integer.parseInt(txtGross.getText().toString());
                        }
                        Integer dozenCount = 0;
                        if (txtDozen.getText() != null && !txtDozen.getText().toString().equals("")) {
                            dozenCount = Integer.parseInt(txtDozen.getText().toString());
                        }
                        Integer unitCount = 0;
                        if (txtUnits.getText() != null && !txtUnits.getText().toString().equals("")) {
                            unitCount = Integer.parseInt(txtUnits.getText().toString());
                        }
                        mydb.performTakeout(bundleCount,grossCount,dozenCount,unitCount);
                        Intent intent = new Intent(getApplicationContext(), SalesActivity.class);
                        startActivity(intent);

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setDefault();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    protected boolean countMatched() {
        if ((txtBundle.getText() != null && !txtBundle.getText().toString().equals("")) &&
                (txtGross.getText() != null && !txtGross.getText().toString().equals("")) &&
                (txtDozen.getText() != null && !txtDozen.getText().toString().equals(""))) {

            return true;
        } else {
            return false;
        }

    }

    protected void setEmpty() {
        txtBundle.setText("");
        txtGross.setText("");
        txtDozen.setText("");
        txtUnits.setText("");
    }

    protected void setDefault() {
        txtBundle.setText("1");
        txtGross.setText("");
        txtDozen.setText("");
        txtUnits.setText("");
    }

    protected void setDisabled() {
        txtBundle.setEnabled(false);
        txtGross.setEnabled(false);
        txtDozen.setEnabled(false);
        txtUnits.setEnabled(false);
    }
}