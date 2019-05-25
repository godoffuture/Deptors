package com.example.arek_pc.deptors.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "Debts_table")
public class Debts {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private int id;

    @NonNull
    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "contact")
    private String contact;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    @NonNull
    @ColumnInfo(name = "value")
    private int value;

    @NonNull
    @ColumnInfo(name = "currency")
    private String currency;

    @NonNull
    @ColumnInfo(name = "add_date")
    private String add_date;

    @ColumnInfo(name = "end_date")
    private String end_date;

    @NonNull
    @ColumnInfo(name = "status")
    private int status;//oplacone, nie oplacone, usuniete,0 vs 1

    @NonNull
    @ColumnInfo(name = "my_debt")
    private int my_debt;//zmienna 0 lub 1, zero kiedy nie nasza

    public Debts() {
    }


    @Ignore
    public Debts( @NonNull String name, String contact, @NonNull String description, @NonNull int value, @NonNull String currency,@NonNull String add_date, @NonNull int status, @NonNull int my_debt) {
        this.name = name;
        this.contact = contact;
        this.description = description;
        this.value = value;
        this.currency = currency;
        this.add_date = add_date;
        this.status = status;
        this.my_debt = my_debt;
        end_date="";
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public int getValue() {
        return value;
    }

    public void setValue(@NonNull int value) {
        this.value = value;
    }

    @NonNull
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(@NonNull String currency) {
        this.currency = currency;
    }

    @NonNull
    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(@NonNull String add_date) {
        this.add_date = add_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @NonNull
    public int getStatus() {
        return status;
    }

    public void setStatus(@NonNull int status) {
        this.status = status;
    }

    @NonNull
    public int getMy_debt() {
        return my_debt;
    }

    public void setMy_debt(@NonNull int my_debt) {
        this.my_debt = my_debt;
    }
}
