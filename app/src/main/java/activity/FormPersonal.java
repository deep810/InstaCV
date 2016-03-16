package activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishwashrisairm.materialdesign.R;

import java.util.Calendar;

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

    /**
     * Validating form
     */
    private void submitForm() {

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
