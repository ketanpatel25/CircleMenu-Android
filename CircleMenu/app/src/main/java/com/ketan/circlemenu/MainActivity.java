package com.ketan.circlemenu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.ketan.circlemenu.circlemenu.CircleLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity implements View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView listView;
    private ArrayAdapter adapter;
    private TextView tvToolbarTitle;
    private FloatingActionButton floatingActionButton;
    private Calendar calendar = Calendar.getInstance();

    EditText edName, edMobile, edEmail, edDate, edLocation, edTime;
    Spinner spService;
    TextView tvsubmit, tvcancel;
    String strName, strMobile, strService, strDate, strLocation, strEmail;
    private Dialog dialogCircleMenu;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolbarTitle = (TextView) findViewById(R.id.txt_toolbar_title);
//        tvToolbarTitle.setText("HOME");

        floatingActionButton = (FloatingActionButton) findViewById(R.id.activity_main_floating_buttton);
        floatingActionButton.setOnClickListener(this);


        floatingActionButton.bringToFront();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View v) {

        if (v == floatingActionButton) {
            callMenuUI();
        }
    }

    public void ShearApp(View v) {
        String appPackageName = getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }



    private void callMenuUI() {

        dialogCircleMenu = new Dialog(this, R.style.TransparantBlackDialog);
        LayoutInflater li = LayoutInflater.from(this);
        View view = li.inflate(R.layout.menu_main, null);
        dialogCircleMenu.setContentView(view);
        dialogCircleMenu.setCancelable(true);

        CircleLayout circleLayout = (CircleLayout) view.findViewById(R.id.circle_layout);

        circleLayout.setOnItemSelectedListener(new CircleLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view) {
                switch (view.getId()) {

                    case R.id.main_mail_image:

                        String recepientEmail = "services@test.com"; // either set to destination email or leave empty
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:" + recepientEmail));
                        startActivity(intent);
                        dialogCircleMenu.dismiss();

                        break;
                    case R.id.main_whatsapp_image:

                        try {
                            Uri mUri = Uri.parse("smsto:+919904486604");
                            Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
                            mIntent.setPackage("com.whatsapp");
//                    mIntent.putExtra("sms_body", "The text goes here");
                            mIntent.putExtra("chat", true);
                            startActivity(mIntent);

                        } catch (Exception e) {

                            Toast.makeText(MainActivity.this, "Your device in whatsapp app not install", Toast.LENGTH_SHORT).show();
                        }

                        dialogCircleMenu.dismiss();

                        break;
                    case R.id.main_google_image:
//                    https://plus.google.com/u/0/101197932961623764042
//
                        try {
                            Intent intent1 = new Intent(Intent.ACTION_VIEW);
                            intent1.setClassName("com.google.android.apps.plus",
                                    "com.google.android.apps.plus.phone.UrlGatewayActivity");
                            intent1.putExtra("customAppUri", "101197932961623764042");
                            startActivity(intent1);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + "101197932961623764042" + "/posts")));
                        }


                        dialogCircleMenu.dismiss();
                        // Handle mail selection
                        break;
                }
            }
        });


        dialogCircleMenu.show();
    }


    public void facebookServiceUI(View v) {


        try {
            String facebookScheme = "fb://profile/" + "1903376073276581";
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
            startActivity(facebookIntent);
        } catch (ActivityNotFoundException e) {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=" + "1903376073276581"));
            startActivity(facebookIntent);
        }

        if (dialogCircleMenu.isShowing()) {
            dialogCircleMenu.dismiss();
        }
    }


    public void MapServiceUI(View v) {
        if (dialogCircleMenu.isShowing()) {
            dialogCircleMenu.dismiss();
        }


//        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", "23.0450", "72.5838");
        String geoUri = "http://maps.google.com/maps?q=loc:" + "23.0450" + "," + "72.5838" + " (" + "CircleMenu" + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        startActivity(intent);

//        try{
//            String facebookScheme = "fb://profile/" + "1903376073276581";
//            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
//            startActivity(facebookIntent);
//        } catch (ActivityNotFoundException e) {
//            Intent facebookIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/profile.php?id="+"1903376073276581"));
//            startActivity(facebookIntent);
//        }

    }

    public void ContectServiceUI(View v) {
        if (dialogCircleMenu.isShowing()) {
            dialogCircleMenu.dismiss();
        }
        tvToolbarTitle.setText("Book Service");
        drawerLayout.closeDrawers();

    }


    public void HomeServiceUI(View v) {
        if (dialogCircleMenu.isShowing()) {
            dialogCircleMenu.dismiss();
        }
        tvToolbarTitle.setText("Home");
        drawerLayout.closeDrawers();

    }



    public void callToAdmin(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:7046220033"));
        startActivity(intent);
    }





}
