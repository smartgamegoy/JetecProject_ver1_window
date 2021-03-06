package com.example.jetecpro_ver1.PDFandCSVHelper_ChartAct;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.jetecpro_ver1.MainProcess.ChartActivity;
import com.example.jetecpro_ver1.R;
import com.example.jetecpro_ver1.SQLite.DBHelper;
import com.example.jetecpro_ver1.Values.SendType;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreatePDFandCSV {
    Context context;

    String fileName, appendTitle;
    Activity activity;
    String FIRSTunit, SECONDunti, THIRDunit;
    JSONArray jsonArray;
    PdfPTable table_Row3,table_Row2,table_Row1;


    public CreatePDFandCSV(Activity activity) {
        this.activity = activity;
    }

    /**
     * CSV資料準備部分
     */
    public void CSV(Context context) {

        this.context = context;
        fileName = trans(R.string.CSVPDFTitle)+".csv";
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
            case 'C':
            case 'D':
            case 'E':
                first = "," + trans(R.string.CO2);
                FIRSTunit = "ppm";
                break;
            case 'I':
                first = "," + trans(R.string.FirstRow);
                FIRSTunit = "";
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
            case 'C':
            case 'D':
            case 'E':
                second = "," + trans(R.string.CO2);
                SECONDunti = "ppm";
                break;
            case 'I':
                second = "," + trans(R.string.SecondRow);
                SECONDunti = "";
                break;
            default:
                second = "";
                break;
        }
        switch (SendType.ThirdWord) {
            case 'T':
                first = "," + trans(R.string.Temperature);
                THIRDunit = "°C";
                break;
            case 'H':
                first = "," + trans(R.string.Humidity);
                THIRDunit = "%";
                break;
            case 'C':
            case 'D':
            case 'E':
                third = "," + trans(R.string.CO2);
                THIRDunit = "ppm";
                break;
            case 'I':
                third = "," + trans(R.string.ThirdRow);
                THIRDunit = "";
                break;
            default:
                third = "";
                break;
        }
        XTitle = defult + first + second + third;
//        Log.v("BT", XTitle);

        /*==========收資料與製為CSV============*/
        String[] createFile = context.fileList();
        String checkFile = null;
        for (String repeatFile : createFile) {
            checkFile = repeatFile;
        }
        try {
            if (checkFile.contains(fileName)) {

                if (context.deleteFile(fileName)) {
                    Log.v("BT", "su");
                    write(fileName, XTitle);
                }
            }
        } catch (Exception e) {
            write(fileName, XTitle);
        }

    }

    /**
     * 取得SQLite資料
     */
    private void getSQLite() {
        String DB_NAME = SendType.DB_Name;
        String DB_TABLE = SendType.DB_TABLE + "GETRecord";
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
            Log.v("BT", getJson);
            jsonArray = new JSONArray(getJson);
//            Log.v("BT","1: "+jsonArray);
        } catch (JSONException e) {
            Log.v("BT", "資料庫為空？" + e);
        }


    }

    /**
     * 寫CSV的主要部分
     */
    private void write(String fileName, String XTitle) {
        getSQLite();
//        Log.v("BT","2: "+jsonArray);
        StringBuilder data = new StringBuilder();
        data.append(XTitle);
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                switch (SendType.row) {
                    case '1':

                        break;

                    case '2':
                        CSVRow2(data, i);
                        break;
                    case '3':
                        CSVRow3(data, i);
                        break;
                }


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
            Toast.makeText(context, trans(R.string.localHaveAData) + "\n" + trans(R.string.localposition) + filelocation, Toast.LENGTH_LONG).show();

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

    /**
     * CSV兩排
     */
    private void CSVRow2(StringBuilder data, int i) throws JSONException {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        data.append("\n" + jsonObject.getString("id") + ","
                + jsonObject.getString("RecordDate") + " " + jsonObject.getString("RecordTime") + ","
                + trans2Double(jsonObject.getString("First")) + FIRSTunit + ","
                + trans2Double(jsonObject.getString("Second")) + SECONDunti);
    }

    /**
     * CSV三排
     */
    private void CSVRow3(StringBuilder data, int i) throws JSONException {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        data.append("\n" + jsonObject.getString("id") + ","
                + jsonObject.getString("RecordDate") + " " + jsonObject.getString("RecordTime") + ","
                + trans2Double(jsonObject.getString("First")) + FIRSTunit + ","
                + trans2Double(jsonObject.getString("Second")) + SECONDunti + ","
                + trans2Double(jsonObject.getString("Third")) + THIRDunit);
    }

    /**
     * PDF資料處理1排的
     */
    public void PDF_1C(Context context) {
        this.context = context;
    }

    /**
     * PDF資料處理2排的
     */
    public void PDF_2C(Context context) {
        selectUnit();//選擇單位
        this.context = context;
        getSQLite();
        String mFileName = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss",
                Locale.getDefault()).format(System.currentTimeMillis());
        String mFilePath = Environment.getExternalStorageDirectory() + "/" + SendType.DeviceName + "數據報表 " + mFileName + ".pdf";
        Document document = new Document(PageSize.A4, 20, 20, 10, 40);
        try {
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStr2eam(mFilePath));
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(mFilePath));
            TOCCreation event = new TOCCreation();
            writer.setPageEvent(event);


            document.open();
            //======================================================================================

            document.add(new Paragraph("Jetec Electronics CO,LTD"));
            document.add(new Paragraph(" "));

            event.setRoot(writer.getRootOutline());
            ColumnText ct = new ColumnText(writer.getDirectContent());
            PdfPTable tabbbb = new PdfPTable(9);
            tabbbb.setWidthPercentage(90);
            PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(24f, "Date")));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBackgroundColor(BaseColor.GRAY);
            PdfPCell cell2 = new PdfPCell(new Paragraph(new Phrase(24f, Lab(SendType.FirstWord,1))));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell2.setBackgroundColor(BaseColor.GRAY);
            PdfPCell cell3 = new PdfPCell(new Paragraph(new Phrase(24f, Lab(SendType.SecondWord,2))));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell3.setBackgroundColor(BaseColor.GRAY);
            for (int i = 0; i < 3; i++) {
                tabbbb.addCell(cell);
                tabbbb.addCell(cell2);
                tabbbb.addCell(cell3);
            }

            document.add(tabbbb);
