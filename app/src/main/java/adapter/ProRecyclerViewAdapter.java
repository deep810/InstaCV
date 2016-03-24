package adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishwashrisairm.materialdesign.R;

import java.util.List;

import activity.FormPro;
import database.ProjectInfo;
import helper.PInfoDbHandler;

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
        holder.item_id = proDataset.get(position).get_id();
        holder.pro_id = proDataset.get(position).get_proid();
    }

    @Override
    public int getItemCount() {
        return proDataset.size();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1, t2, t3, t4,t5;
        Button b1, b2;
        int pro_id,item_id;

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

            final View root = itemView;
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Projects/Experience Information");

                    // Set up the input
                    final EditText pro_title = new EditText(v.getContext());
                    final EditText  pro_location=new  EditText(v.getContext());
                    final EditText pro_duration = new EditText(v.getContext());
                    final EditText pro_designation = new EditText(v.getContext());
                    final EditText pro_description = new EditText(v.getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    pro_title.setInputType(InputType.TYPE_CLASS_TEXT);
                    pro_title.setText(t1.getText());

                    pro_location.setInputType(InputType.TYPE_CLASS_TEXT);
                    pro_location.setText(t2.getText());

                    pro_duration.setInputType(InputType.TYPE_CLASS_TEXT);
                    pro_duration.setText(t3.getText());

                    pro_designation.setInputType(InputType.TYPE_CLASS_TEXT);
                    pro_designation.setText(t4.getText());

                    pro_description.setInputType(InputType.TYPE_CLASS_TEXT);
                    pro_description.setText(t5.getText());
                    //pro_description.setHint("Description");

                    LinearLayout ll=new LinearLayout(v.getContext());
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


                            PInfoDbHandler db = new PInfoDbHandler(root.getContext(),"",null,1);
                            ProjectInfo p=new ProjectInfo(item_id,pro_id,title,location,duration,designation,description);
                            db.updatePRInfo(p);
                            Intent i = new Intent(root.getContext(), FormPro.class);
                            i.putExtra("item_id",item_id);
                            root.getContext().startActivity(i);
                            ((Activity)root.getContext()).finish();

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
