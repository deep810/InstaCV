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

import database.ProjectInfo;

/**
 * Created by vishwashrisairm on 23/3/16.
 */
public class ProRecyclerViewAdapter extends RecyclerView.Adapter<ProRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "ProRecyclerViewAdapter";
    private List<ProjectInfo> proDataset;
    private static MyClickListener proClickListener;

    public ProRecyclerViewAdapter(List<ProjectInfo> dataSet) {
        proDataset=dataSet;
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pro_card_view,parent,false);
        DataObjectHolder dataObjectHolder=new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.t1.setText(proDataset.get(position).get_title());
        holder.t2.setText(proDataset.get(position).get_location());
        holder.t3.setText(proDataset.get(position).get_time());
        holder.t4.setText(proDataset.get(position).get_desig());
        holder.t5.setText(proDataset.get(position).get_desc());
    }

    @Override
    public int getItemCount() {
        return proDataset.size();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1, t2, t3, t4,t5;
        Button b1, b2;

        public DataObjectHolder(View itemView) {
            super(itemView);
            t1 = (TextView) itemView.findViewById(R.id.pro_card_1);
            t2 = (TextView) itemView.findViewById(R.id.pro_card_2);
            t3 = (TextView) itemView.findViewById(R.id.pro_card_3);
            t4 = (TextView) itemView.findViewById(R.id.pro_card_4);
            t5 = (TextView) itemView.findViewById(R.id.pro_card_5);
            b1 = (Button) itemView.findViewById(R.id.preb);
            b2 = (Button) itemView.findViewById(R.id.prdb);

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
            Log.i("test", String.valueOf(proClickListener));
            proClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
    public void setOnItemClickListener(MyClickListener myClickListener){
        this.proClickListener=myClickListener;
    }


    public interface MyClickListener {
        public void onItemClick(int position,View v);
    }
}