//            Log.v("BT","json長度:"+ jsonArray.length());
            int start;
            int end = 0;

            for (int i = 0; i <= jsonArray.length() - 1; ) {
                start = (i) + 1;
                i++;
                end = i;
                String title = String.format("", start, end);
                Chunk c = new Chunk(title);
                c.setGenericTag(title);
                ct.addElement(c);
                ct.addElement(createTable_Row2(start, end));
            }
            //補格子
            int fullPageCell = 135;
            if(end%fullPageCell >0){
                int func = ((end/fullPageCell)+1)*fullPageCell;
                for (int i = 0;i<func-end;i++){
                    PdfPCell dateCell = new PdfPCell(Phrase.getInstance(" "));
                    dateCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table_Row2.addCell(dateCell);
                    table_Row2.addCell(" ");
                    table_Row2.addCell(" ");
                }
            }
            int column = 0;
            do {
                if (column == 3) {

                    document.newPage();
                    document.add(new Paragraph("Jetec Electronics CO,LTD"));
                    document.add(new Paragraph(" "));
                    document.add(tabbbb);
                    column = 0;
                }
                ct.setSimpleColumn(COLUMNS_Row2[column++]);
            }
            while (ColumnText.hasMoreText(ct.go()));
            document.newPage();


            //======================================================================================
            document.close();
            Toast.makeText(activity, SendType.DeviceName + "數據報表.pdf\nis saved to\n" + mFilePath, Toast.LENGTH_SHORT).show();

            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            File filelocation = new File(Environment.
                    getExternalStorageDirectory(), "/" + SendType.DeviceName + "數據報表 " + mFileName + ".pdf");
            Uri path = Uri.fromFile(filelocation);
            fileIntent.setType("text/plain");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, SendType.DeviceName + "的數據報表");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            activity.startActivity(Intent.createChooser(fileIntent, "Send Mail"));

        } catch (FileNotFoundException e) {
            Log.d("BT", String.valueOf(e));
        } catch (DocumentException e) {
            Log.d("BT", String.valueOf(e));
        } catch (Exception e) {
            Log.d("BT", String.valueOf(e));
        }
    }

    /**
     * 篩選單位
     */
    private void selectUnit() {
        switch (SendType.FirstWord) {
            case 'T':

                FIRSTunit = "°C";
                break;
            case 'H':

                FIRSTunit = "%";
                break;
            case 'C':
            case 'D':
            case 'E':

                FIRSTunit = "ppm";
                break;
            case 'I':

                FIRSTunit = "";
                break;
            default:

                break;
        }
        switch (SendType.SecondWord) {
            case 'T':

                SECONDunti = "°C";
                break;
            case 'H':

                SECONDunti = "%";
                break;
            case 'C':
            case 'D':
            case 'E':

                SECONDunti = "ppm";
                break;
            case 'I':

                SECONDunti = "";
                break;
            default:

                break;
        }
        switch (SendType.ThirdWord) {
            case 'T':

                THIRDunit = "°C";
                break;
            case 'H':

                THIRDunit = "%";
                break;
            case 'C':
            case 'D':
            case 'E':

                THIRDunit = "ppm";
                break;
            case 'I':

                THIRDunit = "";
                break;
            default:

                break;
        }
    }

    /**
     * PDF資料處理3排的
     */
    public void PDF_3C(Context context) {
        this.context = context;
        getSQLite();
        selectUnit();

                String mFileName = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss",
                        Locale.getDefault()).format(System.currentTimeMillis());
                String mFilePath = Environment.getExternalStorageDirectory() + "/" + SendType.DeviceName + "數據報表 " + mFileName + ".pdf";
                Document document = new Document(PageSize.A4, 20, 20, 10, 40);
                try {
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(mFilePath));
                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(mFilePath));
                    TOCCreation event = new TOCCreation();
                    writer.setPageEvent(event);


                    document.open();
                    //======================================================================================

                    document.add(new Paragraph("Jetec Electronics CO,LTD"));
                    document.add(new Paragraph(" "));

                    event.setRoot(writer.getRootOutline());
                    ColumnText ct = new ColumnText(writer.getDirectContent());
                    PdfPTable tabbbb = new PdfPTable(8);
                    tabbbb.setWidths(new float[]{20,26,26,26,20,26,26,26});
                    tabbbb.setWidthPercentage(90);
                    PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(24f, "Date")));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBackgroundColor(BaseColor.GRAY);
                    PdfPCell cell2 = new PdfPCell(new Paragraph(new Phrase(24f, Lab(SendType.FirstWord,1))));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell2.setBackgroundColor(BaseColor.GRAY);
                    PdfPCell cell3 = new PdfPCell(new Paragraph(new Phrase(24f, Lab(SendType.SecondWord,2))));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell3.setBackgroundColor(BaseColor.GRAY);
                    PdfPCell cell4 = new PdfPCell(new Paragraph(new Phrase(24f, Lab(SendType.ThirdWord,3))));
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell4.setBackgroundColor(BaseColor.GRAY);
                    for (int i = 0; i < 2; i++) {
                        tabbbb.addCell(cell);
                        tabbbb.addCell(cell2);
                        tabbbb.addCell(cell3);
                        tabbbb.addCell(cell4);
                    }

                    document.add(tabbbb);


