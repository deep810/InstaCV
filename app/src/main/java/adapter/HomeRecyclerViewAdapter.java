package adapter;

import android.content.Context;
import android.content.Intent;
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

import activity.EditProfile;
import activity.ItemActivity;
import database.ItemStatus;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 21/3/16.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.DataObjectHolder>{
    private static String LOG_TAG="HomeRecyclerViewAdapter";
    private List<ItemStatus> hDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1,t2;

        int item_id;

//        Button b1,b2;
       int x;

        public DataObjectHolder(View itemView) {
            super(itemView);
            View root = itemView;
//            label=(TextView)itemView.findViewById(R.id.home_card_1);
            t1=(TextView)itemView.findViewById(R.id.home_card_1);
            t2=(TextView)itemView.findViewById(R.id.home_card_2);
//            b1=(Button)itemView.findViewById(R.id.heb);
//            b2=(Button)itemView.findViewById(R.id.hdb);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
//            b1.setOnClickListener(this);
//            b2.setOnClickListener(this);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Need details of the movie to be sent to the next activity.

                    Intent i = new Intent(v.getContext(), ItemActivity.class);
                    i.putExtra("item_id",item_id);
                   /* Intent intent = new Intent(context, MovieDetail.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("EXTRA_ART_URL", card.getAlbumArtURL());
                    intent.putExtra("EXTRA_NAME", card.getName());
                    intent.putExtra("EXTRA_Synopsis", card.getSynopsis());
                    intent.putExtra("EXTRA_Rating", card.getRating());
                    intent.putExtra("EXTRA_ReleaseDate", card.getReleaseDate());*/
                    v.getContext().startActivity(i);

                    //Toast.makeText(v.getContext(),"Item_ID:"+item_id, Toast.LENGTH_LONG).show();


                }
            });

        }

        @Override
        public void onClick(View v) {
            ItemStatus i = (ItemStatus) v.getTag();
//            if (v.getId() == b1.getId()){
//                //Toast.makeText(v.getContext(), "Edit: " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
//
//            } else if(v.getId() == b2.getId()){
//                Toast.makeText(v.getContext(), "Delete: " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
//            }

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
        holder.item_id = hDataset.get(position).get_item_id();

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
