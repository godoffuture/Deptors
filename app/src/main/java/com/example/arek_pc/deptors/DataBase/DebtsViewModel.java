package com.example.arek_pc.deptors.DataBase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class DebtsViewModel  extends AndroidViewModel {

    private DebtsRepository mRepository;

    private LiveData<List<Debts>> mAllDebts,mMyDebts,mMyDebtors;

    public DebtsViewModel(Application application) {
        super(application);
        mRepository = new DebtsRepository(application);
        mAllDebts = mRepository.getAllDebts();
        mMyDebts = mRepository.getMyDebts();
        mMyDebtors = mRepository.getMyDebtors();
    }

    public LiveData<List<Debts>> getAllDebts(){
        return mAllDebts;}

    public LiveData<List<Debts>> getMyDebtors(){
        return mMyDebtors;}

    public LiveData<List<Debts>> getMyDebts(){
        return mMyDebts;}

    public void insertDebts(Debts debts){mRepository.insertDebt(debts);}

    public void updateDebt(int id, String end_date){mRepository.updateDebt(id,end_date);}

    public void updateDebts(Debts debts){mRepository.updateDebts(debts);}

    public void deleteDebt(Debts debts){mRepository.deleteDebt(debts);}

}
