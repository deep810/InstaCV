package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.example.vishwashrisairm.materialdesign.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by vishwashrisairm on 13/3/16.
 */
public class SampleCVsFragment extends Fragment {
    public SampleCVsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_samplecvs, container, false);

        Button btn1=(Button)rootView.findViewById(R.id.test1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyReadAssets(1);

            }
        });

        Button btn2=(Button)rootView.findViewById(R.id.test2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyReadAssets(2);

            }
        });

        Button btn3=(Button)rootView.findViewById(R.id.test3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyReadAssets(3);

            }
        });

        Button btn4=(Button)rootView.findViewById(R.id.test4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyReadAssets(4);

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void CopyReadAssets(int status) {
        AssetManager assetManager = getActivity().getAssets();

        String f="";
        switch(status){
            case 1:
                f="temp1.pdf";
                break;
            case 2:
                f="temp2.pdf";
                break;
            case 3:
                f="temp3.pdf";
                break;
            case 4:
                f="temp4.pdf";
                break;
        }

        InputStream in = null;
        OutputStream out = null;
        File file = new File(getActivity().getFilesDir(),f);

        try
        {
            in = assetManager.open(f);
            out = getActivity().openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getActivity().getFilesDir() + "/"+f),
                "application/pdf");

        startActivity(intent);

    }

    private void copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
