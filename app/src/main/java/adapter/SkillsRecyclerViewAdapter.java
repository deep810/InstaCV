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

import activity.FormSkill;
import database.EduInfo;
import database.ItemStatus;
import database.SkillsInfo;
import helper.PInfoDbHandler;

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
        holder.item_id = skillDataset.get(position).get_id();
        holder.skill_id = skillDataset.get(position).get_skillid();


    }

    @Override
    public int getItemCount() {
        return skillDataset.size();
    }



    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1, t2;
//        Button b1, b2;
        int skill_id,item_id;
//        private MyClickListener eduClickListener;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            label=(TextView)itemView.findViewById(R.id.home_card_1);
            t1 = (TextView) itemView.findViewById(R.id.skill_card_1);
            t2 = (TextView) itemView.findViewById(R.id.skill_card_2);
            //t3 = (TextView) itemView.findViewById(R.id.edu_card_3);
            //t4 = (TextView) itemView.findViewById(R.id.edu_card_4);
//            b1 = (Button) itemView.findViewById(R.id.eeb);
//            b2 = (Button) itemView.findViewById(R.id.edb);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
//            b1.setOnClickListener(this);
//            b2.setOnClickListener(this);

            final View root = itemView;
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Skills Information");

                    // Set up the input
                    final EditText skill_name = new EditText(v.getContext());
                    final EditText  proficiency= new EditText(v.getContext());
                    //  final EditText cgpa = new EditText(FormEdu.this);
                    // final EditText institute = new EditText(FormEdu.this);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    skill_name.setInputType(InputType.TYPE_CLASS_TEXT);
                    skill_name.setText(t1.getText());

                    proficiency.setInputType(InputType.TYPE_CLASS_TEXT);
                    proficiency.setText(t2.getText());

                    //cgpa.setInputType(InputType.TYPE_CLASS_TEXT);
                    //cgpa.setHint("CGPA/%age");
                    //institute.setInputType(InputType.TYPE_CLASS_TEXT);
                    // institute.setHint("Institute");

                    LinearLayout ll=new LinearLayout(v.getContext());
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.addView(skill_name);
                    ll.addView(proficiency);
                    //ll.addView(cgpa);
                    //ll.addView(institute);


                    builder.setView(ll);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String skill = skill_name.getText().toString();
                            String prof = proficiency.getText().toString();
                            //String cg = cgpa.getText().toString();
                            //String ins = institute.getText().toString();


                            PInfoDbHandler db = new PInfoDbHandler(root.getContext(),"",null,1);
                            SkillsInfo e=new SkillsInfo(item_id,skill_id, prof, skill);
                            db.updateSInfo(e);
                            Intent i = new Intent(root.getContext(), FormSkill.class);
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
//            if (v.getId() == b1.getId()){
//                Toast.makeText(v.getContext(), "Edit: " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
//            } else if(v.getId() == b2.getId()){
//                Toast.makeText(v.getContext(), "Delete: " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
//            }
//            Log.i("test",String.valueOf(skillClickListener));
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
