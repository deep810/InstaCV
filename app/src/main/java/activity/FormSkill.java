package activity;

import android.app.Dialog;
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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vishwashrisairm.materialdesign.R;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.Normalizer;
import java.util.List;

import adapter.EduRecyclerViewAdapter;
import adapter.HomeRecyclerViewAdapter;
import adapter.SkillsRecyclerViewAdapter;
import database.EduInfo;
import database.ItemStatus;
import database.RefInfo;
import database.SkillsInfo;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 22/3/16.
 */
public class FormSkill extends AppCompatActivity {
    private RecyclerView skillRecyclerView;
    private RecyclerView.Adapter skillAdapter;
    private RecyclerView.LayoutManager skillLayoutManager;
    private List<SkillsInfo> mItems;
    private int item_id;
    private ImageButton btnback;
    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_skill);

        item_id=getIntent().getIntExtra("item_id",0);

        skillRecyclerView=(RecyclerView)findViewById(R.id.skillRecyclerView);
        skillRecyclerView.setHasFixedSize(true);
        skillLayoutManager=new LinearLayoutManager(this);
        skillRecyclerView.setLayoutManager(skillLayoutManager);
        mItems=getDataSet();
        skillAdapter=new SkillsRecyclerViewAdapter(mItems);
        skillRecyclerView.setAdapter(skillAdapter);
        btnback = (ImageButton) findViewById(R.id.btn_back_skill);

        mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("7D087F53B0932D81A57D9DF7BF3C0CBA").build();
        mAdView.loadAd(adRequest);


        //        Swipe Touch Listener
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(skillRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    Toast.makeText(MainActivity.this, mItems.get(position) + " swiped left", Toast.LENGTH_SHORT).show();

                                    SkillsInfo eduid=mItems.get(position);
                                    PInfoDbHandler db = new PInfoDbHandler(recyclerView.getContext(),"",null,1);
                                    db.deleteSInfo(eduid);
                                    mItems.remove(position);
                                    skillAdapter.notifyItemRemoved(position);
                                }
                                skillAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    Toast.makeText(MainActivity.this, mItems.get(position) + " swiped right", Toast.LENGTH_SHORT).show();
                                    SkillsInfo eduid=mItems.get(position);
                                    PInfoDbHandler db = new PInfoDbHandler(recyclerView.getContext(),"",null,1);
                                    mItems.remove(position);
                                    db.deleteSInfo(eduid);
                                    skillAdapter.notifyItemRemoved(position);
                                }
                                skillAdapter.notifyDataSetChanged();
                            }
                        });

        skillRecyclerView.addOnItemTouchListener(swipeTouchListener);

//        Floating action button
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_skill);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormSkill.this);
                builder.setTitle("Skills Information");

                // Set up the input
                final TextView skill_name_label=new TextView(FormSkill.this);
                final EditText skill_name = new EditText(FormSkill.this);
                final TextView proficiency_label=new TextView(FormSkill.this);
                final EditText  proficiency= new EditText(FormSkill.this);
              //  final EditText cgpa = new EditText(FormEdu.this);
               // final EditText institute = new EditText(FormEdu.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                skill_name_label.setText("Skill:");
                skill_name_label.setTextSize(20);
                skill_name_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                skill_name.setInputType(InputType.TYPE_CLASS_TEXT);
                skill_name.setHint("java/php..");

                proficiency_label.setText("Proficiency:");
                proficiency_label.setTextSize(20);
                proficiency_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                proficiency.setInputType(InputType.TYPE_CLASS_TEXT);
                proficiency.setHint("beginner/advanced");
                //cgpa.setInputType(InputType.TYPE_CLASS_TEXT);
                //cgpa.setHint("CGPA/%age");
                //institute.setInputType(InputType.TYPE_CLASS_TEXT);
               // institute.setHint("Institute");

                LinearLayout ll=new LinearLayout(FormSkill.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(skill_name_label);
                ll.addView(skill_name);
                ll.addView(proficiency_label);
                ll.addView(proficiency);
                //ll.addView(cgpa);
                //ll.addView(institute);


                builder.setView(ll);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String skill = skill_name.getText().toString();
                        String prof = proficiency.getText().toString();
                       //String cg = cgpa.getText().toString();
                        //String ins = institute.getText().toString();


                        PInfoDbHandler db = new PInfoDbHandler(FormSkill.this,"",null,1);
                        SkillsInfo e=new SkillsInfo(item_id, prof, skill);
                        db.addSInfo(e);
                        Intent i = new Intent(FormSkill.this,FormSkill.class);
                        i.putExtra("item_id",item_id);
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

//                builder.show();

                Dialog d = builder.create();
                d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(d.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;  // or try WRAP_CONTENT
                d.show();
                d.getWindow().setAttributes(lp);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormSkill.this,FormPro.class);
                intent.putExtra("item_id",item_id);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((SkillsRecyclerViewAdapter) skillAdapter).setOnItemClickListener(new SkillsRecyclerViewAdapter.MyClickListener(){

            @Override
            public void onItemClick(int position, View v) {
                Log.i("edu", " Clicked on Item " + position);
            }
        });

    }

    private List<SkillsInfo> getDataSet() {
        PInfoDbHandler db = new PInfoDbHandler(this,"",null,1);
        List<SkillsInfo> i = db.getAllSInfoById(item_id);
        for (SkillsInfo item : i) {
          //  String log = "Degree: " + item.get_degree() + " ,CGPA: " + item.get_cgpa() ;
            // Writing Contacts to log
           // Log.d("Edu: ", log);
        }

        return i;
    }

    public void submitForm(View view) {
        PInfoDbHandler db = new PInfoDbHandler(FormSkill.this,"",null,1);
        if(mItems.size()>0)
        {
            db.updateStatusSkill(item_id, 1);
        }
        else
            db.updateStatusSkill(item_id, 0);

        if(db.getSInfoCountById(item_id)>0){

        Intent intent = new Intent(this,FormExcr.class);
        intent.putExtra("item_id",item_id);
        startActivity(intent);
        finish();
        }

    }
}
