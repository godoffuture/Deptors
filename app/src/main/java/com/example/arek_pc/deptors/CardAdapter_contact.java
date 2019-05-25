package com.example.arek_pc.deptors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arek_pc.deptors.DataBase.Debts;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter_contact extends RecyclerView.Adapter<CardHolder_contact> {

    private CardAdapter_contact.OnItemClickListener mListener;
    private List<Contact> mContacts = new ArrayList<>();
    private final LayoutInflater mInflater;
    private Contact contact;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CardAdapter_contact.OnItemClickListener listener) {
        mListener = listener;
    }

    public CardAdapter_contact(Context context, ArrayList<Contact> contacts) {
        mInflater = LayoutInflater.from(context);
        mContacts=contacts;
    }

    @NonNull
    @Override
    public CardHolder_contact onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.contact_cardview, parent, false);
        return new CardHolder_contact(v, mListener);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CardHolder_contact holder, int position) {

        contact = mContacts.get(position);
        holder.mName.setText(contact.getName());
        holder.mNumber.setText(contact.getNumber());
    }

    void setmDebts(List<Contact> contacts)
    {
        mContacts=contacts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mContacts != null) {
            return mContacts.size();
        }else return 0;
    }
}

class CardHolder_contact extends RecyclerView.ViewHolder
{
    final TextView mName;
    final TextView mNumber;


    CardHolder_contact(View itemView, final CardAdapter_contact.OnItemClickListener listener) {
        super(itemView);

        this.mName = itemView.findViewById(R.id.name_text);
        this.mNumber = itemView.findViewById(R.id.number_text);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        listener.onItemClick(position);

                    }
                }
            }
        });

    }


}
