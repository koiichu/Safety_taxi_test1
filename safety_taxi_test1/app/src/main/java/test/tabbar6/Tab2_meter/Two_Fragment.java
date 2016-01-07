package test.tabbar6.Tab2_meter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.InjectView;
import test.tabbar6.R;

/**
 * Created by KMITL on 05/01/2016.
 */
public class Two_Fragment extends Fragment implements RoutingListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks
{

    protected View mView;

    private double distance;
    private double  time;
    private double price;

    protected LatLng start;
    protected LatLng end;


    protected AutoCompleteTextView autoStart;
    protected AutoCompleteTextView autoDestination;

    /*@InjectView(R.id.start)
    AutoCompleteTextView starting;
    @InjectView(R.id.destination)
    AutoiCompleteTextVew destination;*/

    @InjectView(R.id.send)
    Button send;

    Button send_auto;
    private TextView TimeCal;
    private TextView DistanceCal;
    private TextView PriceCal;

    private String LOG_TAG = "MyActivity";
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutoCompleteAdapter mAdapter;
    private ProgressDialog progressDialog;

    private static final LatLngBounds BOUNDS_JAMAICA= new LatLngBounds(new LatLng(-57.965341647205726, 144.9987719580531),
            new LatLng(72.77492067739843, -9.998857788741589));


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_two, container, false);

        TimeCal = (TextView) rootView.findViewById(R.id.TimeCal);
        DistanceCal = (TextView) rootView.findViewById(R.id.DistanceCal);
        PriceCal = (TextView) rootView.findViewById(R.id.PriceCal);
        autoStart = (AutoCompleteTextView)rootView.findViewById(R.id.autoStart);
        autoDestination = (AutoCompleteTextView)rootView.findViewById(R.id.autoDestination);

        mGoogleApiClient = new GoogleApiClient.Builder(rootView.getContext().getApplicationContext())
                .addApi(Places.GEO_DATA_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();


        mAdapter = new PlaceAutoCompleteAdapter(rootView.getContext().getApplicationContext(), android.R.layout.simple_list_item_activated_1
                ,
                mGoogleApiClient, BOUNDS_JAMAICA, null);


        autoStart.setAdapter(mAdapter);
        autoDestination.setAdapter(mAdapter);

        send_auto = (Button) rootView.findViewById(R.id.send);
        send_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getActivity(), "Hi!!", Toast.LENGTH_SHORT).show();
                sendRequest();
            }
        });




        autoStart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i(LOG_TAG, "Autocomplete item selected: " + item.description);


                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            // Request did not complete successfully
                            Log.e(LOG_TAG, "Place query did not complete. Error: " + places.getStatus().toString());//places.getStatus().toString())
                            places.release();
                            return;
                        }
                        // Get the Place object from the buffer.
                        final Place place = places.get(0);

                        start = place.getLatLng();

                    }
                });

            }
        });
        //autocomplete
        autoDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PlaceAutoCompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                Log.i(LOG_TAG, "Autocomplete item selected: " + item.description);


                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            // Request did not complete successfully
                            Log.e(LOG_TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        // Get the Place object from the buffer.
                        final Place place = places.get(0);

                        end = place.getLatLng();
                        System.out.print(place.getLatLng());

                    }
                });

            }
        });


        autoStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int startNum, int before, int count) {
                if (start != null) {
                    start = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autoDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if(end!=null)
                {
                    end=null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.mView = rootView;
        return mView;
    }


    public void sendRequest()
    {
        if(Util.Operations.isOnline(this.getActivity()))
        {
            //Toast.makeText(this.getActivity(), "Hi!!", Toast.LENGTH_SHORT).show();

            InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

            //create route;
            route();
        }
        else
        {
            Toast.makeText(this.getActivity(), "No internet connectivity", Toast.LENGTH_SHORT).show();
        }

    }

    public void route()
    {


        if(start==null || end==null)
        {
            if(start==null)
            {
                if(autoStart.getText().length()>0)
                {
                    autoStart.setError("Choose location from dropdown.");
                }
                else
                {
                    Toast.makeText(getActivity(),"Please choose a starting point.",Toast.LENGTH_SHORT).show();
                }
            }
            if(end==null)
            {
                if(autoDestination.getText().length()>0)
                {
                    autoDestination.setError("Choose location from dropdown.");
                }
                else
                {
                    Toast.makeText(getActivity(),"Please choose a destination.",Toast.LENGTH_SHORT).show();
                }
            }
        }

        else
        {

            progressDialog = ProgressDialog.show(getActivity(), "Please wait.", "Fetching route information.", true);
            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(start, end)
                    .build();
            routing.execute();
        }
    }
    @Override
    public void onRoutingFailure()
    {
        progressDialog.dismiss();
        Toast.makeText(getActivity(),"Something went wrong, Try again", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(final ArrayList<Route> route, int shortestRouteIndex)
    {
        progressDialog.dismiss();

        String message = "Choose Path";
        //final String[] path = new String[i];

        int n = route.size();
        String[] path = new String[n];


        for (int i = 0; i < route.size(); i++)
        {
            path[i] = route.get(i).getEndAddressText()+" Distance: "+route.get(i).getDistanceText();
        }

        if(route.size()>1)
        {
            new MaterialDialog.Builder(getActivity())
                    .title(message)
                    .items(path)
                    .itemsCallback(new MaterialDialog.ListCallback()
                    {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text)
                        {
                            //String clickedItemValue = Arrays.asList(Colors).get(which);
                            //tv.setTextColor(Color.parseColor(clickedItemValue));

                            distance = route.get(which).getDistanceValue() / 1000.00;
                            time = Math.round(route.get(which).getDurationValue() / 60.0);
                            CalMeter();
                        }
                    })
                    .show();
        }

        else
        {
            //add route(s) to the map.
            for (int i = 0; i < route.size(); i++)
            {

                //In case of more than 5 alternative routes
                distance  = route.get(i).getDistanceValue() / 1000.000;
                time = Math.round(route.get(i).getDurationValue() / 60.00);

                CalMeter();

            }
        }


    }

    public void CalMeter()
    {
        int t = 9*2;

        if (distance <= 1) price = 35.0;
        else if  (distance <=10) 	price = 35 + (distance -1)*5.5;
        else if (distance <=20) 	price =	84.5 + (distance -10)*6.5;
        else if (distance <=40) 	price = 149.5 + (distance -20)*7.5;
        else if (distance <=60) 	price = 299.5 + (distance -40)*8.0;
        else if (distance <=80) 	price = 459.5 + (distance -60)*9.0;
        else 				price = 639.5 + (distance -80)*10.5;
        int price2 = (int) Math.round(price);
        //int price2 = Integer.parseInt(a);//»Ñ´àÈÉ·È¹ÔÂÁ
        //if (price2%2 == 0)//àÅ¢¤ÙèºÇ¡1
        //  price2+=1;
        int time2 = (int) Math.round(time);

        int PriveTime = price2+t;

        DecimalFormat form = new DecimalFormat("0.0");
        //textView2.setText(form.format(result) );
        DistanceCal.setText(form.format(distance));
        TimeCal.setText(String.valueOf(time2));
        PriceCal.setText(String.valueOf(price2+"-"+PriveTime));
    }

    @Override
    public void onRoutingCancelled()
    {
        Log.i(LOG_TAG, "Routing was cancelled.");
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}