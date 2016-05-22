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
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.vishwashrisairm.materialdesign.R;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.Normalizer;
import java.util.List;

import adapter.EduRecyclerViewAdapter;
import adapter.HomeRecyclerViewAdapter;
import adapter.RefRecyclerViewAdapter;
import database.EduInfo;
import database.ItemStatus;
import database.RefInfo;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 22/3/16.
 */
public class FormRef extends AppCompatActivity {
    private RecyclerView refRecyclerView;
    private RecyclerView.Adapter refAdapter;
    private RecyclerView.LayoutManager refLayoutManager;
    private List<RefInfo> mItems;
    private int item_id;
    private ImageButton btnback;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_ref);

        item_id=getIntent().getIntExtra("item_id",0);

        refRecyclerView=(RecyclerView)findViewById(R.id.refRecyclerView);
        refRecyclerView.setHasFixedSize(true);
        refLayoutManager=new LinearLayoutManager(this);
        refRecyclerView.setLayoutManager(refLayoutManager);
        mItems=getDataSet();
        refAdapter=new RefRecyclerViewAdapter(mItems);
        refRecyclerView.setAdapter(refAdapter);
        btnback = (ImageButton) findViewById(R.id.btn_back_ref);

        mAdView = (AdView) findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("7D087F53B0932D81A57D9DF7BF3C0CBA").build();
        mAdView.loadAd(adRequest);

        //        Swipe Touch Listener
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(refRecyclerView,
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

                                    RefInfo eduid=mItems.get(position);
                                    mItems.remove(position);
                                    PInfoDbHandler db = new PInfoDbHandler(recyclerView.getContext(),"",null,1);
                                    db.deleteRInfo(eduid);
                                    refAdapter.notifyItemRemoved(position);
                                }
                                refAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    Toast.makeText(MainActivity.this, mItems.get(position) + " swiped right", Toast.LENGTH_SHORT).show();
                                    RefInfo eduid=mItems.get(position);
                                    PInfoDbHandler db = new PInfoDbHandler(recyclerView.getContext(),"",null,1);
                                    db.deleteRInfo(eduid);
                                    mItems.remove(position);
                                    refAdapter.notifyItemRemoved(position);
                                }
                                refAdapter.notifyDataSetChanged();
                            }
                        });

        refRecyclerView.addOnItemTouchListener(swipeTouchListener);


//        Floating action button
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_ref);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormRef.this);
                builder.setTitle("References");

                // Set up the input
                final TextView ref_name_label=new TextView(FormRef.this);
                final EditText ref_name = new EditText(FormRef.this);
                final TextView ref_contact_label=new TextView(FormRef.this);
                final EditText  ref_contact= new EditText(FormRef.this);
                final TextView ref_position_label=new TextView(FormRef.this);
                final EditText ref_position = new EditText(FormRef.this);
                final TextView ref_org_label=new TextView(FormRef.this);
                final EditText ref_org = new EditText(FormRef.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                ref_name_label.setText("Name:");
                ref_name_label.setTextSize(20);
                ref_name_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                ref_name.setInputType(InputType.TYPE_CLASS_TEXT);

                ref_contact_label.setText("Contact:");
                ref_contact_label.setTextSize(20);
                ref_contact_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                ref_contact.setInputType(InputType.TYPE_CLASS_TEXT);

                ref_position_label.setText("Position:");
                ref_position_label.setTextSize(20);
                ref_position_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                ref_position.setInputType(InputType.TYPE_CLASS_TEXT);

                ref_org_label.setText("Organisation:");
                ref_org_label.setTextSize(20);
                ref_org_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                ref_org.setInputType(InputType.TYPE_CLASS_TEXT);


                LinearLayout ll=new LinearLayout(FormRef.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(ref_name_label);
                ll.addView(ref_name);
                ll.addView(ref_contact_label);
                ll.addView(ref_contact);
                ll.addView(ref_position_label);
                ll.addView(ref_position);
                ll.addView(ref_org_label);
                ll.addView(ref_org);

                ScrollView s=new ScrollView(FormRef.this);
                s.addView(ll);
                builder.setView(s);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = ref_name.getText().toString();
                        String contact = ref_contact.getText().toString();
                        String position = ref_position.getText().toString();
                        String org = ref_org.getText().toString();


                        PInfoDbHandler db = new PInfoDbHandler(FormRef.this,"",null,1);
                        RefInfo e=new RefInfo(item_id, name, contact, position, org);
                        db.addRInfo(e);
                        Intent i = new Intent(FormRef.this,FormRef.class);
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
                Dialog d = builder.setView(ll).create();
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
                Intent intent = new Intent(FormRef.this,FormExcr.class);
                intent.putExtra("item_id",item_id);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((RefRecyclerViewAdapter) refAdapter).setOnItemClickListener(new RefRecyclerViewAdapter.MyClickListener(){

            @Override
            public void onItemClick(int position, View v) {
                Log.i("edu", " Clicked on Item " + position);
            }
        });

    }

    private List<RefInfo> getDataSet() {
        PInfoDbHandler db = new PInfoDbHandler(this,"",null,1);
        List<RefInfo> i = db.getAllRInfoById(item_id);
        for (RefInfo item : i) {
          //  String log = "Degree: " + item.get_degree() + " ,CGPA: " + item.get_cgpa() ;
            // Writing Contacts to log
          //  Log.d("Edu: ", log);
        }

        return i;
    }

    public void submitForm(View view) {
        PInfoDbHandler db = new PInfoDbHandler(FormRef.this,"",null,1);

        if(mItems.size()>0)
        {
            db.updateStatusRef(item_id, 1);
        }
        else
        {
            db.updateStatusRef(item_id, 0);
        }

        if (db.getRInfoCountById(item_id)>0) {
            Intent intent = new Intent(FormRef.this,MainActivity.class);
            intent.putExtra("item_id",item_id);
            startActivity(intent);
            finish();
        }


    }
}
