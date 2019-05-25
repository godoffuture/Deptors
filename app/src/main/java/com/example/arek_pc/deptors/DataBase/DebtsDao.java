package com.example.arek_pc.deptors.DataBase;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface DebtsDao {
    @Query("SELECT * FROM Debts_table")
    LiveData<List<Debts>> getAllDebts();

    @Query("SELECT * FROM Debts_table WHERE my_debt=0 and status=1")
    LiveData<List<Debts>> getMyDebtors();

    @Query("SELECT * FROM Debts_table WHERE my_debt=1 and status=1")
    LiveData<List<Debts>> getMyDebts();

    @Insert
    void insertDebt(Debts... debts);

    @Query("Update Debts_table SET status=0, end_date=:end_date WHERE id =:id")
    void updateDebt(int id, String end_date);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateDebts(Debts... debts);

    @Delete
    void deleteDebt(Debts... debts);
}
