package test.tabbar6.Tab5_report;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.List;

import test.tabbar6.R;

public class Five_Fragment extends Fragment {

    private EditText license_a;
    private EditText other_color;
    //private String[] color_query;
    private EditText start_a;
    private EditText end_a;
    private EditText comment_a;
    private Spinner spinner;
    private CheckBox one_a;
    private CheckBox two_a;
    private CheckBox three_a;
    private RatingBar rate;
    private Button submit;
    private Button search;

    private String color_choose = "";
    private float rate_f = 0;

    private ProgressDialog dialog;
    private String objectId;

    //String[] color;//spinner
    private ArrayAdapter<String> dataAdapter;//อันเก่า
    Integer[] image = {R.drawable.ic_taxi_green_yellow_24dp, R.drawable.ic_taxi_yellow_24dp,
            R.drawable.ic_taxi_blue_24dp, R.drawable.ic_taxi_color_24dp};
    String[] color = {"เหลือง-เขียว", "เหลือง", "ฟ้า", "อื่นๆ"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_five, container, false);

        ParseUser currentUser = ParseUser.getCurrentUser();
        objectId = currentUser.getObjectId().toString();

        other_color = (EditText)rootView.findViewById(R.id.other);
        other_color.setEnabled(false);
        license_a = (EditText) rootView.findViewById(R.id.license_in);

