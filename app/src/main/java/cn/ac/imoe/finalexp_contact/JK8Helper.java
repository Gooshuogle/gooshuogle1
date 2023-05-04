package cn.ac.imoe.finalexp_contact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class JK8Helper extends SQLiteOpenHelper {

    public JK8Helper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_sql = "CREATE TABLE contact (dphone TEXT NOT NULL, dname TEXT, academy TEXT, bestfriend TEXT, gender TEXT, owner TEXT NOT NULL, PRIMARY KEY (dphone, owner), FOREIGN KEY (owner) REFERENCES user(dusername));";
        sqLiteDatabase.execSQL(create_sql);

        String create_sql2 = "create table user(dusername text not null primary key,dpassword text);";
        sqLiteDatabase.execSQL(create_sql2);

        sqLiteDatabase.execSQL("INSERT INTO user VALUES('mali','123456'),('jixy','123456'),('list','123456');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}