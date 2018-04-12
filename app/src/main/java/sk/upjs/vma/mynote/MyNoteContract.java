package sk.upjs.vma.mynote;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public interface MyNoteContract {

    String AUTHORITY = "sk.upjs.vma.mynote";

    interface Note extends BaseColumns {
        String TABLE_NAME = "note";

        String DESCRIPTION = "description";
        String TIMESTAMP = "timestamp";

        Uri CONTENT_URI = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

}
