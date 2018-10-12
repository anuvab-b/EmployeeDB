package anuvab_biswas.employeedb;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog.Builder;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    DatabaseHelper myDb;
    EditText e1,e2,e3,e4;
    Button addData,view,update,delete;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);
        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);
        e3=(EditText)findViewById(R.id.editText3);
        e4=(EditText)findViewById(R.id.editText4);
        addData=(Button)findViewById(R.id.button_add);
        view=(Button)findViewById(R.id.button_view);
        update=(Button)findViewById(R.id.btnupdate);
        delete=(Button)findViewById(R.id.btndelete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void DeleteData()
    {
        delete.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v)
                    {
                        Integer deletedRows=myDb.deleteData(e4.getText().toString());
                        if(deletedRows>0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data Not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void UpdateData()
    {
        update.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isUpdated=myDb.updateData(e4.getText().toString(),e1.getText().toString()
                                ,e2.getText().toString(),e3.getText().toString());
                        if(isUpdated==true)
                        {
                            Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Data Not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void AddData()
    {
        addData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted=myDb.insertData(e1.getText().toString(),e2.getText().toString(),e3.getText().toString());
                        if(isInserted==true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data Not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll()
    {
        view.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                       Cursor res= myDb.getAllData();
                        if(res.getCount()==0) {
                            //Show message

                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext())
                        {
                            buffer.append("Id: "+res.getString(0)+"\n");
                            buffer.append("Name: "+res.getString(1)+"\n");
                            buffer.append("Surname: "+res.getString(2)+"\n");
                            buffer.append("Marks: "+res.getString(3)+"\n\n");
                        }
                        //Show all the data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String Message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);//arg is context
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}



