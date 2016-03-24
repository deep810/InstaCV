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
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_skill);

        skillRecyclerView=(RecyclerView)findViewById(R.id.skillRecyclerView);
        skillRecyclerView.setHasFixedSize(true);
        skillLayoutManager=new LinearLayoutManager(this);
        skillRecyclerView.setLayoutManager(skillLayoutManager);
        mItems=getDataSet();
        skillAdapter=new SkillsRecyclerViewAdapter(mItems);
        skillRecyclerView.setAdapter(skillAdapter);

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
                final EditText skill_name = new EditText(FormSkill.this);
                final EditText  proficiency= new EditText(FormSkill.this);
              //  final EditText cgpa = new EditText(FormEdu.this);
               // final EditText institute = new EditText(FormEdu.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                skill_name.setInputType(InputType.TYPE_CLASS_TEXT);
                skill_name.setHint("java/php..");
                proficiency.setInputType(InputType.TYPE_CLASS_TEXT);
                proficiency.setHint("beginner/advanced");
                //cgpa.setInputType(InputType.TYPE_CLASS_TEXT);
                //cgpa.setHint("CGPA/%age");
                //institute.setInputType(InputType.TYPE_CLASS_TEXT);
               // institute.setHint("Institute");

                LinearLayout ll=new LinearLayout(FormSkill.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(skill_name);
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
                        SkillsInfo e=new SkillsInfo(1, prof, skill);
                        db.addSInfo(e);
                        Intent i = new Intent(FormSkill.this,FormSkill.class);
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
        ((SkillsRecyclerViewAdapter) skillAdapter).setOnItemClickListener(new SkillsRecyclerViewAdapter.MyClickListener(){

            @Override
            public void onItemClick(int position, View v) {
                Log.i("edu", " Clicked on Item " + position);
            }
        });

    }

    private List<SkillsInfo> getDataSet() {
        PInfoDbHandler db = new PInfoDbHandler(this,"",null,1);
        List<SkillsInfo> i = db.getAllSInfo();
        for (SkillsInfo item : i) {
          //  String log = "Degree: " + item.get_degree() + " ,CGPA: " + item.get_cgpa() ;
            // Writing Contacts to log
           // Log.d("Edu: ", log);
        }

        return i;
    }

    public void submitForm(View view) {
        Intent intent = new Intent(this,FormExcr.class);
        startActivity(intent);

    }
}