        spinner = (Spinner) rootView.findViewById(R.id.spinner2);
        //final String[] color = {"เหลือง-เขียว", "เหลือง", "ฟ้า", "อื่นๆ"};
        //dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, color);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //spinner.setAdapter(dataAdapter);
        spinner.setAdapter(new SpinnerAdapter(getActivity(), R.layout.spinner_image, color, image));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (color[position] == "เหลือง-เขียว") {
                    other_color.setEnabled(false);
                    color_choose = "เหลือง-เขียว";
                } else if (color[position] == "เหลือง") {
                    other_color.setEnabled(false);
                    color_choose = "เหลือง";
                } else if (color[position] == "ฟ้า") {
                    other_color.setEnabled(false);
                    color_choose = "ฟ้า";
                } else if (color[position] == "อื่นๆ") {
                    other_color.setEnabled(true);
                } else return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }

        });//end spinner

        start_a = (EditText) rootView.findViewById(R.id.start_in);
        end_a = (EditText) rootView.findViewById(R.id.end_in);
        comment_a = (EditText) rootView.findViewById(R.id.comment_in);
        one_a = (CheckBox) rootView.findViewById(R.id.checkBox_1);
        two_a = (CheckBox) rootView.findViewById(R.id.checkBox_2);
        three_a = (CheckBox) rootView.findViewById(R.id.checkBox_3);
        submit = (Button) rootView.findViewById(R.id.report_submit);
        //search = (Button)findViewById(R.id.report_query);

        rate = (RatingBar) rootView.findViewById(R.id.ratingBar_report);
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating == 0.5) {
                    rate_f = rating;
                } else if (rating == 1) {
                    rate_f = rating;
                } else if (rating == 1.5) {
                    rate_f = rating;
                } else if (rating == 2) {
                    rate_f = rating;
                } else if (rating == 2.5) {
                    rate_f = rating;
                } else if (rating == 3) {
                    rate_f = rating;
                } else if (rating == 3.5) {
                    rate_f = rating;
                } else if (rating == 4) {
                    rate_f = rating;
                } else if (rating == 4.5) {
                    rate_f = rating;
                } else if (rating == 5) {
                    rate_f = rating;
                } else return;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {//------------click-------------------
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Reporting...");
                dialog.setCancelable(false);
                dialog.show();

                String License = license_a.getText().toString().trim();
                if (!(License.indexOf("-") == -1)) {
                    String[] split = License.split("-");
                    License = split[1] + split[0];
                }

                final String other_textColor = other_color.getText().toString();
                if (!"".equals(other_textColor)) {
                    color_choose = other_textColor;
                }

                final String Start = start_a.getText().toString();
                final String End = end_a.getText().toString();
                final String Comment = comment_a.getText().toString();

                //------------------------------------------start checkbox--------------------------
                int numCheck = 0;
                String[] a = new String[3];//0-2
                if (one_a.isChecked()) {//true
                    a[numCheck] = "ขับรถเร็ว";
                    numCheck++;
                }
                if (two_a.isChecked()) {
                    a[numCheck] = "ไม่สุภาพ";
                    numCheck++;
                }
                if (three_a.isChecked()) {
                    a[numCheck] = "ลวนลาม";
                    numCheck++;
                }

                String mix = "";
                for (int i = 0; i <= numCheck - 1; i++) {
                    if (i < numCheck - 1) {
                        int j = i + 1;
                        mix = mix + j + "." + a[i] + ", ";
                    } else if (i == numCheck - 1) {
                        int j = i + 1;
                        mix = mix + j + "." + a[i];
                    } else {
                        return;
                    }
                }//-----------------------------------------end checkbox----------------------------

                if (!(("".equals(License)) || ("0".equals(rate_f)) || ("".equals(Start)) || ("".equals(End)))) {

                    final String finalMix = mix;
                    //queryLicense(License, color_choose, finalMix, Start, End, Comment);
                    //-----------query License to update color------------------------------------------------------
                    try {
                        final String[] license_id = new String[1];
                        final ParseQuery<ParseObject> query_license = ParseQuery.getQuery("Taxi");
                        query_license.whereEqualTo("License", License);
                        //final String finalLicense = License;
                        List<ParseObject> ob = query_license.find();
                        if (ob.size() > 0) {
                            for (ParseObject taxi : ob) {
                                license_id[0] = taxi.getObjectId().toString();
                            }

                            updateColorTaxi(License, license_id, color_choose, finalMix, Start, End, Comment);
                        } else {
                            saveTaxi(License, color_choose, finalMix, Start, End, Comment);
                        }

                    } catch (Exception e) {
                        Log.d("query taxi license", "Error: " + e.getMessage());
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(getActivity(),
                            "Please insert data complete",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        return rootView;
    }

    void updateColorTaxi(final String license_c, final String[] license_id2, final String color_c, final String finalMix, final String Start, final String End, final String Comment) {
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Taxi");
        query2.whereEqualTo("License", license_c);
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    //final TextView show = (TextView)findViewById(R.id.show_idTaxi);
                    //show.setText(Arrays.toString(license_id2));
                    ParseObject taxi_color = list.get(0);
                    taxi_color.put("Color", color_c);
                    taxi_color.saveInBackground();

                    saveReport(license_c, Start, End, finalMix, Comment, license_id2);
                } else {
                    Log.d("update color", "Error: " + e.getMessage());
                }
            }
        });
    }

    void saveTaxi(final String license, final String color_c, final String finalMix, final String Start, final String End, final String Comment) {
        final String[] id = new String[1];//id taxi
        final ParseObject taxi = new ParseObject("Taxi");
        taxi.put("License", license);
        taxi.put("Color", color_c);
        taxi.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    id[0] = taxi.getObjectId().toString();//get id taxi à¹€à¸žà¸·à¹ˆà¸­à¹ƒà¸ªà¹ˆà¹€à¸›à¹‡à¸™fk à¹ƒà¸™ table report
                    //final TextView show = (TextView)findViewById(R.id.show_idTaxi);
                    //show.setText(Arrays.toString(id));
                    saveReport(license, Start, End, finalMix, Comment, id);
                } else {
                    Log.d("query taxi id", "Error: " + e.getMessage());
                }
            }
        });
    }

    void saveReport(String license, String Start, String End, String mix, String Comment, String[] id) {
        ParseObject report = new ParseObject("Report");
        report.put("Start", Start);
        report.put("End", End);
        report.put("Rating", String.valueOf(rate_f));
        report.put("Action", mix);
        report.put("Comment", Comment);
        report.put("user_id", ParseObject.createWithoutData("_User", objectId));
        report.put("taxi_id", ParseObject.createWithoutData("Taxi", id[0]));
        report.put("License_report", license);
        report.saveInBackground();

        dialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Report");
        builder.setMessage("Report is success");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        license_a.setText("");
                        other_color.setText("");
                        other_color.setEnabled(false);
                        start_a.setText("");
                        end_a.setText("");
                        comment_a.setText("");
                        spinner.setSelection(dataAdapter.getPosition("yellow-green"));
                        rate.setRating(0);
                        one_a.setChecked(false);
                        two_a.setChecked(false);
                        three_a.setChecked(false);
                        return;
                    }
                }

        );
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public class SpinnerAdapter extends ArrayAdapter<String> {
        public SpinnerAdapter(Context context, int textViewResourceId,String[] textColor, Integer[] imageColor) {
            super(context, textViewResourceId, textColor);
        }
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }


        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {
            // TODO Auto-generated method stub
            // return super.getView(position, convertView, parent);

            LayoutInflater inflater = getLayoutInflater(null);
            View row = inflater.inflate(R.layout.spinner_image, parent, false);
            TextView label = (TextView) row.findViewById(R.id.color);
            label.setText(color[position]);

            ImageView icon = (ImageView) row.findViewById(R.id.icon);
            icon.setImageResource(image[position]);
            return row;
        }
    }
}