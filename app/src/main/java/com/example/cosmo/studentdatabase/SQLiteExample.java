package com.example.cosmo.studentdatabase;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class SQLiteExample extends AppCompatActivity implements OnClickListener {
    Button btnUpdate, btnView, btnGetInfo, btnEdit, btnDelete;
    EditText txtName, txtProgram, txtRowId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_example);
        txtName = (EditText)findViewById(R.id.txtName);
        txtProgram = (EditText)findViewById(R.id.txtProgram);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnView = (Button)findViewById(R.id.btnView);
        btnUpdate.setOnClickListener(this);
        btnView.setOnClickListener(this);
        txtRowId = (EditText)findViewById(R.id.txtRowId);
        btnGetInfo = (Button)findViewById(R.id.btnGetInfo);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        btnGetInfo.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    public void onClick(View arg0){
        switch (arg0.getId()) {
            case R.id.btnUpdate:
                boolean worked = true;
                if(txtName.getText().length()>0 && txtProgram.getText().length()>0){
                    try {
                        String name = txtName.getText().toString();
                        String program = txtProgram.getText().toString();
                        StudentInfo entry = new StudentInfo(SQLiteExample.this);
                        entry.open();
                        entry.createEntry(name, program);
                        entry.close();
                    } catch (Exception e) {
                        worked = false;
                        String error = e.toString();
                        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
                    } finally {
                        if(worked){
                            txtName.setText("");
                            txtProgram.setText("");
                            Dialog d = new Dialog(this);
                            d.setTitle("Record has been Updated!.");
                            TextView tv = new TextView(this);
                            tv.setText("Record has been Updated!");
                            d.setContentView(tv);
                            d.show();
                        }
                    }
                }else{
                    Toast.makeText(this,"Field(s) must not be empty",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnView:
                startActivity(new Intent(SQLiteExample.this, SQLView.class));
                break;
            case R.id.btnGetInfo:
                worked = true;
                if(txtRowId.getText().length()>0){
                    try{
                        String s = txtRowId.getText().toString();
                        Long l = Long.parseLong(s);
                        StudentInfo si = new StudentInfo(this);
                        si.open();
                        String returnedName = si.getName(l);
                        String returnedProgram = si.getProgram(l);
                        si.close();
                        txtName.setText(returnedName);
                        txtProgram.setText(returnedProgram);
                    } catch (Exception e){
                        worked = false;
                        String error = e.toString();
                        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
                    } finally {
                        if(worked){
                            btnEdit.setEnabled(true);
                            btnDelete.setEnabled(true);
                        }
                    }
                }else{
                    Toast.makeText(this,"Row ID must not be empty",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnEdit:
                worked = true;
                if(txtRowId.getText().length()>0 && txtName.getText().length()>0 && txtProgram.getText().length()>0){
                    try{
                        String sname = txtName.getText().toString();
                        String sprogram = txtProgram.getText().toString();
                        String srow = txtRowId.getText().toString();
                        Long lrow = Long.parseLong(srow);

                        StudentInfo ed = new StudentInfo(this);
                        ed.open();
                        ed.updateEntry(lrow, sname, sprogram);
                        ed.close();
                    } catch (Exception e){
                        worked = false;
                        String error = e.toString();
                        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
                    }finally {
                        if (worked) {
                            Dialog d = new Dialog(this);
                            d.setTitle("Record has been edited.");
                            TextView tv = new TextView(this);
                            tv.setText("Edited!");
                            d.setContentView(tv);
                            d.show();
                            txtRowId.setText("");
                            btnEdit.setEnabled(false);
                            txtName.setText("");
                            txtProgram.setText("");
                        }
                    }
                }else{
                    Toast.makeText(this,"Row ID must not be empty",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnDelete:
                worked = true;
                if(txtRowId.getText().length()>0){
                    try{
                        String drow = txtRowId.getText().toString();
                        Long delrow = Long.parseLong(drow);

                        StudentInfo dr = new StudentInfo(this);
                        dr.open();
                        dr.deleteEntry(delrow);
                        dr.close();
                    } catch (Exception e){
                        worked = false;
                        String error = e.toString();
                        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
                    } finally {
                        if(worked){
                            Dialog d = new Dialog(this);
                            d.setTitle("Record has been removed from the database.");
                            TextView tv = new TextView(this);
                            tv.setText("Deleted!");
                            d.setContentView(tv);
                            d.show();
                            txtRowId.setText("");
                            txtProgram.setText("");
                            txtName.setText("");
                            btnEdit.setEnabled(false);
                            btnDelete.setEnabled(false);
                        }
                    }
                }else{
                    Toast.makeText(this,"Row ID must not be empty",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}