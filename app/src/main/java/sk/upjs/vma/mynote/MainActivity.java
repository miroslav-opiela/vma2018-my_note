package sk.upjs.vma.mynote;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 3;

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView notesGridView = findViewById(R.id.notesGridView);
        String[] from = {MyNoteContract.Note.DESCRIPTION};
        int[] to = {R.id.cardText};
        adapter = new SimpleCursorAdapter(this, R.layout.note,
                null, from, to, 0);
        notesGridView.setAdapter(adapter);

        getLoaderManager().initLoader(LOADER_ID, Bundle.EMPTY, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        if (id != LOADER_ID) {
            throw new IllegalStateException("Invalid Loader with ID: " + id);
        }
        CursorLoader loader = new CursorLoader(this);
        loader.setUri(MyNoteContract.Note.CONTENT_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
        cursor.setNotificationUri(getContentResolver(), MyNoteContract.Note.CONTENT_URI);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    private void createNewNote() {
        final EditText descriptionEditText = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Add a new note")
                .setView(descriptionEditText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String description = descriptionEditText.getText().toString();
                        insertIntoContentProvider(description);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void insertIntoContentProvider(String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyNoteContract.Note.DESCRIPTION, description);
        // normal
        //getContentResolver().insert(MyNoteContract.Note.CONTENT_URI, contentValues);
        // asynchronne
        // abstraktna trieda
        AsyncQueryHandler queryHandler = new AsyncQueryHandler(getContentResolver()) {
            @Override
            protected void onInsertComplete(int token, Object cookie, Uri uri) {
                Toast.makeText(MainActivity.this, "Saved " + cookie.toString(), Toast.LENGTH_LONG).show();
            }
        };
        queryHandler.startInsert(0, description, MyNoteContract.Note.CONTENT_URI, contentValues);
    }



    public void onFabClick(View view) {
        createNewNote();
        /*ContentValues contentValues = new ContentValues();
        contentValues.put(MyNoteContract.Note.DESCRIPTION, "Test this");
        getContentResolver().insert(MyNoteContract.Note.CONTENT_URI, contentValues);*/
    }
}
