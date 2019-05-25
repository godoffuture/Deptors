package com.example.arek_pc.deptors.DataBase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import static android.content.ContentValues.TAG;

public class DebtsRepository {

    private LiveData<List<Debts>> mAllDepts, mMyDepts, mMyDeptors;

    private Debts debt;
    private DebtsDao mDeptsDao;

    DebtsRepository(Application application) {
        DebtsDataBase db = DebtsDataBase.getInstance(application);
        mDeptsDao = db.debtsDao();
        mAllDepts = mDeptsDao.getAllDebts();
        mMyDepts = mDeptsDao.getMyDebts();
        mMyDeptors = mDeptsDao.getMyDebtors();
    }

    public LiveData<List<Debts>> getAllDebts() {
        return mAllDepts;
    }

    public LiveData<List<Debts>> getMyDebtors() { return mMyDeptors; }

    public LiveData<List<Debts>> getMyDebts() {
        return mMyDepts;
    }

    public void updateDebts(Debts debts){
        new UpdateAsyncTask(mDeptsDao).execute(debts);}

    public void deleteDebt(Debts debts) {
        deleteDebt(debts);
    }

    public void insertDebt(Debts debts) {
        new InsertAsyncTask(mDeptsDao).execute(debts); }

    public void updateDebt(final int id,final String end_date) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mDeptsDao.updateDebt(id,end_date);
            }
        });

    }

    private static class InsertAsyncTask extends AsyncTask<Debts, Void, Void> {

        private DebtsDao mAsyncTaskDao;
        InsertAsyncTask(DebtsDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Debts... params) {
            mAsyncTaskDao.insertDebt(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Debts, Void, Void> {

        private DebtsDao mAsyncTaskDao;
        UpdateAsyncTask(DebtsDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Debts... params) {
            mAsyncTaskDao.updateDebts(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Debts, Void, Void> {

        private DebtsDao mAsyncTaskDao;
        DeleteAsyncTask(DebtsDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Debts... params) {
            mAsyncTaskDao.deleteDebt(params[0]);
            return null;
        }
    }

}
