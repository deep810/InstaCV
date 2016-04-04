package activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.vishwashrisairm.materialdesign.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.EduRecyclerViewAdapter;
import adapter.ExcrRecyclerViewAdapter;
import adapter.PerRecyvlerViewAdapter;
import adapter.ProRecyclerViewAdapter;
import adapter.RefRecyclerViewAdapter;
import adapter.SkillsRecyclerViewAdapter;
import database.CurrInfo;
import database.EduInfo;
import database.PersonalInfo;
import database.ProjectInfo;
import database.RefInfo;
import database.SkillsInfo;
import helper.PInfoDbHandler;

/**
 * Created by vishwashrisairm on 24/3/16.
 */
public class ItemActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView perItemRecyclerView;
    private RecyclerView.Adapter perItemAdapter;
    private RecyclerView.LayoutManager perItemLayoutManager;

    private RecyclerView eduItemRecyclerView;
    private RecyclerView.Adapter eduItemAdapter;
    private RecyclerView.LayoutManager eduItemLayoutManager;

    private RecyclerView proItemRecyclerView;
    private RecyclerView.Adapter proItemAdapter;
    private RecyclerView.LayoutManager proItemLayoutManager;

    private RecyclerView skillItemRecyclerView;
    private RecyclerView.Adapter skillItemAdapter;
    private RecyclerView.LayoutManager skillItemLayoutManager;

    private RecyclerView excrItemRecyclerView;
    private RecyclerView.Adapter excrItemAdapter;
    private RecyclerView.LayoutManager excrItemLayoutManager;

    private RecyclerView refItemRecyclerView;
    private RecyclerView.Adapter refItemAdapter;
    private RecyclerView.LayoutManager refItemLayoutManager;

    AlertDialog levelDialog;

    private int item_id;


    //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);

        item_id=getIntent().getIntExtra("item_id",0);

        PInfoDbHandler db = new PInfoDbHandler(this,"",null,1);

       // eduItemRecyclerView.setLayoutParams(params);
        //proItemRecyclerView.setLayoutParams(params);
       // skillItemRecyclerView.setLayoutParams(params);
        //excrItemRecyclerView.setLayoutParams(params);
        //refItemRecyclerView.setLayoutParams(params);

        toolbar=(Toolbar)findViewById(R.id.item_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("My CV");

        ImageButton pre=(ImageButton)findViewById(R.id.button_preview);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Template1"," Template2 "," Template3"," Template4"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
                builder.setTitle("Choose a template");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        String path = Environment.getExternalStorageDirectory().toString()+"/test.pdf";
                        File file = new File(path);
                        file.getParentFile().mkdirs();
                        switch(item)
                        {
                            case 0:
                                try {
                                    createPdf(path,0);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 1:
                                try {
                                    createPdf(path,1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                try {
                                    createPdf(path,2);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 3:
                                try {
                                    createPdf(path,3);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 4:
                                try {
                                    createPdf(path,4);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                }
                                break;

                        }
                        levelDialog.dismiss();

//                        Open pdf file
                        File pdfFile = new File(path);//File path
                        if (pdfFile.exists()) //Checking for the file is exist or not
                        {
                            Uri path1 = Uri.fromFile(pdfFile);
                            Intent objIntent = new Intent(Intent.ACTION_VIEW);
                            objIntent.setDataAndType(path1, "application/pdf");
                            objIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(objIntent);//Staring the pdf viewer
                        } else {

                            Toast.makeText(ItemActivity.this, "The file not exists! ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                levelDialog = builder.create();
                levelDialog.show();



            }
        });


        perItemRecyclerView = (RecyclerView) findViewById(R.id.perItemRecyclerView);
        perItemRecyclerView.setHasFixedSize(false);
        perItemLayoutManager = new LinearLayoutManager(this);
        perItemRecyclerView.setLayoutManager(perItemLayoutManager);
        perItemAdapter = new PerRecyvlerViewAdapter(db.getAllPInfoById(item_id));
        perItemRecyclerView.setAdapter(perItemAdapter);

        eduItemRecyclerView=(RecyclerView)findViewById(R.id.eduItemRecyclerView);
        eduItemRecyclerView.setHasFixedSize(true);
        eduItemLayoutManager=new LinearLayoutManager(this);
        eduItemRecyclerView.setLayoutManager(eduItemLayoutManager);
//        mItems=getDataSet();
        eduItemAdapter=new EduRecyclerViewAdapter(db.getAllEInfoByID(item_id));
        eduItemRecyclerView.setAdapter(eduItemAdapter);

        proItemRecyclerView=(RecyclerView)findViewById(R.id.proItemRecyclerView);
        proItemRecyclerView.setHasFixedSize(true);
        proItemLayoutManager=new LinearLayoutManager(this);
        proItemRecyclerView.setLayoutManager(proItemLayoutManager);
//        mItems=getDataSet();
        proItemAdapter=new ProRecyclerViewAdapter(db.getAllPRInfoById(item_id));
        proItemRecyclerView.setAdapter(proItemAdapter);

        skillItemRecyclerView=(RecyclerView)findViewById(R.id.skillItemRecyclerView);
        skillItemRecyclerView.setHasFixedSize(true);
        skillItemLayoutManager=new LinearLayoutManager(this);
        skillItemRecyclerView.setLayoutManager(skillItemLayoutManager);
//        mItems=getDataSet();
        skillItemAdapter=new SkillsRecyclerViewAdapter(db.getAllSInfoById(item_id));
        skillItemRecyclerView.setAdapter(skillItemAdapter);

        excrItemRecyclerView=(RecyclerView)findViewById(R.id.excrItemRecyclerView);
        excrItemRecyclerView.setHasFixedSize(true);
        excrItemLayoutManager=new LinearLayoutManager(this);
        excrItemRecyclerView.setLayoutManager(excrItemLayoutManager);
//        mItems=getDataSet();
        excrItemAdapter=new ExcrRecyclerViewAdapter(db.getAllCInfoById(item_id));
        excrItemRecyclerView.setAdapter(excrItemAdapter);

        refItemRecyclerView=(RecyclerView)findViewById(R.id.refItemRecyclerView);
        refItemRecyclerView.setHasFixedSize(true);
        refItemLayoutManager=new LinearLayoutManager(this);
        refItemRecyclerView.setLayoutManager(refItemLayoutManager);
//        mItems=getDataSet();
        refItemAdapter=new RefRecyclerViewAdapter(db.getAllRInfoById(item_id));
        refItemRecyclerView.setAdapter(refItemAdapter);


    }


    private void createPdf(String file,int choice) throws IOException, DocumentException {
        PInfoDbHandler db = new PInfoDbHandler(ItemActivity.this,"",null,1);
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

        String html ="",per_info,edu_info,skill_info,exper_info,excra_info,ref_info;

        switch(choice){
            case 0:
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

                break;
            case 1:
                html="<body>" +
                        "<h1>Template2</h1>" +
                        "</body>";
//                per_info="";
//                html+=per_info;
//                edu_info="";
//                html+=edu_info;
//                skill_info="";
//                html+=skill_info;
//                exper_info="";
//                html+=exper_info;
//                excra_info="";
//                html+=excra_info;
//                ref_info="";
//                html+=ref_info;



                break;
            case 2:
                html="<body>" +
                        "<h1>Template3</h1>" +
                        "</body>";
                break;
            case 3:
                html="<body>" +
                        "<h1>Template4</h1>" +
                        "</body>";
                break;
            case 4:
                html="<body>" +
                        "<h1>Template5</h1>" +
                        "</body>";
                break;
        }


        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
//        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        for (Element e : XMLWorkerHelper.parseToElementList(html, null)) {
            cell.addElement(e);
        }
        table.addCell(cell);
        document.add(table);

        document.close();
        Log.i("test", "instide createpdf function");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_preview:
                Toast.makeText(this, "Preview", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.menuitem2:
//                Toast.makeText(this, "Menu item 2 selected", Toast.LENGTH_SHORT).show();
//                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
