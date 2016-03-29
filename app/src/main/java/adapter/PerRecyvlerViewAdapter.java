package adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vishwashrisairm.materialdesign.R;

import java.util.List;

import activity.FormPro;
import database.PersonalInfo;
import database.ProjectInfo;
import helper.PInfoDbHandler;

/**
 * Created by darshit on 3/24/16.
 */
public class    PerRecyvlerViewAdapter extends RecyclerView.Adapter<PerRecyvlerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "PerRecyclerViewAdapter";
    private List<PersonalInfo> perDataset;
    private static MyClickListener preClickListener;

    public PerRecyvlerViewAdapter(List<PersonalInfo> Dataset) {
        this.perDataset = Dataset;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView t1,t2,t3,t4,t5,t6;
        int item_id;
        public DataObjectHolder(View itemView) {
            super(itemView);
            t1 = (TextView) itemView.findViewById(R.id.per_card_1);
            t2 = (TextView) itemView.findViewById(R.id.per_card_2);
            t3 = (TextView) itemView.findViewById(R.id.per_card_3);
            t4 = (TextView) itemView.findViewById(R.id.per_card_4);
            t5 = (TextView) itemView.findViewById(R.id.per_card_5);
            t6 = (TextView) itemView.findViewById(R.id.per_card_6);

            itemView.setOnClickListener(this);
            final View root = itemView;
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Personal Information");

                    // Set up the input
                    final EditText per_name = new EditText(v.getContext());
                    final EditText per_email=new  EditText(v.getContext());
                    final EditText per_contact = new EditText(v.getContext());
                    final EditText per_dob = new EditText(v.getContext());
                    final EditText per_objective = new EditText(v.getContext());
                    final EditText per_addr = new EditText(v.getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    per_name.setInputType(InputType.TYPE_CLASS_TEXT);
                    per_name.setText(t1.getText());

                    per_email.setInputType(InputType.TYPE_CLASS_TEXT);
                    per_email.setText(t2.getText());

                    per_contact.setInputType(InputType.TYPE_CLASS_TEXT);
                    per_contact.setText(t3.getText());

                    per_dob.setInputType(InputType.TYPE_CLASS_TEXT);
                    per_dob.setText(t4.getText());

                    per_objective.setInputType(InputType.TYPE_CLASS_TEXT);
                    per_objective.setText(t5.getText());

                    per_addr.setInputType(InputType.TYPE_CLASS_TEXT);
                    per_addr.setText(t5.getText());
                    //pro_description.setHint("Description");

                    LinearLayout ll=new LinearLayout(v.getContext());
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.addView(per_name);
                    ll.addView(per_email);
                    ll.addView(per_contact);
                    ll.addView(per_dob);
                    ll.addView(per_objective);
                    ll.addView(per_addr);

                    builder.setView(ll);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            String fname = per_name.getText().toString();
                            String add = per_addr.getText().toString();
                            String dob = per_dob.getText().toString();
                            String cont = per_contact.getText().toString();
                            String email = per_email.getText().toString();
                            String objective = per_objective.getText().toString();



                            PInfoDbHandler db = new PInfoDbHandler(root.getContext(),"",null,1);
                            PersonalInfo p = new PersonalInfo(item_id,fname,add,dob,cont,email,objective);
                            db.updatePInfo(p);
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
        }

    }




    @Override
    public PerRecyvlerViewAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.per_card_view,parent,false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(PerRecyvlerViewAdapter.DataObjectHolder holder, int position) {
        holder.t1.setText(perDataset.get(position).get_fullname());
        holder.t2.setText(perDataset.get(position).get_email());
        holder.t3.setText(perDataset.get(position).get_contact());
        holder.t4.setText(perDataset.get(position).get_dob());
        holder.t5.setText(perDataset.get(position).get_objective());
        holder.t6.setText(perDataset.get(position).get_address());
        holder.item_id = perDataset.get(position).get_id();
    }

    @Override
    public int getItemCount() {
        return perDataset.size();
    }

    public void setOnItemClickListener(MyClickListener myClickListener){
        this.preClickListener=myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position,View v);
    }
}
