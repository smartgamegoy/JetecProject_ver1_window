package com.example.jetecpro_ver1.AllOfNewMonitor.Controll.Pagers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport.NewNDD_ModifiedDataSendOut;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NDD_NewDataDisplaySupport.NewSupportSortData;
import com.example.jetecpro_ver1.AllOfNewMonitor.Model.NewSendType;
import com.example.jetecpro_ver1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class NormalDataSetting extends RelativeLayout {
    private String TAG = NormalDataSetting.class.getSimpleName();
    private View mView;
    MyRecyclerView mAdapter;
    Activity mActivity;
    int getArea;
    ArrayList<Integer> mGetTabs;
    private ArrayList<HashMap<String, String>> displayHashMap = new ArrayList<>();

    public NormalDataSetting(Context context, HashMap<String, ArrayList<String>> getFromIntentArray
            , int area, Activity activity, ArrayList<Integer> getTab) {//area是該編號的位置
        super(context);
        mGetTabs = getTab;
        mActivity = activity;
        displayHashMap.clear();
        getArea = area;
        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.new_ndd_recycler_item_normal_setting, null);
        RecyclerView r = mView.findViewById(R.id.recyclerview_NDD_normal_data_set);
        Button btSend = mView.findViewById(R.id.button_NDD_NormalSendOut);
//        Log.d(TAG, "NormalDataSetting: "+getFromIntentArray);
//        Log.d(TAG, "NormalDataSetting: (位置)" + area);
        for (int i = 0; i < getFromIntentArray.get("Byte").size(); i++) {
            NewSupportSortData s = new NewSupportSortData(area, getFromIntentArray.get("Byte").get(i), activity);
            HashMap<String, String> hashMap = new HashMap<>();
            if (!s.ReBackTitle().matches("xx")) {
                hashMap.put("Title", s.ReBackTitle());
                hashMap.put("Value", s.ReBackValues());
                displayHashMap.add(hashMap);
            }
        }

        String TypeTag = NewSendType.newDeviceType.substring(5, NewSendType.newDeviceType.lastIndexOf("-"));
        try {
            switch (TypeTag.charAt(area - 1)) {
                case 'T':
                case 'H':
                case 'C':
                case 'D':
                case 'E':
                case 'M':
                case 'O':
                case 'P':
                case 'Q':
                case 'I':
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("Title", trans(R.string.new_Decimal));
                    String getDP = "-";
                    for (int i = 0; i < getFromIntentArray.get("Byte").size(); i++) {
                        NewSupportSortData s = new NewSupportSortData(area, getFromIntentArray.get("Byte").get(i), activity);
                        if (!s.getDP().matches("-")) {
                            getDP = s.getDP();
//                            Log.d(TAG, "NormalDataSetting: "+getDP);
                        }
                    }
                    hashMap.put("Value", getDP);
                    displayHashMap.add(hashMap);
                    break;
            }
        } catch (Exception e) {

        }


//        Log.d(TAG, "NormalDataSetting: "+displayHashMap);
        /**按鈕在這邊*/
        btSend.setOnClickListener((v -> {
//            Log.d(TAG, "NormalDataSetting: " + area);
//            Log.d(TAG, "NormalDataSetting: " + displayHashMap);
            NewNDD_ModifiedDataSendOut send = new NewNDD_ModifiedDataSendOut(area,displayHashMap,activity,getTab);
            send.sendOut();
        }));
        r.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new MyRecyclerView();
        r.setAdapter(mAdapter);

        addView(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    private class MyRecyclerView extends RecyclerView.Adapter<MyRecyclerView.ViewHolder> {
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvValue;
            View mView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.textView_NDD_CellTitle);
                tvValue = itemView.findViewById(R.id.textView_NDD_CellValue);
                mView = itemView;
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = mActivity.getLayoutInflater().inflate(R.layout.recycleview_item, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.tvTitle.setText(displayHashMap.get(i).get("Title"));
            viewHolder.tvValue.setText(displayHashMap.get(i).get("Value"));
            viewHolder.mView.setOnClickListener((v -> {
                getClick(viewHolder.tvTitle.getText().toString()
                        , viewHolder.tvValue.getText().toString());
            }));


        }

        @Override
        public int getItemCount() {
            return displayHashMap.size();
        }


    }

    /**
     * 修改資料
     */
    private void getClick(String title, String value) {
//        Log.d(TAG, "getClick: " + title + ", " + getArea);
//        Log.d(TAG, "getClick: " + value);
//        Log.d(TAG, "getClick: " + displayHashMap);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
        NewSupportSortData s = new NewSupportSortData(getArea, title, mActivity);

        if (title.matches(trans(R.string.PV))
                || title.matches(trans(R.string.EH))
                || title.matches(trans(R.string.EL))
                || title.matches(trans(R.string.IH))
                || title.matches(trans(R.string.IL))
                || title.matches(trans(R.string.CR))
        ) {
            setNormal(title, mBuilder);/**一般數值選擇*/

        } else if (title.contains(trans(R.string.RL123))) {
            setSwitch(title, mBuilder);/**警報開關*/

        } else if (title.contains(trans(R.string.RL456))) {
            setOutput(title, mBuilder, s);/**第7項的輸出*/

        } else if (title.contains(trans(R.string.new_Decimal))) {
            serDecimal(title, value, mBuilder);/**修改小數點*/
        }
    }


    /**
     * 第7項的輸出
     */
    private void setOutput(String title, AlertDialog.Builder mBuilder, NewSupportSortData s) {
        View view;
        Button btSendOut;
        Button btCancel;
        AlertDialog dialog;
        view = LayoutInflater.from(mActivity).inflate(R.layout.new_picker_dislog_setting, null);
        mBuilder.setView(view);
        btSendOut = view.findViewById(R.id.button_picker_NDD_modify);
        btCancel = view.findViewById(R.id.button_picker_NDD_cancel);
        NumberPicker npSelect = view.findViewById(R.id.numberPicker_picker_NDD_select);
        TextView tvPickerTitle = view.findViewById(R.id.textView_picker_NDD_modifyTitle);
        tvPickerTitle.setText(title);
        ArrayList<String> outputSelect = new ArrayList<>();
        for (int i = 0; i < mGetTabs.size(); i++) {
            if (mGetTabs.get(i) < 7) {
//                    Log.d(TAG, "getClick: " + str.charAt(mGetTabs.get(i) - 1));
                outputSelect.add(String.valueOf(s.RL456GetValue(mGetTabs.get(i) - 1)));
            }
        }
        if (outputSelect.contains(" ")) {
            outputSelect.set(outputSelect.indexOf(" "), trans(R.string.new_none));
        }
        npSelect.setWrapSelectorWheel(false);
        npSelect.setMinValue(0);
        npSelect.setMaxValue(outputSelect.size() - 1);
        String[] data = new String[outputSelect.size() - 1];
        data = outputSelect.toArray(data);
        npSelect.setDisplayedValues(data);

        dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        String[] finalData = data;
        btSendOut.setOnClickListener(v -> {
//                Log.d(TAG, "getClick: "+npSelect.getValue());
//                Log.d(TAG, "getClick: "+ finalData[npSelect.getValue()]);
            displayHashMap.get(getIndexFormTitle(title, displayHashMap)).put("Value", finalData[npSelect.getValue()]);
            mAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show();
    }

    /**
     * 警報開關
     */
    private void setSwitch(String title, AlertDialog.Builder mBuilder) {
        View view;
        Button btSendOut;
        Button btCancel;
        AlertDialog dialog;
        view = LayoutInflater.from(mActivity).inflate(R.layout.new_switch_dialog_setting, null);
        mBuilder.setView(view);
        btSendOut = view.findViewById(R.id.button_switch_NDD_modify);
        btCancel = view.findViewById(R.id.button_switch_NDD_cancel);
        Switch swSwitch = view.findViewById(R.id.switch_switch_NDD_Switch);
        TextView tvSwitchTitle = view.findViewById(R.id.textView_switch_NDD_modifyTitle);
        tvSwitchTitle.setText(title);
        dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        AtomicBoolean checkStatus = new AtomicBoolean(false);
        swSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkStatus.set(true);
            } else {
                checkStatus.set(false);
            }
        });

        btSendOut.setOnClickListener(v -> {
//                Log.d(TAG, "getClick: (拿到index)"+displayHashMap.get(getIndexFormTitle(title,displayHashMap)));
//                Log.d(TAG, "getClick: (取得按鈕狀態)"+checkStatus.get());
            String status = checkStatus.get() ? "開" : "關";
            displayHashMap.get(getIndexFormTitle(title, displayHashMap)).put("Value", status);
            mAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show();
    }

    /**
     * 修改小數點
     */
    private void serDecimal(String title, String value, AlertDialog.Builder mBuilder) {
        View view;
        Button btSendOut;
        Button btCancel;
        AlertDialog dialog;
        if (!value.matches("-")) {
            view = LayoutInflater.from(mActivity).inflate(R.layout.new_picker_dislog_setting, null);
            mBuilder.setView(view);
            btSendOut = view.findViewById(R.id.button_picker_NDD_modify);
            btCancel = view.findViewById(R.id.button_picker_NDD_cancel);
            NumberPicker npSelect = view.findViewById(R.id.numberPicker_picker_NDD_select);
            TextView tvPickerTitle = view.findViewById(R.id.textView_picker_NDD_modifyTitle);
            tvPickerTitle.setText(title);
            npSelect.setMinValue(0);
            npSelect.setMaxValue(3);
            npSelect.setValue(Integer.parseInt(value));
            dialog = mBuilder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            btCancel.setOnClickListener(v -> {
                dialog.dismiss();
            });
            btSendOut.setOnClickListener(v -> {
                displayHashMap.get(getIndexFormTitle(title, displayHashMap)).put("Value", String.valueOf(npSelect.getValue()));
                for (int i = 0; i < displayHashMap.size(); i++) {
//                    Log.d(TAG, "getClick: " + displayHashMap.get(i));
                    if (displayHashMap.get(i).get("Title").matches(trans(R.string.EH))
                            || displayHashMap.get(i).get("Title").matches(trans(R.string.EL))
                            || displayHashMap.get(i).get("Title").matches(trans(R.string.IH))
                            || displayHashMap.get(i).get("Title").matches(trans(R.string.IL))
                            || displayHashMap.get(i).get("Title").matches(trans(R.string.CR))
                            || displayHashMap.get(i).get("Title").matches(trans(R.string.PV))) {
                        displayHashMap.get(i).put("Value", String.format("%." + npSelect.getValue() + "f"
                                , Double.parseDouble(displayHashMap.get(i).get("Value"))));
                    }
                }
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            });

            dialog.show();
        }
    }

    /**
     * 一般數值選擇
     */
    private void setNormal(String title, AlertDialog.Builder mBuilder) {
        View view;
        EditText editText;
        Button btCancel;
        Button btSendOut;
        AlertDialog dialog;
        view = LayoutInflater.from(mActivity).inflate(R.layout.new_normal_modify_setting, null);
        mBuilder.setView(view);
        editText = view.findViewById(R.id.editText_NDD_modify);
        btCancel = view.findViewById(R.id.button_NDD_cancel);
        btSendOut = view.findViewById(R.id.button_NDD_modify);
        TextView tvNDDTitle = view.findViewById(R.id.textView_NDD_modifyTitle);
        editText.setHint(getHint(getArea, title, displayHashMap
                .get(getIndexFormTitle(trans(R.string.new_Decimal), displayHashMap)).get("Value")));
        tvNDDTitle.setText(title);
        dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        String getDPV = displayHashMap.get(getIndexFormTitle(trans(R.string.new_Decimal), displayHashMap)).get("Value");
        switch (Integer.parseInt(getDPV)) {
            case 0:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                break;
            default:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
        }

        editText.setFilters(new InputFilter[]{lengthFilter});/**根據小數點限制輸入的小數點位數*/
        String getHint = editText.getHint().toString();
        double getMin = Double.parseDouble(getHint.substring(0, getHint.lastIndexOf("~")));
        double getMax = Double.parseDouble(getHint.substring(getHint.indexOf("~") + 1));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!s.toString().matches("-")) {
                        if (Double.parseDouble(String.valueOf(s)) > getMax) {
                            editText.setText(String.valueOf(getMax).substring(0, String.valueOf(getMax).indexOf(".")));

                        } else if (Double.parseDouble(String.valueOf(s)) < getMin) {
                            editText.setText(String.valueOf(getMin).substring(0, String.valueOf(getMin).indexOf(".")));
//                                editText.setSelection(editText.getText().length());
                        }
                        if (s.toString().length() > 1 && s.toString().startsWith("0")) {
                            s.replace(0, 1, "");

                        } else if (s.toString().length() > 1 && s.toString().startsWith("-")) {
                            if (s.toString().charAt(1) == '0') {
                                s.replace(1, 2, "");
                                editText.setSelection(editText.getText().length());
                            }
                        }
                    }

                } catch (Exception e) {
                    editText.setText("0");
                }

            }
        });
        btCancel.setOnClickListener((v -> {
            dialog.dismiss();
        }));
        btSendOut.setOnClickListener((v -> {
//                Log.d(TAG, "getClick:(取得index) " + getIndexFormTitle(title, displayHashMap));
            if (editText.getText().length() > 0) {

                displayHashMap.get(getIndexFormTitle(title, displayHashMap)).put("Value", editText.getText().toString());
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }


        }));

        dialog.show();
    }

    /**
     * 根據小數點限制輸入的小數點位數
     */
    private InputFilter lengthFilter = (source, start, end, dest, dstart, dend) -> {
        // source:当前输入的字符
        // start:输入字符的开始位置
        // end:输入字符的结束位置
        // dest：当前已显示的内容
        // dstart:当前光标开始位置
        // dent:当前光标结束位置

        if (dest.length() == 0 && source.equals(".")) {
            return "0.";
        }
        String dValue = dest.toString();
        String[] splitArray = dValue.split("\\.");
        if (splitArray.length > 1) {
            String dotValue = splitArray[1];
            int dp = Integer.parseInt(displayHashMap
                    .get(getIndexFormTitle(trans(R.string.new_Decimal), displayHashMap)).get("Value"));
            if (dotValue.length() == dp) {
                return "";
            }
        }
        return null;

    };

    /**
     * 取得提示值
     */
    private String getHint(int area, String title, String dp) {
        String str = NewSendType.newDeviceType.substring(5, NewSendType.newDeviceType.lastIndexOf("-"));
        switch (str.charAt(area - 1)) {
            case 'T':
                if (title.matches(trans(R.string.PV))) {
                    return String.format("%." + dp + "f", -5.0) + "~" + String.format("%." + dp + "f", 5.0);
                } else {
                    return String.format("%." + dp + "f", -10.0) + "~" + String.format("%." + dp + "f", 65.0);
                }
            case 'H':
                if (title.matches(trans(R.string.PV))) {
                    return String.format("%." + dp + "f", -20.0) + "~" + String.format("%." + dp + "f", 20.0);
                } else {
                    return String.format("%." + dp + "f", 0.0) + "~" + String.format("%." + dp + "f", 100.0);
                }
            case 'C':
                if (title.matches(trans(R.string.PV))) {
                    return String.format("%." + dp + "f", -500.0) + "~" + String.format("%." + dp + "f", 500.0);
                } else {
                    return String.format("%." + dp + "f", 0.0) + "~" + String.format("%." + dp + "f", 2000.0);
                }
            case 'D':
                if (title.matches(trans(R.string.PV))) {
                    return String.format("%." + dp + "f", -500.0) + "~" + String.format("%." + dp + "f", 500.0);
                } else {
                    return String.format("%." + dp + "f", 0.0) + "~" + String.format("%." + dp + "f", 3000.0);
                }
            case 'E':
                if (title.matches(trans(R.string.PV))) {
                    return String.format("%." + dp + "f", -500.0) + "~" + String.format("%." + dp + "f", 500.0);
                } else {
                    return String.format("%." + dp + "f", 0.0) + "~" + String.format("%." + dp + "f", 5000.0);
                }
            case 'M':
            case 'P':
            case 'Q':
                if (title.matches(trans(R.string.PV))) {
                    return String.format("%." + dp + "f", -10.0) + "~" + String.format("%." + dp + "f", 10.0);
                } else {
                    return String.format("%." + dp + "f", 0.0) + "~" + String.format("%." + dp + "f", 1000.0);
                }
            case 'O':
                if (title.matches(trans(R.string.PV))) {
                    return String.format("%." + dp + "f", -10.0) + "~" + String.format("%." + dp + "f", 10.0);
                } else {
                    return String.format("%." + dp + "f", 30.0) + "~" + String.format("%." + dp + "f", 130.0);
                }

            case 'I':
            case 'R':
                if (title.matches(trans(R.string.PV))) {
                    return String.format("%." + dp + "f", -999.0) + "~" + String.format("%." + dp + "f", 9999.0);
                } else {
                    return String.format("%." + dp + "f", -999.0) + "~" + String.format("%." + dp + "f", 9999.0);
                }
            default:
                return " ";

        }

    }


    /**
     * 反向從ArrayList<HashMap<String,String>> 內挖取ArrayList index 的副程式
     */
    private int getIndexFormTitle(String title, ArrayList<HashMap<String, String>> arrayList) {
        int i;
        for (i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).get("Title").contains(title)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 處理文字
     */
    private String trans(int name) {
        String str = mActivity.getResources().getString(name);
        return str;
    }

}
