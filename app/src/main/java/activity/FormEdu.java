package activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.vishwashrisairm.materialdesign.R;

import java.text.Normalizer;
import java.util.List;

import adapter.EduRecyclerViewAdapter;
import adapter.HomeRecyclerViewAdapter;
import database.EduInfo;
import database.ItemStatus;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 22/3/16.
 */
public class FormEdu extends AppCompatActivity {
    private RecyclerView eduRecyclerView;
    private RecyclerView.Adapter eduAdapter;
    private RecyclerView.LayoutManager eduLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_edu);

        eduRecyclerView=(RecyclerView)findViewById(R.id.eduRecyclerView);
        eduRecyclerView.setHasFixedSize(true);
        eduLayoutManager=new LinearLayoutManager(this);
        eduRecyclerView.setLayoutManager(eduLayoutManager);
        eduAdapter=new EduRecyclerViewAdapter(getDataSet());
        eduRecyclerView.setAdapter(eduAdapter);

//        Floating action button
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_edu);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormEdu.this);
                builder.setTitle("Education Information");

                // Set up the input
                final EditText edu_name = new EditText(FormEdu.this);
                final EditText  yop= new EditText(FormEdu.this);
                final EditText cgpa = new EditText(FormEdu.this);
                final EditText institute = new EditText(FormEdu.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                edu_name.setInputType(InputType.TYPE_CLASS_TEXT);
                edu_name.setHint("Examination");
                yop.setInputType(InputType.TYPE_CLASS_TEXT);
                yop.setHint("Year of Passing");
                cgpa.setInputType(InputType.TYPE_CLASS_TEXT);
                cgpa.setHint("CGPA/%age");
                institute.setInputType(InputType.TYPE_CLASS_TEXT);
                institute.setHint("Institute");

                LinearLayout ll=new LinearLayout(FormEdu.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(edu_name);
                ll.addView(yop);
                ll.addView(cgpa);
                ll.addView(institute);


                builder.setView(ll);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String edu = edu_name.getText().toString();
                        String year = yop.getText().toString();
                        String cg = cgpa.getText().toString();
                        String ins = institute.getText().toString();


                        PInfoDbHandler db = new PInfoDbHandler(FormEdu.this,"",null,1);
                        EduInfo e=new EduInfo(1, edu, year, cg, ins);
                        db.addEInfo(e);
                        Intent i = new Intent(FormEdu.this,FormEdu.class);
                        startActivity(i);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((EduRecyclerViewAdapter) eduAdapter).setOnItemClickListener(new EduRecyclerViewAdapter.MyClickListener(){

            @Override
            public void onItemClick(int position, View v) {
                Log.i("edu", " Clicked on Item " + position);
            }
        });

    }

    private List<EduInfo> getDataSet() {
        PInfoDbHandler db = new PInfoDbHandler(this,"",null,1);
        List<EduInfo> i = db.getAllEInfo();
        for (EduInfo item : i) {
            String log = "Degree: " + item.get_degree() + " ,CGPA: " + item.get_cgpa() ;
            // Writing Contacts to log
            Log.d("Edu: ", log);
        }

        return i;
    }

    public void submitForm(View view) {

        Intent intent = new Intent(FormEdu.this,FormPro.class);
        startActivity(intent);


    }
}
