package sk.upjs.vma.mynote;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyNoteContentProvider extends ContentProvider {

    private DatabaseOpenHelper databaseOpenHelper;

    @Override
    public boolean onCreate() {
        databaseOpenHelper = new DatabaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(MyNoteContract.Note.TABLE_NAME, null,
                null, null, null, null, null);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = databaseOpenHelper.getWritableDatabase();

        Log.d("PROVIDER", values.toString());

        long id = db.insert(MyNoteContract.Note.TABLE_NAME, null, values);

        //getContext().getContentResolver().notifyChange(JotContract.Note.CONTENT_URI, NO_CONTENT_OBSERVER);

        getContext().getContentResolver().notifyChange(MyNoteContract.Note.CONTENT_URI, null);

        return Uri.withAppendedPath(MyNoteContract.Note.CONTENT_URI, String.valueOf(id));
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
