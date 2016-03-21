package activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import database.CurrInfo;
import database.EduInfo;
import database.PersonalInfo;
import database.SkillsInfo;
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


//        Add queries
//        db.addPInfo(new PersonalInfo(1, "abc", "asdad", "23/12/2314", "fwefwef", "fefwfew", "abc"));
//      db.addEInfo(new EduInfo(1,2, "edu1", "abc", "xyz", "dwqd"));
//        db.addCInfo(new CurrInfo(1,1,"play chess"));
//        db.addSInfo(new SkillsInfo(1,1,"beg","HTML"));
;


//Delete queries
//        db.deletePInfo(db.getPInfo(1));
//        db.deleteEInfo(db.getEInfo(1));
//          db.deleteCInfo(db.getCInfo(2));
//        db.deleteSInfo(db.getSInfo(4));

//      Queries for testing
        Log.d("Insert", "Inserting..,");

//Update queries
//        PersonalInfo ptest=db.getPInfo(1);
//        ptest.set_fullname("xyz");
//        db.updatePInfo(ptest);

//        EduInfo etest=db.getEInfo(2);
//        etest.set_degree("btech");
//        db.updateEInfo(etest);

//         CurrInfo ctest=db.getCInfo(5);
//         ctest.set_name("music");
//         db.updateCInfo(ctest);
           SkillsInfo stest=db.getSInfo(9);
           stest.set_nameofskill("JS");
           db.updateSInfo(stest);

//        Display all queries
//        List<PersonalInfo> p = db.getAllPInfo();
//        for (PersonalInfo pi : p) {
//            String log = "Id: " + pi.get_id() + " ,Name: " + pi.get_fullname() + " ,Contact: " + pi.get_contact();
//            // Writing Contacts to log
//            Log.d("Name: ", log);
//        }
//
//        List<EduInfo> e=db.getAllEInfo();
//        for (EduInfo pi : e) {
//            String log = "Id: " + pi.get_eduid() + " ,Degree: " + pi.get_degree() + " ,Cgpa: " + pi.get_cgpa();
//            // Writing Contacts to log
//            Log.d("Edu: ", log);
//        }

//
        List<SkillsInfo> s=db.getAllSInfo();
        for (SkillsInfo pi : s) {
            String log = "Id: " + pi.get_skillid() + " ,Name: " + pi.get_nameofskill()+",Proficiency: "+pi.get_prof() ;
            // Writing Contacts to log
            Log.d("Skills: ", log);
        }
    }


}
