package test.tabbar6.Tab5_report;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import test.tabbar6.R;

public class report_Fragment extends Fragment {

    private ProgressDialog progressDialog;

    private AutoCompleteTextView autoComplete;
    private ArrayAdapter<String> adapter;
    //private TextView show;
    private String[] strings;
    private String selection;//license à¸ˆà¸²à¸autocomplete
    //String[] strings1 = { "apple", "banana", "graps", "orrange", "berry" };
    // -------------------------------end autoComplete----------------------------
    ListView listView_report;
    private ArrayList<ReportInfo> listData = new ArrayList<ReportInfo>();
    ReportAdapter reportAdapter;
    //List<ParseObject> obReport;
    private Button search;
    private float rating_query;
    private String checkbox_query;
    private String comment_query;
    private String usernameReport_query;
    private Date dateTime;
    //-------------------------------end listview---------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.report_main, container, false);

        final Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //show = (TextView)rootView.findViewById(R.id.show);

        search = (Button)rootView.findViewById(R.id.report_query);
        listView_report = (ListView)rootView.findViewById(R.id.listView_report);
        reportAdapter = new ReportAdapter(getActivity().getBaseContext(),listData);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), addReport_Fragment.class);
                //startActivity(intent);

                Toast.makeText(getActivity(),
                        "ADD REPORT",
                        Toast.LENGTH_SHORT).show();

            }
        });

        ParseQuery<ParseObject> queryAllReport = ParseQuery.getQuery("Report");
        queryAllReport.orderByDescending("createdAt");
        queryAllReport.include("user_id");
        try {
            List<ParseObject> listAll = queryAllReport.find();
            if (listAll.size()>0){
                for (ParseObject con : listAll) {
                    ParseObject user_report = con.getParseObject("user_id");
                    selection = con.getString("License_report");
                    rating_query = Float.valueOf(con.getString("Rating"));
                    checkbox_query = con.getString("Action");
                    comment_query = con.getString("Comment");
                    String duration_s = formatter.format(con.getCreatedAt());
                    usernameReport_query = user_report.getString("username");
                    listData.add(new ReportInfo(selection, checkbox_query, comment_query, rating_query, duration_s, usernameReport_query));
                }
                listView_report.setAdapter(reportAdapter);
            }
            else {
                Toast.makeText(getActivity(),
                        "ไม่มีประวัติการรายงานพฤติกรรม",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Taxi");//----autoComplete--------------
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    strings = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        strings[i] = (String) list.get(i).getString("License");

                    }
                    //show.setText(Arrays.toString(strings));//show all license
                    mySetAdapter();

                    //if(list.size() < 40)
                    //    autoComplete.setThreshold(1);
                    //else
                    //    autoComplete.setThreshold(2);
                } else {
                    Log.d("autocomplete", "Error: " + e.getMessage());
                }

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData.clear();

                final ProgressDialog dialog = new ProgressDialog(getActivity());

                InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                dialog.setMessage("กำลังค้นหา...");
                dialog.setCancelable(false);
                dialog.show();

                if(selection == ""){//à¹„à¸¡à¹ˆà¸¡à¸µà¹ƒà¸™autocomplete
                    dialog.dismiss();
                    //progressDialog = ProgressDialog.show(getActivity(), "Please wait.", "Fetching route information.", true);
                    /*Toast.makeText(getActivity(),
                            "เลขทะเบียนนี้ไม่มีประวัติการรายงาน",
                            Toast.LENGTH_SHORT).show();*/

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("เลขทะเบียนนี้ไม่มีประวัติการรายงาน");
                    builder.setCancelable(false);

                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    final Timer timer2 = new Timer();
                    timer2.schedule(new TimerTask() {
                        public void run() {
                            alertDialog.dismiss();
                            timer2.cancel(); //this will cancel the timer of the system
                        }
                    }, 2000); // the timer will count 5 seconds....

                }
                else {
                    final ParseQuery<ParseObject> queryReport = ParseQuery.getQuery("Report");//-------List----------
                    queryReport.whereEqualTo("License_report", selection);
                    queryReport.orderByDescending("createdAt");
                    queryReport.include("user_id");
                    queryReport.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (e == null) {
                                if (list.size() > 0) {
                                    dialog.dismiss();
                                    for (ParseObject con : list) {
                                        ParseObject user_report = con.getParseObject("user_id");
                                        rating_query = Float.valueOf(con.getString("Rating"));
                                        checkbox_query = con.getString("Action");
                                        comment_query = con.getString("Comment");
                                        String duration_s = formatter.format(con.getCreatedAt());
                                        usernameReport_query = user_report.getString("username");
                                        listData.add(new ReportInfo(selection, checkbox_query, comment_query, rating_query, duration_s, usernameReport_query));
                                    }
                                } else {//ไม่มีประวัติการรีพอต
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(),
                                            "เลขทะเบียนนี้ไม่มีประวัติการรายงาน",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d("Query Report List", "Error: " + e.getMessage());
                            }
                            listView_report.setAdapter(reportAdapter);
                        }
                    });
                }
            }
        });

        return rootView;
    }
    void mySetAdapter(){
        autoComplete = (AutoCompleteTextView) getActivity().findViewById(R.id.autoComplete);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, strings);
        autoComplete.setThreshold(1);
        autoComplete.setAdapter(adapter);
        //autoComplete.setOnItemSelectedListener(this);
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getBaseContext(), "Position:" + position + " License:" + parent.getItemAtPosition(position),
                        Toast.LENGTH_LONG).show();//Position: 0-1-... License: ?
                selection = (String) parent.getItemAtPosition(position);

                Log.d("AutocompleteContacts", "Position:" + position + " License:" + parent.getItemAtPosition(position));
            }
        });
    }


    /*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Autocomplete", "onItemSelected() position " + position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }*/

}
