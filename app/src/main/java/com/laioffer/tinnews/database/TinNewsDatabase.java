package com.laioffer.tinnews.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.laioffer.tinnews.model.Article;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class TinNewsDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();

    private static volatile TinNewsDatabase INSTANCE;


//
//    static TinNewsDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (TinNewsDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            TinNewsDatabase.class, "tinnews_database")
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}