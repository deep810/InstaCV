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

import activity.FormExcr;
import database.CurrInfo;
import database.EduInfo;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 23/3/16.
 */
public class ExcrRecyclerViewAdapter extends RecyclerView.Adapter<ExcrRecyclerViewAdapter.DataObjectHolder>{

    private static String LOG_TAG = "ExcrRecyclerViewAdapter";
    private List<CurrInfo> excrDataset;
    private static MyClickListener excrClickListener;

    public ExcrRecyclerViewAdapter(List<CurrInfo> dataSet) {
        excrDataset=dataSet;
    }

    @Override
    public ExcrRecyclerViewAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.excr_card_view,parent,false);
        DataObjectHolder dataObjectHolder=new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.t1.setText(excrDataset.get(position).get_name());
        holder.item_id = excrDataset.get(position).get_id();
        holder.extracurr_id = excrDataset.get(position).get_currid();
    }



    @Override
    public int getItemCount() {
        return excrDataset.size();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1;
//        Button b1, b2;
        int item_id;
        int extracurr_id;
//        private MyClickListener eduClickListener;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            label=(TextView)itemView.findViewById(R.id.home_card_1);
            t1 = (TextView) itemView.findViewById(R.id.excr_card_1);

//            b1 = (Button) itemView.findViewById(R.id.excreb);
//            b2 = (Button) itemView.findViewById(R.id.excrdb);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
//            b1.setOnClickListener(this);
//            b2.setOnClickListener(this);

            final View root = itemView;
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("ExtraCurricular Activities");

                    // Set up the input
                    final EditText excra = new EditText(v.getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    excra.setInputType(InputType.TYPE_CLASS_TEXT);
                    excra.setText(t1.getText());



                    LinearLayout ll=new LinearLayout(v.getContext());
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.addView(excra);



                    builder.setView(ll);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String ext = excra.getText().toString();


                            PInfoDbHandler db = new PInfoDbHandler(root.getContext(),"",null,1);
                            CurrInfo c=new CurrInfo(item_id,extracurr_id,ext);
                            db.updateCInfo(c);
                            Intent i = new Intent(root.getContext(), FormExcr.class);
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

            excrClickListener.onItemClick(getAdapterPosition(), v);

        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener){
        this.excrClickListener=myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position,View v);
    }


}
