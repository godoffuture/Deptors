package com.example.arek_pc.deptors;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arek_pc.deptors.DataBase.Debts;
import com.example.arek_pc.deptors.DataBase.DebtsViewModel;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Debts> carddebt;

    private CardAdapter_debt mAdapter;

    private RecyclerView mRecyclerView;

    private DebtsViewModel mDebtsViewModel;

    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        carddebt = new ArrayList<>();

        mDebtsViewModel = ViewModelProviders.of(this).get(DebtsViewModel.class);
        mDebtsViewModel.getMyDebts().observe(this, new Observer<List<Debts>>() {

            @Override
            public void onChanged(@Nullable List<Debts> debts) {
                mAdapter.setmDebts(debts);
                mAdapter.setID();
            }
        });
        buildRecyclerView();
        actionbtn();
        drawer();
        navbtn();

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_deptors) {

            mDebtsViewModel = ViewModelProviders.of(this).get(DebtsViewModel.class);
            mDebtsViewModel.getMyDebtors().observe(this, new Observer<List<Debts>>() {

                @Override
                public void onChanged(@Nullable List<Debts> recipes) {
                    mAdapter.setmDebts(recipes);
                }
            });
        } else if (id == R.id.my_depts) {

            mDebtsViewModel = ViewModelProviders.of(this).get(DebtsViewModel.class);
            mDebtsViewModel.getMyDebts().observe(this, new Observer<List<Debts>>() {

                @Override
                public void onChanged(@Nullable List<Debts> recipes) {
                    mAdapter.setmDebts(recipes);
                }
            });
        } else if (id == R.id.all_depts) {

            //carddebt = new ArrayList<>();

            mDebtsViewModel = ViewModelProviders.of(this).get(DebtsViewModel.class);
            mDebtsViewModel.getAllDebts().observe(this, new Observer<List<Debts>>() {

                @Override
                public void onChanged(@Nullable List<Debts> recipes) {
                    mAdapter.setmDebts(recipes);
                }
            });

        } else if (id == R.id.contacts) {
            Intent intent = new Intent (Main.this, Contacts.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.debt_recyclerview);
        mAdapter = new CardAdapter_debt(this, carddebt);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(new CardAdapter_debt.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onStatusClick(int id) {
                statusDialog(id);
            }

            @Override
            public void onDeleteClick(int position) {

            }

            @Override
            public void onOptionClick(int position) {

            }
        });
    }


    private void addDebtDialog(){
        LayoutInflater inflater = LayoutInflater.from(Main.this);
        View view = inflater.inflate(R.layout.dialog_fragment, null);
        final EditText name = (EditText)view.findViewById(R.id.Name_dialog);
        final EditText description = (EditText)view.findViewById(R.id.Description_dialog);
        final EditText value = (EditText)view.findViewById(R.id.Value_dialog);
        final CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox);
        final Spinner sp = (Spinner)view.findViewById(R.id.currency_spinner);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nowy wpis");
        builder.setView(view);
        spinner(view);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!name.getText().toString().isEmpty() &&
                        !description.getText().toString().isEmpty() &&
                        !value.getText().toString().isEmpty()){

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                    String str = mdformat.format(calendar.getTime());

                    Debts debt = new Debts(name.getText().toString(),"",
                            description.getText().toString(),
                            Integer.parseInt(value.getText().toString()),
                            sp.getSelectedItem().toString(),str,1,
                            checked(cb));
                    mDebtsViewModel.insertDebts(debt);

                } else { Toast.makeText(getApplicationContext(),"Uzupełnij wszystkie luki",Toast.LENGTH_SHORT).show(); }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Main.this, "Anulowano wpisywanie", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    private void statusDialog(final int id){
        AlertDialog.Builder ADB = new AlertDialog.Builder(Main.this);
        ADB.setTitle("Opłata długu");
        ADB.setMessage("Czy chcesz aby dług zmienił status opłacenia?");
        ADB.setPositiveButton("tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Main.this, "Opłacono", Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                String end_date = mdformat.format(calendar.getTime());
                mDebtsViewModel.updateDebt(id,end_date);
            }
        });
        ADB.setNegativeButton("nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ADB.create().show();
    }

    void spinner(View v){
        spinner = (Spinner) v.findViewById(R.id.currency_spinner);

        ArrayAdapter<CharSequence> currency= ArrayAdapter.createFromResource(this,R.array.currency,android.R.layout.simple_spinner_item);
        currency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(currency);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textview = (TextView) view;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    int checked(CheckBox cb){
        if(cb.isChecked()) return 1;
        else return 0;
    }

    void actionbtn(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_dept);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDebtDialog();
            }
        });
    }

    void navbtn(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    void drawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
}
