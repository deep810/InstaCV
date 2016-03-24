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

import java.util.List;

import adapter.EduRecyclerViewAdapter;
import adapter.ExcrRecyclerViewAdapter;
import database.CurrInfo;
import database.EduInfo;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 23/3/16.
 */
public class FormExcr extends AppCompatActivity {
    private RecyclerView excrRecyclerView;
    private RecyclerView.Adapter excrAdapter;
    private RecyclerView.LayoutManager excrLayoutManager;
    private List<CurrInfo> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_excr);

        excrRecyclerView=(RecyclerView)findViewById(R.id.excrRecyclerView);
        excrRecyclerView.setHasFixedSize(true);
        excrLayoutManager=new LinearLayoutManager(this);
        excrRecyclerView.setLayoutManager(excrLayoutManager);
        mItems=getDataSet();
        excrAdapter=new ExcrRecyclerViewAdapter(mItems);
        excrRecyclerView.setAdapter(excrAdapter);

        //        Swipe Touch Listener
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(excrRecyclerView,
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

                                    CurrInfo eduid=mItems.get(position);

                                    PInfoDbHandler db = new PInfoDbHandler(recyclerView.getContext(),"",null,1);
                                    db.deleteCInfo(eduid);
                                    mItems.remove(position);
                                    excrAdapter.notifyItemRemoved(position);
                                }
                                excrAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    Toast.makeText(MainActivity.this, mItems.get(position) + " swiped right", Toast.LENGTH_SHORT).show();
                                    CurrInfo eduid=mItems.get(position);
                                    mItems.remove(position);
                                    PInfoDbHandler db = new PInfoDbHandler(recyclerView.getContext(),"",null,1);
                                    db.deleteCInfo(eduid);

                                    excrAdapter.notifyItemRemoved(position);
                                }
                                excrAdapter.notifyDataSetChanged();
                            }
                        });

        excrRecyclerView.addOnItemTouchListener(swipeTouchListener);


//        Floating action button
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_excr);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormExcr.this);
                builder.setTitle("ExtraCurricular Activities");

                // Set up the input
                final EditText excra = new EditText(FormExcr.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                excra.setInputType(InputType.TYPE_CLASS_TEXT);
                excra.setHint("Activity");


                LinearLayout ll=new LinearLayout(FormExcr.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.addView(excra);



                builder.setView(ll);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String ext = excra.getText().toString();


                        PInfoDbHandler db = new PInfoDbHandler(FormExcr.this,"",null,1);
                        CurrInfo c=new CurrInfo(1,ext);
                        db.addCInfo(c);
                        Intent i = new Intent(FormExcr.this,FormExcr.class);
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
        ((ExcrRecyclerViewAdapter) excrAdapter).setOnItemClickListener(new ExcrRecyclerViewAdapter.MyClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Log.i("excr", " Clicked on Item " + position);
            }
        });

    }

    private List<CurrInfo> getDataSet() {
        PInfoDbHandler db = new PInfoDbHandler(this,"",null,1);
        List<CurrInfo> i = db.getAllCInfo();
        return i;
    }

    public void submitForm(View view) {
        Intent intent = new Intent(this,FormRef.class);
        startActivity(intent);

    }

}

