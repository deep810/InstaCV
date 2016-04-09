package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vishwashrisairm.materialdesign.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import database.CurrInfo;
import database.EduInfo;
import database.PersonalInfo;
import database.ProjectInfo;
import database.RefInfo;
import database.SkillsInfo;
import helper.PInfoDbHandler;


/**
 * Created by vishwashrisairm on 13/3/16.
 */
public class SampleCVsFragment extends Fragment {

    //    for testting pdf generation
    public static final String DEST = "assets/html_1.pdf";

//    till here

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

        ImageView btn1=(ImageView)rootView.findViewById(R.id.imageView1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyReadAssets(1);

            }
        });

        ImageView btn2=(ImageView)rootView.findViewById(R.id.imageView2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyReadAssets(2);

            }
        });

        ImageView btn3=(ImageView)rootView.findViewById(R.id.imageView3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyReadAssets(3);

            }
        });

        ImageView btn4=(ImageView)rootView.findViewById(R.id.imageView4);
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
            case 5:
                f= "test.pdf";
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
                Uri.parse("file://" + getActivity().getFilesDir() + "/" + f),
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

    private void createPdf(String file) throws IOException, DocumentException{
        PInfoDbHandler db = new PInfoDbHandler(getContext(),"",null,1);
        PersonalInfo p=db.getPInfo(1);
        List<EduInfo> edu=db.getAllEInfoByID(1);
        List<CurrInfo>c=db.getAllCInfoById(1);
        List<SkillsInfo>s=db.getAllSInfoById(1);
        List<ProjectInfo>pr=db.getAllPRInfoById(1);
        List<RefInfo>r=db.getAllRInfoById(1);

        Document document = new Document();
        PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(file));

        document.addAuthor("InstaCV");
        document.addCreationDate();
        document.addProducer();
        document.addCreator("InstaCV");
        document.addTitle("Resume");
        document.setPageSize(PageSize.A4);

        document.open();

        String html,per_info,edu_info,skill_info,exper_info,excra_info,ref_info;

        html="<body>" +
                "<h1 align=\"center\">Resume</h1>";

        per_info="<div id=\"personal_info\">" +
                "<h3>Personal Information</h3>" +
                "<table style=\"border:none\">" +
                "<tr>" +
                "<td>Name</td>" +
                "<td>"+p.get_fullname()+"</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Email</td>" +
                "<td>"+p.get_email()+"</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Address</td>" +
                "<td>"+p.get_address()+"</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Phone no </td>" +
                "<td>"+p.get_contact()+"</td>" +
                "</tr>" +
                "<tr>" +
                "<td>Objectuve </td>" +
                "<td>"+p.get_objective()+"</td>" +
                "</tr>" +
                "</table>" +
                "</div>";
        html+=per_info;

        edu_info="<div id=\"education_info\">" +
                "<h3>Education Info</h3>" +
                "<table style=\"border:none\">" +
                "<tr>" +
                "<th>Exam</th>" +
                "<th>Year of Passing</th>" +
                "<th>Percentage</th>" +
                "<th>Institute</th>" +
                "</tr>" ;
        for(EduInfo item:edu) {
            edu_info += "<tr>" +
                    "<td>"+item.get_degree()+"</td>" +
                    "<td>"+item.get_yop()+"</td>" +
                    "<td>"+item.get_cgpa()+"</td>" +
                    "<td>"+item.get_institute()+"</td>" +
                    "</tr>";
        }
        edu_info+= "</table>" +
                "</div>";

        html+=edu_info;

        skill_info="<div id=\"skills\">" +
                "<h3>Skills</h3>" +
                "<ul>";
        for(SkillsInfo item:s) {
            skill_info += "<li>"+item.get_nameofskill()+"</li>";
        }
        skill_info+="</ul>" +
                "</div>";

        html+=skill_info;

        exper_info="<div id=\"experience_info\">" +
                "<h3>Experience/Projects</h3>";

        for(ProjectInfo item:pr) {
            exper_info += "<div style=\"width:50%;\">" +
                    "<h4>"+item.get_title()+"</h4>" +
                    "<b style=\"float:right;\">"+item.get_location()+"</b>" +
                    "<p>"+item.get_time()+"</p>" +
                    "<p>"+item.get_desig()+"</p>" +
                    "<p>"+item.get_desc()+"</p>" +
                    "</div>";
        }

        exper_info+= "</div>";

        html+=exper_info;

        excra_info="<div id=\"extracurricular_info\">" +
                "<h3>Extracurricular Activities</h3>" +
                "<ul>";
        for(CurrInfo item:c) {
            excra_info += "<li>"+item.get_name()+"</li>";
        }

        excra_info+= "</ul>" +
                "</div>";

        html+=excra_info;

        ref_info="<div id=\"reference_info\">" +
                "<h3>Reference</h3>";
        for(RefInfo item:r) {
            ref_info += "<div>" +
                    "<p>"+item.get_rname()+"</p>" +
                    "<p>"+item.get_pos()+", "+item.get_org()+"</p>" +
                    "<p>Contact:"+item.get_contact()+"</p>" +
                    "</div>";
        }
        ref_info+="</div>";

        html+=ref_info;

        html+="</body>";



        PdfPTable table = new PdfPTable(1);

        PdfPCell cell = new PdfPCell();
        for (Element e : XMLWorkerHelper.parseToElementList(html, null)) {
            cell.addElement(e);
        }
        table.addCell(cell);
        document.add(table);

        document.close();
        Log.i("test","instide createpdf function");
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
