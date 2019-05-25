package com.example.arek_pc.deptors.DataBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;


@Database(entities = {Debts.class},version = 1)
public abstract class DebtsDataBase extends RoomDatabase {

    public abstract DebtsDao debtsDao();

    private static volatile DebtsDataBase mInstance;

    public static DebtsDataBase getInstance(final Context context)
    {
        if(mInstance==null){
            synchronized (DebtsDataBase.class) {
                mInstance = Room.databaseBuilder(context.getApplicationContext(), DebtsDataBase.class, "DeptsDB")
                        .addCallback(sRoomDatabaseCallback)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return mInstance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db)
                {
                    super.onOpen(db);
                }
            };
}
