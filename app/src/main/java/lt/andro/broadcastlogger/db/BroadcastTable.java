
package lt.andro.broadcastlogger.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * {@link BroadcastTable}<br/>
 * <br/>
 * 
 * @author Vilius Kraujutis
 * @since Nov 25, 2012 7:08:35 PM
 */

public class BroadcastTable {

    // Database table
    public static final String TABLE_NAME = "broadcasts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ACTION = "action";
    public static final String COLUMN_EXTRAS = "extras";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_ACTION + " text not null, "
            + COLUMN_EXTRAS + " text not null," + COLUMN_TIMESTAMP + " text not null" + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(BroadcastTable.class.getName(), "Upgrading database from version " + oldVersion
                + " to " + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}
