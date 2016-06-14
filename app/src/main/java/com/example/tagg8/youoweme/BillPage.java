package com.example.tagg8.youoweme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.*;
import java.io.*;
import java.lang.Object.*;
import android.graphics.Bitmap;
import android.os.*;
import android.widget.Toast;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.view.ViewGroup.LayoutParams;
import android.view.Gravity;

/**
 * Created by tagg8 on 2/12/2016.
 */
public class BillPage extends Activity {


    private Map<String, Float> workPriceMap;
    private TextView[] tvBillMap;
    private TextView tvBillBreakdown;
    //private int workPriceMapSize;
    private TextView tvTotal;
    //private TextView tvTotalVal;
    private float totalVal;
    Button btnTakeScreenshot;
    //ImageView imageView;
    Bitmap mbitmap;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bill_page_layout);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.bill_page_layout);

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.bill_page_toolbar);
        //setSupportActionBar(myToolbar);


        tvBillBreakdown = (TextView) findViewById(R.id.tv_bill_breakdown);
        Intent intent = getIntent();
        workPriceMap = (HashMap<String, Float>) intent.getSerializableExtra("workPriceMap");
        totalVal = (Float) intent.getSerializableExtra("totalVal");
        Log.v("Total", String.valueOf(totalVal));
        Log.v("Tag",workPriceMap.toString());
        Log.v("WorkpriceMap Size: ", String.valueOf(workPriceMap.size()));
        tvBillMap = new TextView[workPriceMap.size() * 2];

        View linearLayout = findViewById(R.id.bill_page_layout);

        int i = 0;
        Iterator myVeryOwnIterator = workPriceMap.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            String key=(String)myVeryOwnIterator.next();
            String value=(String)workPriceMap.get(key).toString();
            key = key + ": ";
            Log.v("Key", key);
            Log.v("Value", value);
            DecimalFormat form = new DecimalFormat("0.00");
            tvBillMap[i] = new TextView(this);
            tvBillMap[i].setText(key);
            tvBillMap[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tvBillMap[i].setGravity(Gravity.CENTER_HORIZONTAL);
            tvBillMap[i + 1] = new TextView(this);
            tvBillMap[i + 1].setText("$" + String.valueOf(form.format(Float.parseFloat(value))));
            tvBillMap[i + 1].setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tvBillMap[i + 1].setGravity(Gravity.CENTER_HORIZONTAL);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT);
            tvBillMap[i].setId(i);
            tvBillMap[i + 1].setId(i + 1);
            tvBillMap[i].setLayoutParams(layoutParams);
            ((LinearLayout) linearLayout).addView(tvBillMap[i]);
            tvBillMap[i + 1].setLayoutParams(layoutParams);
            ((LinearLayout)linearLayout).addView(tvBillMap[i + 1]);
            //tvBillMap[i].bringToFront();
            //tvBillMap[i + 1].bringToFront();
            i++;
        }


        LayoutParams layoutParams2 = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        tvTotal = new TextView(this);
        tvTotal.setText("Total: ");
        tvTotal.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvTotal.setGravity(Gravity.CENTER_HORIZONTAL);
        tvTotal.setLayoutParams(layoutParams2);
        ((LinearLayout)linearLayout).addView(tvTotal);
        TextView tvTotalVal = new TextView(this);
        DecimalFormat form2 = new DecimalFormat("0.00");
        tvTotalVal.setText("$" + String.valueOf(form2.format(totalVal)));
        tvTotalVal.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tvTotalVal.setGravity(Gravity.CENTER_HORIZONTAL);
        tvTotalVal.setLayoutParams(layoutParams2);
        ((LinearLayout)linearLayout).addView(tvTotalVal);




        btnTakeScreenshot = (Button)findViewById(R.id.btnTakeScreenshot);
        //imageView = (ImageView) findViewById(R.id.imageView);
        btnTakeScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap screenShot = TakeScreenShot(ll);

                /*
                    MediaStore
                        The Media provider contains meta data for all available media
                        on both internal and external storage devices.

                    MediaStore.Images
                        Contains meta data for all available images.

                    insertImage(ContentResolver cr, Bitmap source, String title, String description)
                        Insert an image and create a thumbnail for it.
                */

                // Save the screenshot on device gallery
                MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        screenShot,
                        "Image",
                        "Captured ScreenShot"
                );

                // Notify the user that screenshot taken.
                Toast.makeText(getApplicationContext(), "Screen Captured.", Toast.LENGTH_SHORT).show();
            }
        });

    }


    // Custom method to take screenshot
    public Bitmap TakeScreenShot(View rootView) {
        /*
            public static Bitmap createBitmap (int width, int height, Bitmap.Config config)
                Returns a mutable bitmap with the specified width and height.
                Its initial density is as per getDensity().

                Parameters
                    width : The width of the bitmap
                    height : The height of the bitmap
                    config : The bitmap config to create.

                Throws
                    IllegalArgumentException : if the width or height are <= 0
        */

        /*
            Bitmap.Config
                Possible bitmap configurations. A bitmap configuration describes how pixels
                are stored. This affects the quality (color depth) as well as the ability
                to display transparent/translucent colors.

                ARGB_8888
                    Each pixel is stored on 4 bytes.
        */

        // Screenshot taken for the specified root view and its child elements.
        Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(),rootView.getHeight(),Config.ARGB_8888);

        /*
            Canvas
                The Canvas class holds the "draw" calls. To draw something, you need
                4 basic components:
                    A Bitmap to hold the pixels,
                    a Canvas to host the draw calls (writing into the bitmap),
                    a drawing primitive (e.g. Rect, Path, text, Bitmap),
                    and a paint (to describe the colors and styles for the drawing).
        */

        /*
            public Canvas (Bitmap bitmap)
                Construct a canvas with the specified bitmap to draw into. The bitmap must be mutable.
                The initial target density of the canvas is the same as the given bitmap's density.

                Parameters
                bitmap : Specifies a mutable bitmap for the canvas to draw into.
        */
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(-1);
        rootView.draw(canvas);
        return bitmap;
    }
}
