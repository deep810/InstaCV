package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.vishwashrisairm.materialdesign.R;

import java.util.List;

import adapter.EduRecyclerViewAdapter;
import adapter.ExcrRecyclerViewAdapter;
import adapter.ProRecyclerViewAdapter;
import adapter.RefRecyclerViewAdapter;
import adapter.SkillsRecyclerViewAdapter;
import database.EduInfo;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 24/3/16.
 */
public class ItemActivity extends AppCompatActivity {

    private RecyclerView eduItemRecyclerView;
    private RecyclerView.Adapter eduItemAdapter;
    private RecyclerView.LayoutManager eduItemLayoutManager;

    private RecyclerView proItemRecyclerView;
    private RecyclerView.Adapter proItemAdapter;
    private RecyclerView.LayoutManager proItemLayoutManager;

    private RecyclerView skillItemRecyclerView;
    private RecyclerView.Adapter skillItemAdapter;
    private RecyclerView.LayoutManager skillItemLayoutManager;

    private RecyclerView excrItemRecyclerView;
    private RecyclerView.Adapter excrItemAdapter;
    private RecyclerView.LayoutManager excrItemLayoutManager;

    private RecyclerView refItemRecyclerView;
    private RecyclerView.Adapter refItemAdapter;
    private RecyclerView.LayoutManager refItemLayoutManager;


    //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);

        PInfoDbHandler db = new PInfoDbHandler(this,"",null,1);

       // eduItemRecyclerView.setLayoutParams(params);
        //proItemRecyclerView.setLayoutParams(params);
       // skillItemRecyclerView.setLayoutParams(params);
        //excrItemRecyclerView.setLayoutParams(params);
        //refItemRecyclerView.setLayoutParams(params);


        eduItemRecyclerView=(RecyclerView)findViewById(R.id.eduItemRecyclerView);
        eduItemRecyclerView.setHasFixedSize(true);
        eduItemLayoutManager=new LinearLayoutManager(this);
        eduItemRecyclerView.setLayoutManager(eduItemLayoutManager);
//        mItems=getDataSet();
        eduItemAdapter=new EduRecyclerViewAdapter(db.getAllEInfo());
        eduItemRecyclerView.setAdapter(eduItemAdapter);

        proItemRecyclerView=(RecyclerView)findViewById(R.id.proItemRecyclerView);
        proItemRecyclerView.setHasFixedSize(true);
        proItemLayoutManager=new LinearLayoutManager(this);
        proItemRecyclerView.setLayoutManager(proItemLayoutManager);
//        mItems=getDataSet();
        proItemAdapter=new ProRecyclerViewAdapter(db.getAllPRInfo());
        proItemRecyclerView.setAdapter(proItemAdapter);

        skillItemRecyclerView=(RecyclerView)findViewById(R.id.skillItemRecyclerView);
        skillItemRecyclerView.setHasFixedSize(true);
        skillItemLayoutManager=new LinearLayoutManager(this);
        skillItemRecyclerView.setLayoutManager(skillItemLayoutManager);
//        mItems=getDataSet();
        skillItemAdapter=new SkillsRecyclerViewAdapter(db.getAllSInfo());
        skillItemRecyclerView.setAdapter(skillItemAdapter);

        excrItemRecyclerView=(RecyclerView)findViewById(R.id.excrItemRecyclerView);
        excrItemRecyclerView.setHasFixedSize(true);
        excrItemLayoutManager=new LinearLayoutManager(this);
        excrItemRecyclerView.setLayoutManager(excrItemLayoutManager);
//        mItems=getDataSet();
        excrItemAdapter=new ExcrRecyclerViewAdapter(db.getAllCInfo());
        excrItemRecyclerView.setAdapter(excrItemAdapter);

        refItemRecyclerView=(RecyclerView)findViewById(R.id.refItemRecyclerView);
        refItemRecyclerView.setHasFixedSize(true);
        refItemLayoutManager=new LinearLayoutManager(this);
        refItemRecyclerView.setLayoutManager(refItemLayoutManager);
//        mItems=getDataSet();
        refItemAdapter=new RefRecyclerViewAdapter(db.getAllRInfo());
        refItemRecyclerView.setAdapter(refItemAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
