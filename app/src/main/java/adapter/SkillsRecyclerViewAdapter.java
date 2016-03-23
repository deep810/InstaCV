package adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishwashrisairm.materialdesign.R;

import java.util.List;

import database.EduInfo;
import database.ItemStatus;
import database.SkillsInfo;

/**
 * Created by vishwashrisairm on 22/3/16.
 */
public class SkillsRecyclerViewAdapter extends RecyclerView.Adapter<SkillsRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "SkillsRecyclerViewAdapter";
    private List<SkillsInfo> skillDataset;
    private static MyClickListener skillClickListener;

    public SkillsRecyclerViewAdapter(List<SkillsInfo> dataSet) {
        skillDataset=dataSet;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_card_view,parent,false);
        DataObjectHolder dataObjectHolder=new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        //holder.t1.setText(eduDataset.get(position).get_degree());
        holder.t1.setText(skillDataset.get(position).get_nameofskill());
        holder.t2.setText(skillDataset.get(position).get_prof());
       // holder.t4.setText(eduDataset.get(position).get_institute());


    }

    @Override
    public int getItemCount() {
        return skillDataset.size();
    }



    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1, t2;
        Button b1, b2;
//        private MyClickListener eduClickListener;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            label=(TextView)itemView.findViewById(R.id.home_card_1);
            t1 = (TextView) itemView.findViewById(R.id.skill_card_1);
            t2 = (TextView) itemView.findViewById(R.id.skill_card_2);
            //t3 = (TextView) itemView.findViewById(R.id.edu_card_3);
            //t4 = (TextView) itemView.findViewById(R.id.edu_card_4);
            b1 = (Button) itemView.findViewById(R.id.eeb);
            b2 = (Button) itemView.findViewById(R.id.edb);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
            b1.setOnClickListener(this);
            b2.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == b1.getId()){
                Toast.makeText(v.getContext(), "Edit: " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            } else if(v.getId() == b2.getId()){
                Toast.makeText(v.getContext(), "Delete: " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
            Log.i("test",String.valueOf(skillClickListener));
            skillClickListener.onItemClick(getAdapterPosition(), v);

        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener){
        this.skillClickListener=myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position,View v);
    }
}
