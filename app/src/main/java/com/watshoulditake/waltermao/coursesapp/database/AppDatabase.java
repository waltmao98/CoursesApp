package com.watshoulditake.waltermao.coursesapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class AppDatabase extends SQLiteAssetHelper {

    public static final int DB_VERSION = 1;

    public AppDatabase(Context context) {
        super(context, DBSchema.DB_NAME, null, DB_VERSION);
    }

    public CourseCursorWrapper queryCourses(String[] colNames,
                                            String whereClause,
                                            String[] whereArgs,
                                            String groupBy,
                                            String having,
                                            String orderBy){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DBSchema.TABLE_NAME,
                colNames,
                whereClause,
                whereArgs,
                null,
                null,
                orderBy);

        return new CourseCursorWrapper(cursor);
    }

    public void close() {
        close();
    }

}
