package com.example.tagg8.youoweme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.app.ActionBar;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import android.view.inputmethod.InputMethodManager;

import java.util.*;
import java.text.*;

public class MainPage extends AppCompatActivity {


    public EditText etText;
    public TextView tvText;
    public EditText etText2;
    public Button total;
    public Button addNewItem;
    public Button addValues;
    public TextView textViewTotalNumber;
    private Map<String, Float> workPriceMap;
    private float totalVal;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tagg8.youoweme/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        workPriceMap = new HashMap<String, Float>();

        total = (Button) findViewById(R.id.total);
        addNewItem = (Button) findViewById(R.id.addNewItem);
        addValues = (Button) findViewById(R.id.addValues);

        etText = (EditText) findViewById(R.id.etText);
        tvText = (TextView) findViewById(R.id.tvText);
        etText2 = (EditText) findViewById(R.id.etText2);
        textViewTotalNumber = (TextView) findViewById(R.id.textViewTotalNumber);

        addNewItem.setEnabled(true);
        total.setEnabled(false);
        addValues.setEnabled(true);


        etText.setVisibility(View.GONE);
        etText2.setVisibility(View.GONE);

        /*etText.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                switch (keyCode) {
                    case KeyEvent.KEYCODE_NAVIGATE_NEXT:
                        etText.clearFocus();
                        etText2.requestFocus();
                        return true;
                    case KeyEvent.KEYCODE_SOFT_RIGHT:
                        etText.clearFocus();
                        etText2.requestFocus();
                        return true;
                    case KeyEvent.KEYCODE_PAGE_DOWN:
                        etText.clearFocus();
                        etText2.requestFocus();
                        return true;
                    default:
                        return false;
                }

            }
        });*/

        /*etText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        */


        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etText.getVisibility() == View.GONE) {
                    etText.setVisibility(View.VISIBLE);
                    etText2.setVisibility(View.VISIBLE);
                }
                else {
                    etText.setVisibility(View.GONE);
                    etText2.setVisibility(View.GONE);
                }
            }
        });


        addValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float itemPrice = Float.parseFloat(etText2.getText().toString());
                String totalValueString = textViewTotalNumber.getText().toString();
                totalVal = Float.parseFloat(totalValueString.replace("$",""));
                String workDoneText = etText.getText().toString();
                totalVal += itemPrice;
                DecimalFormat form = new DecimalFormat("0.00");

                textViewTotalNumber.setText("$" + String.valueOf(form.format(totalVal)));
                workPriceMap.put(workDoneText, itemPrice);
                etText.setText("");
                etText2.setText("");
                total.setEnabled(true);
            }
        });

        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BillPage.class);
                i.putExtra("workPriceMap", (HashMap<String, Float>)workPriceMap);
                i.putExtra("totalVal", totalVal);
                startActivity(i);
                //TextView tv = new TextView();
                //tv.setText(parent.getItemAtPosition(pos).toString() + "Planet is Selected");
                //setContentView(tv);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    /*
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.tagg8.youoweme/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
