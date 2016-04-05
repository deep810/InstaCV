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
import java.sql.Ref;
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
        final PersonalInfo per=db.getPInfo(item_id);

       // eduItemRecyclerView.setLayoutParams(params);
        //proItemRecyclerView.setLayoutParams(params);
       // skillItemRecyclerView.setLayoutParams(params);
        //excrItemRecyclerView.setLayoutParams(params);
        //refItemRecyclerView.setLayoutParams(params);

        toolbar=(Toolbar)findViewById(R.id.item_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("My CV");

//        Edit Buttons
//        Button btn1=(Button)findViewById(R.id.item_per_button);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(ItemActivity.this,FormPersonal.class);
//                i.putExtra("item_id",item_id);
//                startActivity(i);
//            }
//        });

        Button btn2=(Button)findViewById(R.id.item_edu_button);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemActivity.this,FormEdu.class);
                i.putExtra("item_id",item_id);
                startActivity(i);
            }
        });

        Button btn3=(Button)findViewById(R.id.item_skill_button);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemActivity.this,FormSkill.class);
                i.putExtra("item_id",item_id);
                startActivity(i);
            }
        });

        Button btn4=(Button)findViewById(R.id.item_pro_button);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemActivity.this,FormPro.class);
                i.putExtra("item_id",item_id);
                startActivity(i);
            }
        });

        Button btn5=(Button)findViewById(R.id.item_excra_button);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemActivity.this,FormExcr.class);
                i.putExtra("item_id",item_id);
                startActivity(i);
            }
        });

        Button btn6=(Button)findViewById(R.id.item_ref_button);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemActivity.this,FormRef.class);
                i.putExtra("item_id",item_id);
                startActivity(i);
            }
        });




        ImageButton pre=(ImageButton)findViewById(R.id.button_preview);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"Template1"," Template2 "," Template3"," Template4"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
                builder.setTitle("Choose a template");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        String path;

                        switch(item)
                        {
                            case 0:
                                try {
                                    path = Environment.getExternalStorageDirectory().toString()+"/InstaCV/"+per.get_fullname()+"_"+(item+1)+".pdf";
                                    File file = new File(path);
                                    file.getParentFile().mkdirs();
                                    createPdf(path,0);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 1:
                                try {
                                     path = Environment.getExternalStorageDirectory().toString()+"/InstaCV/"+per.get_fullname()+"_"+(item+1)+".pdf";
                                    File file = new File(path);
                                    file.getParentFile().mkdirs();
                                    createPdf(path,1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                try {
                                     path = Environment.getExternalStorageDirectory().toString()+"/InstaCV/"+per.get_fullname()+"_"+(item+1)+".pdf";
                                    File file = new File(path);
                                    file.getParentFile().mkdirs();
                                    createPdf(path,2);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 3:
                                try {
                                     path = Environment.getExternalStorageDirectory().toString()+"/InstaCV/"+per.get_fullname()+"_"+(item+1)+".pdf";
                                    File file = new File(path);
                                    file.getParentFile().mkdirs();
                                    createPdf(path,3);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (DocumentException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 4:
                                try {
                                    path = Environment.getExternalStorageDirectory().toString()+"/InstaCV/"+per.get_fullname()+"_"+(item+1)+".pdf";
                                    File file = new File(path);
                                    file.getParentFile().mkdirs();
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
                        path = Environment.getExternalStorageDirectory().toString()+"/InstaCV/"+per.get_fullname()+"_"+(item+1)+".pdf";

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
        PersonalInfo p=db.getPInfo(item_id);
        List<EduInfo> edu=db.getAllEInfoByID(item_id);
        List<CurrInfo>c=db.getAllCInfoById(item_id);
        List<SkillsInfo>s=db.getAllSInfoById(item_id);
        List<ProjectInfo>pr=db.getAllPRInfoById(item_id);
        List<RefInfo>r=db.getAllRInfoById(item_id);

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
                html="<body style=\"border:0;font:inherit;font-size:100%;margin:0;padding:0;vertical-align:baseline;\">" +
                        "<div id=\"cv\" class=\"instaFade\" style=\"width: 100%;max-width: 800px;background: #f3f3f3;margin: 30px auto;\">";

                per_info="<div class=\"mainDetails\" style=\"padding: 0px 35px;\tborder-bottom: 2px solid #cf8a05;\tbackground: #ededed;\">" +
                        "<div id=\"name\" style=\"float: left;\">" +
                        "<h1 style=\"font-size: 2em;font-weight: 700;font-family: 'Rokkitt', Helvetica, Arial, sans-serif;margin-bottom: -6px;\">"+p.get_fullname()+"</h1>" +
                        "<h2 style=\"font-size: 1.5em;margin-left: 2px;font-family: 'Rokkitt', Helvetica, Arial, sans-serif;\">Student(Job Title)/"+p.get_dob()+"</h2>" +
                        "</div>" +
                        "<div id=\"contactDetails\" style=\"float: right;margin-top:25px;\">" +
                        "<ul style=\"list-style-type: none;font-size: 0.9em;margin-top: 2px;\">" +
                        "<li style=\"margin-bottom: 3px;\tcolor: #444;\">e: "+p.get_email()+"</li>" +
                        "<li style=\"margin-bottom: 3px;\tcolor: #444;\">m: "+p.get_contact()+"</li>" +
                        "</ul>" +
                        "</div>" +
                        "<div class=\"clear\" style=\"clear: both;\"></div>" +
                        "</div>" +
                        "<div id=\"mainArea\" style=\"padding: 15px 35px;border-bottom: 1px solid #cf8a05;background: #ededed;\">" +

                        "<!--Career objective-->" +
                        "<section style=\"border-top: 1px solid #dedede;padding: 0px 0 0;\">" +
                        "<article>" +
                        "<div class=\"sectionTitle\" style=\"float: left;width: 25%;\">" +
                        "<h1 style=\"font-family: 'Rokkitt', Helvetica, Arial, sans-serif;font-style: italic;\tfont-size: 1.3em;color: #cf8a05;\">Personal Profile</h1>" +
                        "</div>" +
                        "<div class=\"sectionContent\" style=\"float:right;width: 100%;\">" +
                        "<p>"+p.get_objective()+"</p>\n" +
                        "</div>" +
                        "</article>" +
                        "<div class=\"clear\" style=\"clear:both;\"></div>" +
                        "</section>";
                html+=per_info;


                exper_info="<section style=\"border-top: 1px solid #dedede;padding: 0px 0 0;\">\n" +
                        "<article>" +
                        "<div class=\"sectionTitle\" style=\"float: left;width: 25%;\">" +
                        "<h1 style=\"font-family: 'Rokkitt', Helvetica, Arial, sans-serif;font-style: italic;\tfont-size: 1.3em;color: #cf8a05;\">Work experience</h1>" +
                        "</div>" +

                        "<div class=\"sectionContent\" style=\"float:right;width:100%;\">";
                for(ProjectInfo item:pr) {
                    exper_info += "<article>" +
                            "<h2 style=\"font-family: 'Rokkitt', Helvetica, Arial, sans-serif;font-size: 1.1em;margin-bottom: -2px;\">" + item.get_title() + "," + item.get_desig() + "</h2>" +
                            "<p class=\"subDetails\" style=\"font-size: 0.8em;\tfont-style: italic;\tmargin-bottom: 3px;\">" + item.get_time() + "</p>\n" +
                            "<p>" + item.get_desc() + "</p>\n" +
                            "</article>";
                }

                exper_info+="</div>" +
                        "</article>" +
                        "<div class=\"clear\" style=\"clear:both;\"></div>" +

                        "</section>";
                html+=exper_info;

                skill_info="<section style=\"border-top: 1px solid #dedede;padding: 0px 0 0;\">\n" +
                        "<article>\n" +
                        "<div class=\"sectionTitle\" style=\"float: left;width: 25%;\">\n" +
                        "<h1 style=\"font-family: 'Rokkitt', Helvetica, Arial, sans-serif;font-style: italic;\tfont-size: 1.3em;color: #cf8a05;\">Key Skills</h1>\n" +
                        "</div>\n" +
                        "<div class=\"sectionContent\" style=\"float:right;width: 100%;\">\n" +
                        "<ul class=\"keySkills\" style=\"list-style-type: none;\t-moz-column-count:3;-webkit-column-count:3;\tcolumn-count:3;\n" +
                        "margin-bottom: 20px;font-size: 1em;\tcolor: #444;\">" ;
                for(SkillsInfo item:s)
                    skill_info+="<li>"+item.get_nameofskill()+"</li>";

                skill_info+="</ul>" +
                        "</div>" +
                        "</article>" +
                        "<div class=\"clear\" style=\"clear:both;\"></div>" +

                        "</section>";
                html+=skill_info;


                edu_info="<section style=\"border-top: 1px solid #dedede;padding: 0px 0 0;\">\n" +
                        "<article>\n" +
                        "<div class=\"sectionTitle\" style=\"float: left;width: 25%;\">\n" +
                        "<h1 style=\"font-family: 'Rokkitt', Helvetica, Arial, sans-serif;font-style: italic;\tfont-size: 1.3em;color: #cf8a05;\">Education Information</h1>\n" +
                        "</div>\n" +
                        "<div class=\"sectionContent\" style=\"float:right;width: 100%;\">\n";

                for(EduInfo item:edu) {
                    edu_info += "<article>\n" +

                            "<h2 style=\"font-family: 'Rokkitt', Helvetica, Arial, sans-serif;font-size: 1.1em;margin-bottom: -2px;\">"+item.get_degree()+"</h2> <div style=\"float:right;\">"+item.get_yop()+"</div>\n" +
                            "<p class=\"subDetails\" style=\"font-size: 0.8em;\tfont-style: italic;\tmargin-bottom: 3px;\">"+item.get_institute()+"</p>\n" +
                            "<p>Marks:-"+item.get_cgpa()+"</p>\n" +
                            "</article>\n";
                }

                edu_info+="</div>\n" +
                        "</article>\n" +
                        "<div class=\"clear\" style=\"clear:both;\"></div>\n" +
                        "</section>\t";
                html+=edu_info;

                excra_info="<section style=\"border-top: 1px solid #dedede;padding: 0px 0 0;\">\n" +
                        "<article>\n" +
                        "<div class=\"sectionTitle\" style=\"float: left;width: 25%;\">\n" +
                        "<h1 style=\"font-family: 'Rokkitt', Helvetica, Arial, sans-serif;font-style: italic;\tfont-size: 1.3em;color: #cf8a05;\">Extracurricular Activities</h1>" +
                        "</div>" +
                        "<div class=\"sectionContent\" style=\"float:right;width:100%;\">" +
                        "<ul class=\"keySkills\" style=\"list-style-type: none;\t-moz-column-count:1;-webkit-column-count:1;\tcolumn-count:1;\n" +
                        "margin-bottom: 20px;font-size: 1em;\tcolor: #444;\">" ;
                for(CurrInfo item:c) {
                    excra_info += "<li>"+item.get_name()+"</li>";
                }
                excra_info+= "</ul>" +
                        "</div>" +
                        "</article>" +
                        "<div class=\"clear\" style=\"clear:both;\"></div>" +
                        "</section>";
                html+=excra_info;

                ref_info="<section style=\"border-top: 1px solid #dedede;padding: 0px 0 0;\">\n" +
                        "<article>" +
                        "<div class=\"sectionTitle\" style=\"float: left;width: 25%;\">" +
                        "<h1 style=\"font-family: 'Rokkitt', Helvetica, Arial, sans-serif;font-style: italic;\tfont-size: 1.3em;color: #cf8a05;\">References</h1>\n" +
                        "</div>" +
                        "<div class=\"sectionContent\" style=\"float:right;width:100%;\">" ;
                for(RefInfo item:r) {
                    ref_info += "<article>" +
                            "<h2 style=\"font-family: 'Rokkitt', Helvetica, Arial, sans-serif;font-size: 1.1em;margin-bottom: -2px;\">"+item.get_rname()+"</h2>\n" +
                            "<p class=\"subDetails\" style=\"font-size: 1em;margin-bottom: 3px;\">"+item.get_pos()+","+item.get_org()+" </p>\n" +
                            "<p>"+item.get_contact()+"</p>" +
                            "</article>";
                }
                 ref_info+="</div>" +
                        "</article>" +
                        "<div class=\"clear\" style=\"clear:both;\"></div>" +
                        "</section>";
                html+=ref_info;

                html+="</div>" +
                        "</div>" +
                        "</body>";

                break;
            case 2:
                html="<body>" +
                        "<h1>Template3</h1>" +
                        "</body>";
                break;
            case 3:
                html="";
                break;
            case 4:
                html="<body>" +
                        "<h1>Template4</h1>" +
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
