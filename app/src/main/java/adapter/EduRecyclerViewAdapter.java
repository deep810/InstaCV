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

import activity.FormEdu;
import database.EduInfo;
import database.ItemStatus;
import database.SkillsInfo;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 22/3/16.
 */
public class EduRecyclerViewAdapter extends RecyclerView.Adapter<EduRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "EduRecyclerViewAdapter";
    private List<EduInfo> eduDataset;
    private static MyClickListener eduClickListener;

    public EduRecyclerViewAdapter(List<EduInfo> dataSet) {
        eduDataset=dataSet;
    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.edu_card_view,parent,false);
        DataObjectHolder dataObjectHolder=new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.t1.setText(eduDataset.get(position).get_degree());
        holder.t2.setText(eduDataset.get(position).get_yop());
        holder.t3.setText(eduDataset.get(position).get_cgpa());
        holder.t4.setText(eduDataset.get(position).get_institute());
        holder.edu_id = eduDataset.get(position).get_eduid();
        holder.item_id = eduDataset.get(position).get_id();
    }

    @Override
    public int getItemCount() {
        return eduDataset.size();
    }



    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1, t2, t3, t4;
        Button b1, b2;
        int edu_id;
        int item_id;
//        private MyClickListener eduClickListener;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            label=(TextView)itemView.findViewById(R.id.home_card_1);
            t1 = (TextView) itemView.findViewById(R.id.edu_card_1);
            t2 = (TextView) itemView.findViewById(R.id.edu_card_2);
            t3 = (TextView) itemView.findViewById(R.id.edu_card_3);
            t4 = (TextView) itemView.findViewById(R.id.edu_card_4);
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
                    builder.setTitle("Education Information");

                    // Set up the input
                    final EditText edu_name = new EditText(v.getContext());
                    final EditText  yop= new EditText(v.getContext());
                    final EditText cgpa = new EditText(v.getContext());
                    final EditText institute = new EditText(v.getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    edu_name.setInputType(InputType.TYPE_CLASS_TEXT);
                    edu_name.setText(t1.getText());
                    //edu_name.setHint("Examination");
                    yop.setInputType(InputType.TYPE_CLASS_TEXT);
                    yop.setText(t2.getText());
                    //yop.setHint("Year of Passing");
                    cgpa.setInputType(InputType.TYPE_CLASS_TEXT);
                    cgpa.setText(t3.getText());
                    //cgpa.setHint("CGPA/%age");
                    institute.setInputType(InputType.TYPE_CLASS_TEXT);
                    institute.setText(t4.getText());
                    //institute.setHint("Institute");

                    LinearLayout ll=new LinearLayout(v.getContext());
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.addView(edu_name);
                    ll.addView(yop);
                    ll.addView(cgpa);
                    ll.addView(institute);




                    builder.setView(ll);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String edu = edu_name.getText().toString();
                            String year = yop.getText().toString();
                            String cg = cgpa.getText().toString();
                            String ins = institute.getText().toString();


                            PInfoDbHandler db = new PInfoDbHandler(root.getContext(),"",null,1);
                            EduInfo e=new EduInfo(item_id,edu_id, edu, year, cg, ins);
                            db.updateEInfo(e);
                            Intent i = new Intent(root.getContext(), FormEdu.class);
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
            Log.i("test",String.valueOf(eduClickListener));
            eduClickListener.onItemClick(getAdapterPosition(), v);

        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener){
        this.eduClickListener=myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position,View v);
    }
}
