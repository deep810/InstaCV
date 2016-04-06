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
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.vishwashrisairm.materialdesign.R;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.util.List;

import adapter.EduRecyclerViewAdapter;
import adapter.ProRecyclerViewAdapter;
import database.EduInfo;
import database.ProjectInfo;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 23/3/16.
 */
public class FormPro extends AppCompatActivity {

    private RecyclerView proRecyclerView;
    private RecyclerView.Adapter proAdapter;
    private RecyclerView.LayoutManager proLayoutManager;
    private List<ProjectInfo> mItems;
    int item_id;
    private ImageButton btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_projects);

        item_id=getIntent().getIntExtra("item_id",0);

        proRecyclerView=(RecyclerView)findViewById(R.id.proRecyclerView);
        proRecyclerView.setHasFixedSize(true);
        proLayoutManager=new LinearLayoutManager(this);
        proRecyclerView.setLayoutManager(proLayoutManager);
        mItems=getDataSet();
        proAdapter=new ProRecyclerViewAdapter(mItems);
        proRecyclerView.setAdapter(proAdapter);

        btnback = (ImageButton) findViewById(R.id.btn_back_pro);

        //        Swipe Touch Listener
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(proRecyclerView,
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

                                    ProjectInfo eduid=mItems.get(position);
                                    mItems.remove(position);
                                    PInfoDbHandler db = new PInfoDbHandler(recyclerView.getContext(),"",null,1);
                                    db.deletePRInfo(eduid);
                                    proAdapter.notifyItemRemoved(position);
                                }
                                proAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    Toast.makeText(MainActivity.this, mItems.get(position) + " swiped right", Toast.LENGTH_SHORT).show();
                                    ProjectInfo eduid=mItems.get(position);
                                    mItems.remove(position);
                                    PInfoDbHandler db = new PInfoDbHandler(recyclerView.getContext(),"",null,1);
                                    db.deletePRInfo(eduid);

                                    proAdapter.notifyItemRemoved(position);
                                }
                                proAdapter.notifyDataSetChanged();
                            }
                        });

        proRecyclerView.addOnItemTouchListener(swipeTouchListener);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_pro);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormPro.this);
                builder.setTitle("Projects/Experience Information");

                // Set up the input
                final EditText pro_title = new EditText(FormPro.this);
                final EditText  pro_location=new  EditText(FormPro.this);
                final EditText pro_duration = new EditText(FormPro.this);
                final EditText pro_designation = new EditText(FormPro.this);
                final EditText pro_description = new EditText(FormPro.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                pro_title.setInputType(InputType.TYPE_CLASS_TEXT);
                pro_title.setHint("Title");
                pro_location.setInputType(InputType.TYPE_CLASS_TEXT);
                pro_location.setHint("Location");
                pro_duration.setInputType(InputType.TYPE_CLASS_TEXT);
                pro_duration.setHint("Duration");
                pro_designation.setInputType(InputType.TYPE_CLASS_TEXT);
                pro_designation.setHint("Designation");
                pro_description.setInputType(InputType.TYPE_CLASS_TEXT);
                pro_description.setHint("Description");

                LinearLayout ll=new LinearLayout(FormPro.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(pro_title);
                ll.addView(pro_location);
                ll.addView(pro_duration);
                ll.addView(pro_designation);
                ll.addView(pro_description);


                builder.setView(ll);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String title = pro_title.getText().toString();
                        String location = pro_location.getText().toString();
                        String duration = pro_duration.getText().toString();
                        String designation = pro_designation.getText().toString();
                        String description = pro_description.getText().toString();


                        PInfoDbHandler db = new PInfoDbHandler(FormPro.this,"",null,1);
                        ProjectInfo p=new ProjectInfo(item_id,title,location,duration,designation,description);
                        db.addPRInfo(p);
                        Intent i = new Intent(FormPro.this,FormPro.class);
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

                builder.show();

            }
        });


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormPro.this,FormEdu.class);
                intent.putExtra("item_id",item_id);
                startActivity(intent);
                finish();
            }
        });


    }

    private List<ProjectInfo> getDataSet() {
        PInfoDbHandler db = new PInfoDbHandler(this,"",null,1);
        List<ProjectInfo> pr = db.getAllPRInfoById(item_id);
        for (ProjectInfo item : pr) {
            String log = "Title: " + item.get_title() + " ,Designation: " + item.get_desig() ;
            // Writing Contacts to log
            Log.d("Pro: ", log);
        }

        return pr;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((ProRecyclerViewAdapter) proAdapter).setOnItemClickListener(new ProRecyclerViewAdapter.MyClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Log.i("pro", " Clicked on Item " + position);
            }
        });

    }

    public void submitForm(View view) {
        Intent intent = new Intent(this,FormSkill.class);
        intent.putExtra("item_id",item_id);
        startActivity(intent);
    }
}
