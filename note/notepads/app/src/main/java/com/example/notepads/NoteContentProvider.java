package com.example.notepads;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.util.Log;

import com.example.notepads.Notes.Note;

public class NoteContentProvider extends ContentProvider {
    private  NotesDBHelper notesDBHelper = null;
    private  static final int result_code =1;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String ANTHOR ="com.example.notepads.provider";
    static {
        uriMatcher.addURI("com.example.notepads.provider", null,result_code);
    }

    public NoteContentProvider() {
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase db = notesDBHelper.getWritableDatabase();
       int deleteRows = 0;
        int result = uriMatcher.match(uri);
        if (result == result_code) {
            String noteID=uri.getPathSegments().get(1);
            Log.d("TAG", "deleteID---------> "+noteID);
            deleteRows = db.delete("notes","_id=?",new String[]{noteID});
        }
        return deleteRows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db = notesDBHelper.getWritableDatabase();
        Uri uriReturn = null;
        int result = uriMatcher.match(uri);
        if (result == result_code) {
            long newNoteid =db.insert("notes",null,values);
            uriReturn=Uri.parse("content://"+ANTHOR+"/notes/"+newNoteid);
            Log.d("TAG", "NEW ADD ID IS"+newNoteid);
        }
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        notesDBHelper = new NotesDBHelper(getContext());
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        int result = uriMatcher.match(uri);
        if (result==result_code){
            SQLiteDatabase db = notesDBHelper.getReadableDatabase();
            Cursor cursor = db.query(Note.TABLE_NAME,null,null,null,null,null,null);
            return cursor;
        }
        else {
            throw new IllegalArgumentException("参数错误");

        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase db = notesDBHelper.getWritableDatabase();
       int uridatedRows = 0;
        int result = uriMatcher.match(uri);
        if (1==1) {
            String newNoteid =uri.getPathSegments().get(1);
            Log.d("TAG", "updataID---------> "+newNoteid);
            uridatedRows=db.update("notes",values,"_id=?",new String[]{newNoteid});
        }
        Log.d("TAG", "updataID--------->!!!!!!!!!!!!! ");
        return uridatedRows;
    }
}
