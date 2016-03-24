package activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;

import android.util.Log;
import android.util.TypedValue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vishwashrisairm.materialdesign.R;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;


import java.util.ArrayList;
import java.util.List;

import adapter.HomeRecyclerViewAdapter;
import database.EduInfo;
import database.ItemStatus;


import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 23/2/16.
 */
public class HomeFragment extends Fragment {

//
    private RecyclerView hRecyclerView;
    private RecyclerView.Adapter hAdapter;
    private RecyclerView.LayoutManager hLayoutManager;
    private List<ItemStatus> mItems;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public void onResume() {
        super.onResume();
        ((HomeRecyclerViewAdapter)hAdapter).setOnItemClickListener(new HomeRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("HomeRecyclerView:-", " Clicked on Item " + position);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        hRecyclerView=(RecyclerView)rootView.findViewById(R.id.homeRecyclerView);
        hRecyclerView.setHasFixedSize(true);
        hLayoutManager=new LinearLayoutManager(this.getContext());
        hRecyclerView.setLayoutManager(hLayoutManager);
        mItems=getDataSet();
        hAdapter=new HomeRecyclerViewAdapter(mItems);
        hRecyclerView.setAdapter(hAdapter);

        //        Swipe Touch Listener
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(hRecyclerView,
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

                                    ItemStatus eduid=mItems.get(position);
                                    mItems.remove(position);
                                    PInfoDbHandler db = new PInfoDbHandler(recyclerView.getContext(),"",null,1);
                                    db.deleteStatus(eduid);
                                    hAdapter.notifyItemRemoved(position);
                                }
                                hAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    Toast.makeText(MainActivity.this, mItems.get(position) + " swiped right", Toast.LENGTH_SHORT).show();
                                    ItemStatus eduid=mItems.get(position);
                                    mItems.remove(position);
                                    PInfoDbHandler db = new PInfoDbHandler(recyclerView.getContext(),"",null,1);
                                    db.deleteStatus(eduid);

                                    hAdapter.notifyItemRemoved(position);
                                }
                                hAdapter.notifyDataSetChanged();
                            }
                        });

        hRecyclerView.addOnItemTouchListener(swipeTouchListener);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Title");

                // Set up the input
                final EditText input = new EditText(getActivity());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        String m_text = input.getText().toString();
                        PInfoDbHandler db = new PInfoDbHandler(getActivity(),"",null,1);
                        ItemStatus s = new ItemStatus(m_text,0,0,0,0,0,0);
//                        db.addStatus(s);
                        Intent i = new Intent(getActivity(),FormPersonal.class);

                       startActivity(i);
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


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private List<ItemStatus> getDataSet() {
        PInfoDbHandler db = new PInfoDbHandler(this.getContext(),"",null,1);
        List<ItemStatus> i = db.getAllStatus();
        for (ItemStatus item : i) {
            String log = "Id: " + item.getTitle() + " ,Name: " + item.get_item_id() ;
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        return i;
    }



}
