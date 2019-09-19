package com.example.jetecpro_ver1.PDFandCSVHelper_ChartAct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SQLite.DBHelper;
import com.example.jetecpro_ver1.Values.SendType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

public class CreatePDFandCSV {
    Context context;

    String fileName, appendTitle;
    Activity activity;
    String FIRSTunit, SECONDunti,THIRDunit;
    JSONArray jsonArray;


    public CreatePDFandCSV(Activity activity) {
        this.activity = activity;
    }

    /**
     * CSV資料準備部分
     */
    public void CSV(Context context) {

        this.context = context;
        fileName = trans(R.string.CSVPDFTitle) + ".csv";
        //判斷型號
        String defult = "Id" + "," + trans(R.string.justTime);
        String first = "", second = "", third = "";
        String XTitle;

        switch (SendType.FirstWord) {
            case 'T':
                first = "," + trans(R.string.Temperature);
                FIRSTunit = "°C";
                break;
            case 'H':
                first = "," + trans(R.string.Humidity);
                FIRSTunit = "%";
                break;
            case 'I':

                break;
            default:
                first = "";
                break;
        }
        switch (SendType.SecondWord) {
            case 'T':
                second = "," + trans(R.string.Temperature);
                SECONDunti = "°C";
                break;
            case 'H':
                second = "," + trans(R.string.Humidity);
                SECONDunti = "%";
                break;
            case 'I':

                break;
            default:
                second = "";
                break;
        }
        switch (SendType.ThirdWord) {
            case 'T':
                first = "," + trans(R.string.Temperature);
                 THIRDunit= "°C";
                break;
            case 'H':
                first = "," + trans(R.string.Humidity);
                THIRDunit= "%";
                break;
            case 'I':

                break;
            default:
                third = "";
                break;
        }
        XTitle = defult + first + second + third;
        Log.v("BT", XTitle);

        /*==========收資料與製為CSV============*/
        String[] createFile = context.fileList();
        String checkFile = null;
        for (String repeatFile : createFile) {
            checkFile = repeatFile;
        }

        if (checkFile.contains(fileName)) {

            if (context.deleteFile(fileName)) {
                Log.v("BT", "su");
                write(fileName, XTitle);
            }
        } else {
            write(fileName, XTitle);
        }
    }

    /**
     * 取得SQLite資料
     */
    private void getSQLite() {
        String DB_NAME = SendType.DB_Name;
        String DB_TABLE = SendType.DB_TABLE+"GETRecord";
        SQLiteDatabase mCustomDb;

        DBHelper db = new DBHelper(context, DB_NAME, null, 1);
        mCustomDb = db.getWritableDatabase();
        Cursor cursor = mCustomDb.rawQuery(
                "SELECT * FROM " + DB_TABLE, null);
        String getJson = null;

        try {
            while (cursor.moveToNext()) {
                getJson = cursor.getString(1);
            }
            Log.v("BT",getJson);
            jsonArray = new JSONArray(getJson);
            Log.v("BT","1: "+jsonArray);
        } catch (JSONException e) {
            Log.v("BT","資料庫為空？"+e);
        }


    }

    /**
     * 寫CSV的主要部分
     */
    private void write(String fileName, String XTitle) {
        getSQLite();
        Log.v("BT","2: "+jsonArray);
        StringBuilder data = new StringBuilder();
        data.append(XTitle);
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                data.append("\n" + jsonObject.getString("id") + ","
                        + jsonObject.getString("RecordDate") + " " + jsonObject.getString("RecordTime") + ","
                        + trans2Double(jsonObject.getString("First")) +FIRSTunit+ ","
                        + trans2Double(jsonObject.getString("Second"))+SECONDunti);

            } catch (JSONException e) {
                Log.v("BT", "JS爆掉：" + e);
            }

        }
        try {
            FileOutputStream out = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
            out.write((data.toString().getBytes()));
            out.close();
            File filelocation = new File(Environment.
                    getExternalStorageDirectory().getAbsolutePath(), fileName);
            FileOutputStream fos = new FileOutputStream(filelocation);
            fos.write(data.toString().getBytes());
            Uri path = Uri.fromFile(filelocation);
            Toast.makeText(context,trans(R.string.localHaveAData)+"\n"+trans(R.string.localposition)+filelocation,Toast.LENGTH_LONG).show();

            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, fileName);
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            activity.startActivity(Intent.createChooser(fileIntent, "Send Mail"));
        } catch (Exception e) {
            Log.v("BT", "爆掉：" + e);
        }


    }


    public void PDF() {


    }

    private String trans(int name) {
        String str = context.getResources().getString(name);
        return str;
    }

    /**
     * 轉數字
     */
    private String trans2Double(String input) {
        String s1 = input.substring(0, 5);
        float d1 = Float.parseFloat(s1);
        float out;
        switch (input.substring(5)) {
            case "1":
                out = d1 * 0.1f;
                break;
            case "2":
                out = d1 * 0.01f;
                break;
            default:
                out = d1;
                break;

        }
        return String.valueOf(out);
    }
}
