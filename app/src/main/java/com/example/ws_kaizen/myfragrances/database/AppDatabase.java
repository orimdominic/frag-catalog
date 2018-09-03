package com.example.ws_kaizen.myfragrances.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.util.Log;

import static android.arch.persistence.room.ColumnInfo.TEXT;
import static android.graphics.PorterDuff.Mode.ADD;

@Database(entities = {FragranceEntry.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "fragrances";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .addMigrations(FROM_1_TO_2, FROM_2_TO_3)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    static final Migration FROM_1_TO_2 = new Migration(1, 2) {
//        Added 2  new columns to fragrance table: quantity_in_stock and quantity_sold
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE fragrance ADD COLUMN quantity_in_stock INTEGER NOT NULL DEFAULT 0 ");
            database.execSQL("ALTER TABLE fragrance ADD COLUMN quantity_sold INTEGER NOT NULL DEFAULT 0 ");
        }
    };

    static final Migration FROM_2_TO_3 = new Migration(2, 3) {
//        Added gender column to fragrance table
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE fragrance ADD COLUMN gender INTEGER NOT NULL DEFAULT 1 ");
        }
    };

    public abstract FragranceDao mFragranceDao();

}