//            Log.v("BT","json長度:"+ jsonArray.length());
                    int start;
                    int end = 0;

                    for (int i = 0; i <= jsonArray.length() - 1; ) {
                        start = (i) + 1;
                        i++;
                        end = i;
                        String title = String.format("", start, end);
                        Chunk c = new Chunk(title);
                        c.setGenericTag(title);
                        ct.addElement(c);
                        ct.addElement(createTable_Row3(start, end));
                    }
                    //補格子～
                    int fullPageCell = 90;
                    if(end%fullPageCell >0){
                        int func = ((end/fullPageCell)+1)*fullPageCell;
                        for (int i = 0;i<func-end;i++){
                            PdfPCell dateCell = new PdfPCell(Phrase.getInstance(" "));
                            dateCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            table_Row3.addCell(dateCell);
                            table_Row3.addCell(" ");
                            table_Row3.addCell(" ");
                            table_Row3.addCell(" ");
                        }
                    }
                    int column = 0;
                    do {
                        if (column == 2) {

                            document.newPage();
                            document.add(new Paragraph("Jetec Electronics CO,LTD"));
                            document.add(new Paragraph(" "));
                            document.add(tabbbb);
                            column = 0;
                        }
                        ct.setSimpleColumn(COLUMNS_Row3[column++]);
                    }
                    while (ColumnText.hasMoreText(ct.go()));
                    document.newPage();


                    //======================================================================================
                    document.close();
                    Toast.makeText(activity, SendType.DeviceName + "數據報表.pdf\nis saved to\n" + mFilePath, Toast.LENGTH_SHORT).show();

                    Intent fileIntent = new Intent(Intent.ACTION_SEND);
                    File filelocation = new File(Environment.
                            getExternalStorageDirectory(), "/" + SendType.DeviceName + "數據報表 " + mFileName + ".pdf");
                    Uri path = Uri.fromFile(filelocation);
                    fileIntent.setType("text/plain");
                    fileIntent.putExtra(Intent.EXTRA_SUBJECT, SendType.DeviceName + "的數據報表");
                    fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                    activity.startActivity(Intent.createChooser(fileIntent, "Send Mail"));

                } catch (FileNotFoundException e) {
                    Log.d("BT", String.valueOf(e));
                } catch (DocumentException e) {
                    Log.d("BT", String.valueOf(e));
                } catch (Exception e) {
                    Log.d("BT", String.valueOf(e));
                }


    }
    //=======================================================
    //輔助PDF的(２排)
    public static final Rectangle[] COLUMNS_Row2 = {//X,Y,W,H
//            new Rectangle(36, 36, 192, 806),
//            new Rectangle(204, 36, 348, 806),
//            new Rectangle(360, 36, 504, 806)
            new Rectangle(48, 50, 214, 780),
            new Rectangle(215, 50, 380, 780),
            new Rectangle(381, 50, 547, 780)
    };//192是第一區塊的寬度
    private PdfPTable createTable_Row2(int start, int end) throws IOException {
        table_Row2 = new PdfPTable(3);
        table_Row2.setWidthPercentage(100);
        table_Row2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        for (int i = start; i <= end; i++) {//一頁135資料
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i-1);
                PdfPCell dateCell = new PdfPCell(Phrase.getInstance(jsonObject.getString("RecordDate").substring(5)));
                dateCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table_Row2.addCell(dateCell);
//                table.addCell(jsonObject.getString("id"));
                table_Row2.addCell(trans2Double(jsonObject.getString("First")).substring(0,4)+FIRSTunit);
                table_Row2.addCell(trans2Double(jsonObject.getString("Second")).substring(0,4)+SECONDunti);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return table_Row2;
    }

    private class TOCCreation extends PdfPageEventHelper{
        protected PdfOutline root;
        protected List<TOCEntry> toc = new ArrayList<TOCEntry>();

        public TOCCreation() {

        }
        public void setRoot(PdfOutline root) {
            this.root = root;
        }
        public List<TOCEntry> getToc() {
            return toc;
        }
        @Override
        public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {
            PdfDestination dest = new PdfDestination(PdfDestination.XYZ, rect.getLeft(), rect.getTop(), 0);
            new PdfOutline(root, dest, text);
            TOCEntry entry = new TOCEntry();
            entry.action = PdfAction.gotoLocalPage(writer.getPageNumber(), dest, writer);
            entry.title = text;
            toc.add(entry);
        }
    }
    private class TOCEntry {
        protected PdfAction action;
        protected String title;
    }
    //輔助PDF的(3排)
    public static final Rectangle[] COLUMNS_Row3 = {//X,Y,W,H
//            new Rectangle(36, 36, 192, 806),
//            new Rectangle(204, 36, 348, 806),
//            new Rectangle(360, 36, 504, 806)
            new Rectangle(48, 50, 297, 780),
            new Rectangle(298, 50, 547, 780),
//            new Rectangle(381, 50, 547, 780)
    };//192是第一區塊的寬度
    private PdfPTable createTable_Row3(int start, int end) throws IOException {
        table_Row3 = new PdfPTable(4);
        try {
            table_Row3.setWidths(new float[]{20,26,26,26});
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table_Row3.setWidthPercentage(100);
        table_Row3.setHorizontalAlignment(Element.ALIGN_RIGHT);
        for (int i = start; i <= end; i++) {//一頁90資料
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i-1);
                PdfPCell dateCell = new PdfPCell(Phrase.getInstance(jsonObject.getString("RecordDate").substring(5)));
                dateCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table_Row3.addCell(dateCell);
//                table.addCell(jsonObject.getString("id"));
                table_Row3.addCell(trans2Double(jsonObject.getString("First"))+FIRSTunit);
                table_Row3.addCell(trans2Double(jsonObject.getString("Second"))+SECONDunti);
                table_Row3.addCell(trans2Double(jsonObject.getString("Third"))+THIRDunit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return table_Row3;
    }


    //=======================================================
    /**
     * 處理文字
     */
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
    /**揀選標籤*/
    private String Lab(char name,int x) {
        switch (name){
            case 'T':
                return "Tempe.";

            case 'H':
                return "humidity";

            case 'C':
            case 'D':
            case 'E':
                return "CO2";

            case 'P':
                return "Pressure";

            case 'M':
                return "PM2.5";

            case 'Q':
                return "PM10";

            case 'O':
                return "噪音";

            case 'G':
                return "CO";

            case 'F':
                return "Noise";

            case 'I':
                if(x == 1){
                    return "First";
                }else if(x ==2){
                    return "Second";
                }else if(x == 3){
                    return "Third";
                }

                break;

        }
        return "不知道";
    }
}

