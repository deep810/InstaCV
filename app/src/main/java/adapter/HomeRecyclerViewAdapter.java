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

import java.util.ArrayList;
import java.util.List;

import database.ItemStatus;

/**
 * Created by vishwashrisairm on 21/3/16.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.DataObjectHolder>{
    private static String LOG_TAG="HomeRecyclerViewAdapter";
    private List<ItemStatus> hDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1,t2;
        Button b1,b2;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            label=(TextView)itemView.findViewById(R.id.home_card_1);
            t1=(TextView)itemView.findViewById(R.id.home_card_1);
            t2=(TextView)itemView.findViewById(R.id.home_card_2);
            b1=(Button)itemView.findViewById(R.id.heb);
            b2=(Button)itemView.findViewById(R.id.hdb);

            Log.i(LOG_TAG,"Adding Listener");
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

            myClickListener.onItemClick(getAdapterPosition(),v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener){
        this.myClickListener=myClickListener;
    }

    public HomeRecyclerViewAdapter(List<ItemStatus> myDataSet){
        hDataset=myDataSet;
    }


    @Override
    public HomeRecyclerViewAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_view,parent,false);
        DataObjectHolder dataObjectHolder=new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(HomeRecyclerViewAdapter.DataObjectHolder holder, int position) {
        holder.t1.setText(hDataset.get(position).getTitle());
//        holder.t2.setText(hDataset.get(position).get_item_id());

    }

    @Override
    public int getItemCount() {
        return hDataset.size();
    }

    public  void addItem(ItemStatus item,int index){
        hDataset.add(index,item);
        notifyItemInserted(index);
    }

    public void deleteItem(int index){
        hDataset.remove(index);
        notifyItemRemoved(index);
    }


    public interface MyClickListener{
        public void onItemClick(int position,View v);
    }

}
