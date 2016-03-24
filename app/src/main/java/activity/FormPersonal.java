package activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
    private EditText inputpName, inputaddress, inputdob, inputcontact, inputemail,inputobjective;
    private TextInputLayout inputLayoutpName, inputLayoutaddress, inputLayoutdob, inputLayoutcontact, inputLayoutemail;
    private Button btnsave,dp;  //Date picker
    int year_x,month_x,day_x;
    static final int DIALOG_ID=0;

    String title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_personal);

        title=getIntent().getStringExtra("Title");

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
        inputobjective = (EditText) findViewById(R.id.input_objective);

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

        ItemStatus i_status=new ItemStatus(title,1,0,0,0,0,0);
        int id = db.addStatus(i_status);
        Log.d("Insted Item_status: ",String.valueOf(id));


        String fname = inputpName.getText().toString();
        String add = inputpName.getText().toString();
        String dob = inputdob.getText().toString();
        String cont = inputcontact.getText().toString();
        String email = inputemail.getText().toString();
        String objective = inputobjective.getText().toString();

        PersonalInfo p = new PersonalInfo(id,fname,add,dob,cont,email,objective);
        db.addPInfo(p);

        Intent i = new Intent(this,FormEdu.class);
        i.putExtra("item_id",id);
        startActivity(i);

    }


}
