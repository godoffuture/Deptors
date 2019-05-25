package com.example.arek_pc.deptors;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.arek_pc.deptors.DataBase.Debts;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter_debt extends RecyclerView.Adapter<CardHolder_debt> {

    private CardAdapter_debt.OnItemClickListener mListener;
    private List<Debts> mDebts;
    private final LayoutInflater mInflater;
    private Debts debt;
    private int debtid;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onStatusClick(int id);
        void onDeleteClick(int position);
        void onOptionClick(int position);
    }

    public void setOnItemClickListener(CardAdapter_debt.OnItemClickListener listener) {
        mListener = listener;
    }

    public CardAdapter_debt(Context context, ArrayList<Debts> debt) {
        mInflater = LayoutInflater.from(context);
        mDebts=debt;
    }

    @NonNull
    @Override
    public CardHolder_debt onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.deptor_cardview, parent, false);
        return new CardHolder_debt(v, mListener);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CardHolder_debt holder, int position) {

        debt = mDebts.get(position);

        holder.mName.setText(debt.getName());

        if(debt.getEnd_date()=="")
        holder.mDate.setText(debt.getAdd_date());
        else holder.mDate.setText(debt.getAdd_date()
                +" "+debt.getEnd_date());

        holder.mDescription.setText(debt.getDescription());

        if(debt.getStatus()==0)
        holder.mStatus.setText("opłacone");
        else holder.mStatus.setText("nie opłacone");

        if(debt.getMy_debt()==0){
            holder.mValue.setTextColor(R.color.colorDebt);
            holder.mValue.setText(Integer.toString(debt.getValue()));
        }else if(debt.getMy_debt()==1){
            holder.mValue.setTextColor(R.color.colorDebtor);
            holder.mValue.setText("-"+Integer.toString(debt.getValue()));
        }

        holder.mCurrency.setText(debt.getCurrency());
        debtid = holder.setId(debt.getId());
    }

    int setID(){
        return debtid;
    }

    void setmDebts(List<Debts> debts)
    {
        mDebts=debts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mDebts != null) {
            return mDebts.size();
            }else return 0;
    }
}

class CardHolder_debt extends RecyclerView.ViewHolder
{
    final TextView mName;
    final TextView mDescription;
    final TextView mDate;
    final TextView mStatus;
    final TextView mValue;
    final TextView mCurrency;
    int id;
    final Button statusbtn,deletebtn,optionbtn;


    public int setId(int id) {
        return this.id = id;
            }


    CardHolder_debt(View itemView, final CardAdapter_debt.OnItemClickListener listener) {
        super(itemView);

        this.mName = itemView.findViewById(R.id.name_cardview);
        this.mDescription = itemView.findViewById(R.id.description_cardview);
        this.mDate = itemView.findViewById(R.id.date_cardview);
        this.mStatus = itemView.findViewById(R.id.status_cardview);
        this.mValue = itemView.findViewById(R.id.value_cardview);
        this.mCurrency = itemView.findViewById(R.id.currency_cardview);

        this.statusbtn = itemView.findViewById(R.id.status_button);
        this.deletebtn = itemView.findViewById(R.id.delete_button);
        this.optionbtn = itemView.findViewById(R.id.option_button);

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

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        listener.onDeleteClick(position);
                    }
                }
            }
        });

        statusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        listener.onStatusClick(id);
                    }
                }
            }
        });

        optionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        listener.onOptionClick(position);
                    }
                }
            }
        });

    }


}
