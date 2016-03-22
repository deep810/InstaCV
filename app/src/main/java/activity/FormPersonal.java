package activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishwashrisairm.materialdesign.R;
import com.kinvey.android.offline.DatabaseHandler;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import database.EduInfo;
import database.ItemStatus;
import database.PersonalInfo;
import database.ProjectInfo;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 15/3/16.
 */
public class FormPersonal extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText inputpName, inputaddress, inputdob, inputcontact, inputemail;
    private TextInputLayout inputLayoutpName, inputLayoutaddress, inputLayoutdob, inputLayoutcontact, inputLayoutemail;
    private Button btnsave,dp;  //Date picker
    int year_x,month_x,day_x;
    static final int DIALOG_ID=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_personal);



        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);

        showDialogOnButtonClick();



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputLayoutpName = (TextInputLayout) findViewById(R.id.input_layout_pname);
        inputLayoutaddress = (TextInputLayout) findViewById(R.id.input_layout_address);
        inputLayoutdob = (TextInputLayout) findViewById(R.id.input_layout_dob);
        inputLayoutcontact = (TextInputLayout) findViewById(R.id.input_layout_contact);
        inputLayoutemail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputpName = (EditText) findViewById(R.id.input_pname);
        inputaddress = (EditText) findViewById(R.id.input_address);
        inputdob = (EditText) findViewById(R.id.input_dob);
        inputcontact = (EditText) findViewById(R.id.input_contact);
        inputemail = (EditText) findViewById(R.id.input_email);
        btnsave = (Button) findViewById(R.id.btn_save);




        /*inputpName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                hasText(inputpName);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });*/
        inputpName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                Validation.hasText(inputpName);
            }
        });
        inputaddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                Validation.hasText(inputaddress);
            }
        });
        inputdob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                Validation.hasText(inputdob);
            }
        });
        inputcontact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    Validation.isPhoneNumber(inputcontact,true);
            }
        });
        inputemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                Validation.isEmailAddress(inputemail,true);
            }
        });




        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    public void showDialogOnButtonClick(){
        dp=(Button)findViewById(R.id.dp);
        dp.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        showDialog(DIALOG_ID);
                    }
                }
        );

    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id== DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener,year_x,month_x,day_x);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener=new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x=year;
            month_x=monthOfYear+1;
            day_x=dayOfMonth;
            inputdob.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }


    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    /**
     * Validating form
     */
    private void submitForm() {
        PInfoDbHandler db = new PInfoDbHandler(this,"",null,1);
//        db.addPInfo(new PersonalInfo(inputpName.getEditText().toString(),inputLayoutaddress.getEditText().toString()
//        ,inputLayoutdob.getEditText().toString(),inputLayoutpName.getEditText().toString(),inputLayoutpName.getEditText().toString()));
        db.addPInfo(new PersonalInfo(1, "abc", "asdad", "23/12/2314", "fwefwef", "fefwfew", "abc"));
     //   db.addPInfo(new PersonalInfo(2,"abcde","asdfd","23/1/2314","asddad","few","abc"));
        //db.addEInfo(new EduInfo(1, 5, "edu1", "abc", "xyz", "dwqd"));
//        db.deletePInfo(db.getPInfo(1));
       // db.deleteEInfo(db.getEInfo(1));
//        db.addStatus(new ItemStatus(504,"title4",0,0,0,0,0,0));
//        db.addStatus(new ItemStatus(505,"title5",0,0,0,0,0,0));
//        db.addStatus(new ItemStatus(506,"title6",0,0,0,0,0,0));

//        ProjectInfo i = new ProjectInfo(1,5,"project1","surat","3/2/15","cvxvsd","student");
//
//        db.addPRInfo(i);
//
//        ProjectInfo a = db.getPRInfo(2);
//        String log ="Id: "+a.get_id()+"  ,proid: "+a.get_proid()+" ,title: "+a.get_title()+" ,location: "+
//                    a.get_location()+" ,time: "+a.get_time()+" ,desig: "+a.get_desig()+" ,desc: "+a.get_desc();
//        Log.d("Project Table: ", log);
//        List<ProjectInfo> b = db.getAllPRInfo();
//        for(ProjectInfo x : b){
//            log ="Id: "+x.get_id()+"  ,proid: "+x.get_proid()+" ,title: "+x.get_title()+" ,location: "+
//                    x.get_location()+" ,time: "+x.get_time()+" ,desig: "+x.get_desig()+" ,desc: "+x.get_desc();
//            Log.d("Status Table: ",log);
//        }

       /* db.updateStatus(new ItemStatus(1, "Jhon", 0, 0, 0, 0, 0, 0));

       ItemStatus a = db.getStatus(1);
        String log ="Id: "+a.get_item_id()+" ,Title: "+a.getTitle() + " ,personal_status: "+a.get_personalstatus()+" ,edustatus: "+a.get_edustatus()+" ,_prostatus: " +a.get_prostatus()+
                " ,skillstatus: "+a.get_skillstatus()+" ,refstatus: "+a.get_refstatus()+" ,excurstatus: "+a.get_excurstatus();
        Log.d("Status Table: ",log);*/
//        db.deletePRInfo(i);



//      Queries for testing
        /*Log.d("Insert", "Inserting..,");

        List<PersonalInfo> p = db.getAllPInfo();
        for (PersonalInfo pi : p) {
            String log = "Id: " + pi.get_id() + " ,Name: " + pi.get_fullname() + " ,Contact: " + pi.get_contact();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        List<EduInfo> e=db.getAllEInfo();
        for (EduInfo pi : e) {
            String log = "Id: " + pi.get_id() + " ,Name: " + pi.get_degree() + " ,Contact: " + pi.get_cgpa();
            // Writing Contacts to log
            Log.d("Edu: ", log);
        }*/
    }


}
