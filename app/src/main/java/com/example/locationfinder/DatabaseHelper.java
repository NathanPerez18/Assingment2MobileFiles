package com.example.locationfinder;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LocationData.db";
    private static final int DATABASE_VERSION = 1;
    private final Context context;

    public static final String TABLE_NAME = "LocationInfo";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ADDRESS + " TEXT, " +
                    COLUMN_LATITUDE + " REAL, " +
                    COLUMN_LONGITUDE + " REAL);";

    // Set this flag to true to enable table clearing on each app start (for testing only)
    private static final boolean IS_TESTING = false;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        populateDatabaseWithHardcodedData(db); // Directly populate without checking if empty
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (IS_TESTING) {
            // Drop the table if it exists to start fresh every time (for testing purposes only)
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db); // Recreate the table and populate it with hardcoded data
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void populateDatabaseWithHardcodedData(SQLiteDatabase db) {
        insertHardcodedData(db, "Toronto, ON", 43.65107, -79.347015);
        insertHardcodedData(db, "Mississauga, ON", 43.589045, -79.64412);
        insertHardcodedData(db, "Brampton, ON", 43.731548, -79.762418);
        insertHardcodedData(db, "Oakville, ON", 43.467517, -79.687666);
        insertHardcodedData(db, "Vaughan, ON", 43.836101, -79.498954);
        insertHardcodedData(db, "Richmond Hill, ON", 43.878547, -79.435682);
        insertHardcodedData(db, "Markham, ON", 43.8561, -79.337019);
        insertHardcodedData(db, "Burlington, ON", 43.32552, -79.799031);
        insertHardcodedData(db, "Pickering, ON", 43.835017, -79.089375);
        insertHardcodedData(db, "Ajax, ON", 43.850857, -79.020374);
        insertHardcodedData(db, "Whitby, ON", 43.897545, -78.942932);
        insertHardcodedData(db, "Oshawa, ON", 43.897093, -78.865791);
        insertHardcodedData(db, "Milton, ON", 43.518299, -79.877404);
        insertHardcodedData(db, "Georgetown, ON", 43.646523, -79.921152);
        insertHardcodedData(db, "Newmarket, ON", 44.059187, -79.461256);
        insertHardcodedData(db, "Aurora, ON", 44.00648, -79.450396);
        insertHardcodedData(db, "King City, ON", 43.9215, -79.52806);
        insertHardcodedData(db, "Caledon, ON", 43.86682, -79.982817);
        insertHardcodedData(db, "Bolton, ON", 43.874816, -79.735395);
        insertHardcodedData(db, "Stouffville, ON", 43.97024, -79.244865);
        insertHardcodedData(db, "Uxbridge, ON", 44.109467, -79.120054);
        insertHardcodedData(db, "Nobleton, ON", 43.92897, -79.648198);
        insertHardcodedData(db, "Port Perry, ON", 44.105219, -78.944776);
        insertHardcodedData(db, "Brooklin, ON", 43.9626, -78.9501);
        insertHardcodedData(db, "Claremont, ON", 44.03889, -79.0791);
        insertHardcodedData(db, "Mount Albert, ON", 44.1168, -79.321);
        insertHardcodedData(db, "Tottenham, ON", 44.0203, -79.8056);
        insertHardcodedData(db, "Beeton, ON", 44.0808, -79.7828);
        insertHardcodedData(db, "Schomberg, ON", 44.0114, -79.6855);
        insertHardcodedData(db, "Alton, ON", 43.8869, -80.0677);
        insertHardcodedData(db, "Palgrave, ON", 43.9527, -79.8721);
        insertHardcodedData(db, "Orangeville, ON", 43.919978, -80.094311);
        insertHardcodedData(db, "Acton, ON", 43.6334, -80.0422);
        insertHardcodedData(db, "Mono, ON", 44.0009, -80.0662);
        insertHardcodedData(db, "Rockwood, ON", 43.6177, -80.1426);
        insertHardcodedData(db, "Fergus, ON", 43.7055, -80.3771);
        insertHardcodedData(db, "Elora, ON", 43.6856, -80.4308);
        insertHardcodedData(db, "Erin, ON", 43.7688, -80.0682);
        insertHardcodedData(db, "Puslinch, ON", 43.4743, -80.1577);
        insertHardcodedData(db, "Guelph, ON", 43.5448, -80.2482);
        insertHardcodedData(db, "East Gwillimbury, ON", 44.0738, -79.4321);
        insertHardcodedData(db, "Bradford, ON", 44.1148, -79.5666);
        insertHardcodedData(db, "Barrie, ON", 44.3894, -79.6903);
        insertHardcodedData(db, "Innisfil, ON", 44.3001, -79.6118);
        insertHardcodedData(db, "Keswick, ON", 44.2257, -79.4676);
        insertHardcodedData(db, "Sutton, ON", 44.3007, -79.3634);
        insertHardcodedData(db, "Pefferlaw, ON", 44.3179, -79.2294);
        insertHardcodedData(db, "Zephyr, ON", 44.1626, -79.2626);
        insertHardcodedData(db, "Holland Landing, ON", 44.0966, -79.4932);
        insertHardcodedData(db, "Cookstown, ON", 44.2327, -79.6871);
        insertHardcodedData(db, "Minesing, ON", 44.4663, -79.8294);
        insertHardcodedData(db, "Oro-Medonte, ON", 44.5183, -79.5415);
        insertHardcodedData(db, "Orillia, ON", 44.6082, -79.4186);
        insertHardcodedData(db, "Coldwater, ON", 44.6942, -79.6421);
        insertHardcodedData(db, "Severn, ON", 44.7242, -79.5375);
        insertHardcodedData(db, "Port McNicoll, ON", 44.7429, -79.7754);
        insertHardcodedData(db, "Victoria Harbour, ON", 44.7515, -79.8023);
        insertHardcodedData(db, "Midland, ON", 44.7496, -79.8895);
        insertHardcodedData(db, "Penetanguishene, ON", 44.768, -79.9312);
        insertHardcodedData(db, "Wasaga Beach, ON", 44.5205, -80.0164);
        insertHardcodedData(db, "Collingwood, ON", 44.5008, -80.2169);
        insertHardcodedData(db, "Thornbury, ON", 44.5632, -80.4571);
        insertHardcodedData(db, "Meaford, ON", 44.6057, -80.5948);
        insertHardcodedData(db, "Chatsworth, ON", 44.4364, -80.9346);
        insertHardcodedData(db, "Markdale, ON", 44.3193, -80.6475);
        insertHardcodedData(db, "Durham, ON", 44.1769, -80.8253);
        insertHardcodedData(db, "Flesherton, ON", 44.1811, -80.5528);
        insertHardcodedData(db, "Shelburne, ON", 44.0805, -80.2047);
        insertHardcodedData(db, "Grand Valley, ON", 43.9023, -80.3149);
        insertHardcodedData(db, "Caledon East, ON", 43.8811, -79.8712);
        insertHardcodedData(db, "Waterdown, ON", 43.3326, -79.8948);
        insertHardcodedData(db, "Carlisle, ON", 43.3965, -79.9803);
        insertHardcodedData(db, "Dundas, ON", 43.2665, -79.9533);
        insertHardcodedData(db, "Ancaster, ON", 43.2164, -79.9873);
        insertHardcodedData(db, "Binbrook, ON", 43.1208, -79.7891);
        insertHardcodedData(db, "Hamilton, ON", 43.2557, -79.8711);
        insertHardcodedData(db, "Stoney Creek, ON", 43.2187, -79.7396);
        insertHardcodedData(db, "Grimsby, ON", 43.2003, -79.5606);
        insertHardcodedData(db, "Lincoln, ON", 43.1698, -79.4316);
        insertHardcodedData(db, "Beamsville, ON", 43.1677, -79.4755);
        insertHardcodedData(db, "Smithville, ON", 43.1244, -79.5452);
        insertHardcodedData(db, "Niagara Falls, ON", 43.0896, -79.0849);
        insertHardcodedData(db, "Niagara-on-the-Lake, ON", 43.2541, -79.0718);
        insertHardcodedData(db, "Welland, ON", 42.9935, -79.2484);
        insertHardcodedData(db, "Port Colborne, ON", 42.9018, -79.2324);
        insertHardcodedData(db, "Thorold, ON", 43.1203, -79.1993);
        insertHardcodedData(db, "Pelham, ON", 43.0454, -79.3293);
        insertHardcodedData(db, "Wainfleet, ON", 42.9176, -79.3832);
        insertHardcodedData(db, "Fenwick, ON", 43.0242, -79.3656);
        insertHardcodedData(db, "St. Catharines, ON", 43.1594, -79.2469);
        insertHardcodedData(db, "Fort Erie, ON", 42.9048, -78.9333);
        insertHardcodedData(db, "Burlington South, ON", 43.3273, -79.8158);
        insertHardcodedData(db, "New Toronto, ON", 43.6006, -79.5128);
        insertHardcodedData(db, "Hannon, ON", 43.1805, -79.8033);
        insertHardcodedData(db, "Lynden, ON", 43.2622, -80.1739);
        insertHardcodedData(db, "Caistorville, ON", 43.0583, -79.6039);
        insertHardcodedData(db, "Jordan Station, ON", 43.1477, -79.3671);
        insertHardcodedData(db, "Winona, ON", 43.2091, -79.6646);
        insertHardcodedData(db, "Jordan Harbour, ON", 43.1604, -79.3766);
        insertHardcodedData(db, "Clarksburg, ON", 44.5495, -80.4455);
    }


    private void insertHardcodedData(SQLiteDatabase db, String address, double latitude, double longitude) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);

        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Log.e("DatabaseHelper", "Failed to insert data: " + address);
        } else {
            Log.d("DatabaseHelper", "Successfully inserted data: " + address);
        }
    }

    public void insertData(String address, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);

        long result = db.insert(TABLE_NAME, null, values);
        Log.d("DatabaseHelper", "Inserted data with result: " + result); // Check if the result is positive

        db.close();
    }

    public Cursor getAllLocations() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public void updateData(int id, String newAddress, double newLatitude, double newLongitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, newAddress);
        values.put(COLUMN_LATITUDE, newLatitude);
        values.put(COLUMN_LONGITUDE, newLongitude);

        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteData(String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ADDRESS + "=?", new String[]{address});
        db.close();
    }


    public Cursor searchLocation(String address) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_NAME,
                null,
                COLUMN_ADDRESS + " = ?",
                new String[]{address},
                null,
                null,
                null
        );
    }


}
