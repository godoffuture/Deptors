package com.example.arek_pc.deptors;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    private static final String XML_DATA = "contacts.txt";

    private CardAdapter_contact mAdapter;

    private RecyclerView mRecyclerView;


    final ArrayList<Contact> contactList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        read();
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recycler_view();
        add_new_contact();

    }

    public void add_new_contact(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Contacts.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_new_contact, null);
                final EditText mName = (EditText) mView.findViewById(R.id.new_contact_name);
                final EditText mNumber = (EditText) mView.findViewById(R.id.new_contact_number);
                builder.setView(mView);
                builder.setPositiveButton("dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!mName.getText().toString().isEmpty() && !mNumber.getText().toString().isEmpty()){
                            try {
                                contactList.add(new Contact(mName.getText().toString(), mNumber.getText().toString()));
                                Toast.makeText(getApplicationContext(),"pomyslnie oddano nowy kontakt",Toast.LENGTH_SHORT).show();
                                write();
                            }catch (Exception a){
                                Toast.makeText(getApplicationContext(),a.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"uzupełnij wszystkie pola",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Anulowano wpisywanie", Toast.LENGTH_LONG).show();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void recycler_view(){
        mRecyclerView = findViewById(R.id.contact_recyclerview);
        mAdapter = new CardAdapter_contact(getApplicationContext(), contactList);
        mAdapter.setmDebts(contactList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void write() {

            try {
                File file = new File(getFilesDir(),XML_DATA);
                file.createNewFile();
                FileOutputStream fileos = new FileOutputStream(file);
                XmlSerializer xmlSerializer = Xml.newSerializer();
                StringWriter writer = new StringWriter();
                xmlSerializer.setOutput(writer);
                xmlSerializer.startDocument("UTF-8", true);
                xmlSerializer.startTag(null, "userData");

                for(Contact contact:contactList){
                    xmlSerializer.startTag(null, "userName");
                    xmlSerializer.text(contact.getName());
                    xmlSerializer.endTag(null, "userName");
                    xmlSerializer.startTag(null,"password");
                    xmlSerializer.text(contact.getNumber());
                    xmlSerializer.endTag(null, "password");
                }

//                for(int i = 0;i<10;i++){
//                    xmlSerializer.startTag(null, "userName");
//                    xmlSerializer.text("nazwatego: "+i);
//                    xmlSerializer.endTag(null, "userName");
//                    xmlSerializer.startTag(null,"password");
//                    xmlSerializer.text("numertego: "+i);
//                    xmlSerializer.endTag(null, "password");
//                }

                xmlSerializer.endTag(null, "userData");
                xmlSerializer.endDocument();
                xmlSerializer.flush();
                String dataWrite = writer.toString();
                fileos.write(dataWrite.getBytes());
                fileos.close();

            }
            catch (FileNotFoundException e) {
                Log.d("tag","1");
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
                Log.d("tag","2");
                e.printStackTrace();
            }
            catch (IllegalStateException e) {
                Log.d("tag","3");
                e.printStackTrace();
            }
            catch (IOException e) {
                Log.d("tag","4");
                e.printStackTrace();
            }
        }


    public void read() {
            FileInputStream fis;
            InputStreamReader isr;
            char[] inputBuffer;
            String data="";
                try {
                    fis = openFileInput(XML_DATA);
                    isr = new InputStreamReader(fis);
                    inputBuffer = new char[fis.available()];
                    isr.read(inputBuffer);
                    data = new String(inputBuffer);
                    isr.close();
                    fis.close();
                }
                catch (FileNotFoundException e3) {
                    e3.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                XmlPullParserFactory factory = null;

                try {
                    factory = XmlPullParserFactory.newInstance();
                }
                catch (XmlPullParserException e2) {
                    e2.printStackTrace();
                }

                factory.setNamespaceAware(true);

                XmlPullParser xpp = null;

                try {
                    xpp = factory.newPullParser();
                }
                catch (XmlPullParserException e2) {
                    e2.printStackTrace();
                }

                try {
                    xpp.setInput(new StringReader(data));
                }
                catch (XmlPullParserException e1) {
                    e1.printStackTrace();
                }

                int eventType = 0;

                try {
                    eventType = xpp.getEventType();
                }
                catch (XmlPullParserException e1) {
                    e1.printStackTrace();
                }

                ArrayList<String> datalist = new ArrayList<>();
                while (eventType != XmlPullParser.END_DOCUMENT){
                    if(eventType == XmlPullParser.TEXT) {
                        datalist.add(xpp.getText());
                    }
                    try {
                        eventType = xpp.next();
                    }
                    catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                for(int i=0;i<datalist.size();i++)
                    contactList.add(new Contact(datalist.get(i++),datalist.get(i)));
                }

}
class Contact{
    private String name;
    private String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
