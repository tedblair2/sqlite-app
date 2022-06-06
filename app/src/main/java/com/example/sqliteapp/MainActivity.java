package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText fname,sname,marks,id;
    Button btn_add,btn_view,btn_update,btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DatabaseHelper(this);
        fname=findViewById(R.id.fname);
        sname=findViewById(R.id.sname);
        id=findViewById(R.id.id);
        marks=findViewById(R.id.marks);
        btn_add=findViewById(R.id.add);
        btn_view=findViewById(R.id.view);
        btn_update=findViewById(R.id.update);
        btn_delete=findViewById(R.id.delete);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted=db.insertData(fname.getText().toString(),sname.getText().toString(),marks
                        .getText().toString());
                if (isInserted ==true){
                    fname.setText("");
                    sname.setText("");
                    marks.setText("");
                    Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor=db.getAllData();
                if (cursor.getCount() == 0){
                    showMessage("Error","No data is found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while (cursor.moveToNext()){
                    buffer.append("Id: "+cursor.getString(0)+"\n");
                    buffer.append("Name: "+cursor.getString(1)+"\n");
                    buffer.append("Surname: "+cursor.getString(2)+"\n");
                    buffer.append("Marks: "+cursor.getString(3)+"\n");
                    buffer.append("\n");
                }
                showMessage("Data",buffer.toString());
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated=db.updateData(id.getText().toString(),fname.getText().toString(),sname.getText().toString()
                        ,marks.getText().toString());
                if (isUpdated == true){
                    id.setText("");
                    fname.setText("");
                    sname.setText("");
                    marks.setText("");
                    Toast.makeText(MainActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows=db.deleteData(id.getText().toString());
                if (deletedRows > 0){
                    id.setText("");
                    Toast.makeText(MainActivity.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void showMessage(String title,String Message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}