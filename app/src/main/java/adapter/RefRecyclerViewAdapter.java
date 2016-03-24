package adapter;

import android.app.Activity;
import android.app.Notification;
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

import activity.FormRef;
import database.EduInfo;
import database.ItemStatus;
import database.RefInfo;
import database.SkillsInfo;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 22/3/16.
 */
public class RefRecyclerViewAdapter extends RecyclerView.Adapter<RefRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "RefRecyclerViewAdapter";
    private List<RefInfo> refDataset;
    private static MyClickListener refClickListener;

    public RefRecyclerViewAdapter(List<RefInfo> dataSet) {
        refDataset=dataSet;
    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ref_card_view,parent,false);
        DataObjectHolder dataObjectHolder=new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.t1.setText(refDataset.get(position).get_rname());
        holder.t2.setText(refDataset.get(position).get_contact());
        holder.t3.setText(refDataset.get(position).get_pos());
        holder.t4.setText(refDataset.get(position).get_org());
        holder.item_id = refDataset.get(position).get_id();
        holder.ref_id = refDataset.get(position).get_refid();

    }

    @Override
    public int getItemCount() {
        return refDataset.size();
    }



    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1, t2, t3, t4;
        Button b1, b2;
        int item_id,ref_id;
//        private MyClickListener eduClickListener;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            label=(TextView)itemView.findViewById(R.id.home_card_1);
            t1 = (TextView) itemView.findViewById(R.id.ref_card_1);
            t2 = (TextView) itemView.findViewById(R.id.ref_card_2);
            t3 = (TextView) itemView.findViewById(R.id.ref_card_3);
            t4 = (TextView) itemView.findViewById(R.id.ref_card_4);
            b1 = (Button) itemView.findViewById(R.id.eeb);
            b2 = (Button) itemView.findViewById(R.id.edb);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
            b1.setOnClickListener(this);
            b2.setOnClickListener(this);


            final View root = itemView;
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("References");

                    // Set up the input
                    final EditText ref_name = new EditText(v.getContext());
                    final EditText  ref_contact= new EditText(v.getContext());
                    final EditText ref_position = new EditText(v.getContext());
                    final EditText ref_org = new EditText(v.getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    ref_name.setInputType(InputType.TYPE_CLASS_TEXT);
                    ref_name.setText(t1.getText());

                    ref_contact.setInputType(InputType.TYPE_CLASS_TEXT);
                    ref_contact.setText(t2.getText());

                    ref_position.setInputType(InputType.TYPE_CLASS_TEXT);
                    ref_position.setText(t3.getText());

                    ref_org.setInputType(InputType.TYPE_CLASS_TEXT);
                    ref_org.setText(t4.getText());

                    LinearLayout ll=new LinearLayout(v.getContext());
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.addView(ref_name);
                    ll.addView(ref_contact);
                    ll.addView(ref_position);
                    ll.addView(ref_org);


                    builder.setView(ll);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String name = ref_name.getText().toString();
                            String contact = ref_contact.getText().toString();
                            String position = ref_position.getText().toString();
                            String org = ref_org.getText().toString();


                            PInfoDbHandler db = new PInfoDbHandler(root.getContext(),"",null,1);
                            RefInfo e=new RefInfo(item_id,ref_id, name, contact, position, org);
                            db.updateRInfo(e);
                            Intent i = new Intent(root.getContext(), FormRef.class);
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
            Log.i("test",String.valueOf(refClickListener));
            refClickListener.onItemClick(getAdapterPosition(), v);

        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener){
        this.refClickListener=myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position,View v);
    }
}
