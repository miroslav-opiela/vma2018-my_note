package sk.upjs.vma.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public DatabaseOpenHelper(Context context) {
        super(context, "my_note", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable());

        insertSampleEntry(db, "my first note");
        insertSampleEntry(db, "hello world");
    }

    private void insertSampleEntry(SQLiteDatabase db, String description) {
        ContentValues values = new ContentValues();
        values.put(MyNoteContract.Note.DESCRIPTION, description);
        values.put(MyNoteContract.Note.TIMESTAMP, System.currentTimeMillis() / 1000);
        db.insert(MyNoteContract.Note.TABLE_NAME, null, values);
    }

    private String createTable() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT,"
                + "%s INTEGER)";
        return String.format(sqlTemplate,
                MyNoteContract.Note.TABLE_NAME,
                MyNoteContract.Note._ID,
                MyNoteContract.Note.DESCRIPTION,
                MyNoteContract.Note.TIMESTAMP
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
