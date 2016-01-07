package test.tabbar6;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import test.tabbar6.Tab1_follow_me.One_Fragment;
import test.tabbar6.Tab2_meter.GPSTracker;
import test.tabbar6.Tab2_meter.Two_Fragment;
import test.tabbar6.Tab3_fake_call.Three_Fragment;
import test.tabbar6.Tab4_found_lost.Four_Fragment;
import test.tabbar6.Tab5_report.Five_Fragment;

public class MainActivity extends AppCompatActivity
{
    GPSTracker gps;

    private double lat;
    private double lon;

    public SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(mViewPager);


        //////////////////////////////////////
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    lat = gps.getLatitude();
                    lon = gps.getLongitude();
                }
                else{
                    gps.showSettingsAlert();
                }

                String phoneNumber = "0982595770";
                String userMessage = "ฉันอยู่ที่นี่";
                String message = userMessage+"\n"+"http://maps.google.com/?q=" + lat+","+lon;
                sendSMS(phoneNumber,message);
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mViewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        showToast("Follow Me");
                        break;
                    case 1:
                        showToast("Meter");
                        break;
                    case 2:
                        showToast("Fake Call");
                        break;
                    case 3:
                        showToast("Found/Lost");
                        break;
                    case 4:
                        showToast("Report");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void showToast(String three) {
        Toast.makeText(this, three, Toast.LENGTH_SHORT).show();
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //adapter.addFrag(new DummyFragment(getResources().getColor(R.color.purple)), "ONE");
        //adapter.addFrag(new DummyFragment(getResources().getColor(R.color.deep_purple)), "TWO");
        //adapter.addFrag(new DummyFragment(getResources().getColor(R.color.indigo)), "THREE");
        //adapter.addFrag(new DummyFragment(getResources().getColor(R.color.blue)), "FOUR");
        adapter.addFrag(new DummyFragment(getResources().getColor(R.color.light_blue)), "FIVE");
        viewPager.setAdapter(adapter);
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
        if (id == R.id.action_user) {
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_history) {
            return true;
        }
        if (id == R.id.action_logout) {
            openDialogLogout();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            //return mFragmentList.get(position);
            if (position == 0)
                return new One_Fragment();
            else if (position == 1)
                return new Two_Fragment();
            else if (position == 2)
                return new Three_Fragment();
            else if (position == 3)
                return new Four_Fragment();
            else if(position == 4)
                return new Five_Fragment();
            return null;

        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "FOLLOW ME";
                case 1:
                    return "METER";
                case 2:
                    return "FAKE CALL";
                case 3:
                    return "LOST";
                case 4:
                    return "REPORT";

            }
            return null;
        }
    }

    private class DummyFragment extends Fragment {
        int color;

        //ImageButton icon_menu;
        //int[] img = new int[]{R.drawable.ic_maps,R.drawable.ic_meter,R.drawable.ic_fakecall,R.drawable.ic_find_lost,R.drawable.ic_report};

        //private static final String ARG_SECTION_NUMBER = "section_number";

        public DummyFragment() {

        }

        @SuppressLint("ValidFragment")
        public DummyFragment(int color) {
            this.color = color;
        }

        /*public DummyFragment newInstance(int sectionNumber){
            DummyFragment fragment = new DummyFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }*/

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_two, container, false);

            final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
            frameLayout.setBackgroundColor(color);

            //RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
            //recyclerView.setLayoutManager(linearLayoutManager);
            //recyclerView.setHasFixedSize(true);

            //View view = inflater.inflate(R.layout.fragment_main, container, false);
            //TextView textView = (TextView) view.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            //icon_menu = (ImageButton) view.findViewById(R.id.section_img);
            //icon_menu.setBackgroundColor(img[getArguments().getInt(ARG_SECTION_NUMBER)-1]);

            return view;

        }
    }


    public void openDialog() {
        final Dialog dialog = new Dialog(MainActivity.this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_sos);
        dialog.setTitle("ช่วยด้วยยยยย!!!");


        dialog.show();
    }

    public void openDialogLogout() {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("กำลังออกจากระบบ...");
        dialog.setCancelable(false);
        dialog.show();

        ParseUser.logOut();
        Intent intent = new Intent(MainActivity.this, login.class);
        startActivity(intent);
        finish();
    }

    public void sendSMS(String phoneNumber,String message) {
        SmsManager smsManager = SmsManager.getDefault();


        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage(message);
        int messageCount = parts.size();

        Log.i("Message Count", "Message Count: " + messageCount);

        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        for (int j = 0; j < messageCount; j++) {
            sentIntents.add(sentPI);
            deliveryIntents.add(deliveredPI);
        }

        // ---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        // ---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {

                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
           /* sms.sendMultipartTextMessage(phoneNumber, null, parts, sentIntents, deliveryIntents); */
    }
}
