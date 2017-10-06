package com.lapremavera.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button btnBigImageNotification;
    private Button btnHighPriorityNotification;
    private Button btnNougatNotificiation;
    private NotificationManager manager;
    private int NOTIF_REF=1;
    private static final String KEY_TEXT_REPLY= "key_text_reply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnHighPriorityNotification = (Button) findViewById(R.id.btnHighPriorityNotification);
        btnBigImageNotification = (Button) findViewById(R.id.btnBigImageNotification);
        btnNougatNotificiation = (Button) findViewById(R.id.btnNougatNotification);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        btnHighPriorityNotification.setOnClickListener(MainActivity.this);
        btnBigImageNotification.setOnClickListener(MainActivity.this);
        btnNougatNotificiation.setOnClickListener(MainActivity.this);

        handleNotification(getIntent());


    }

    private void handleNotification(Intent intent) {

        if (getMessageText(intent) != null) {
            Notification repliedNotification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ariel)
                    .setContentText("I'm working on it!")
                    .build();
            manager.notify(0123, repliedNotification);
        }


    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(KEY_TEXT_REPLY);
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Notification notif = null;
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ariel)
                .setWhen(System.currentTimeMillis())
                .setContentText("P Notifications");
        switch (view.getId()) {
            case R.id.btnHighPriorityNotification:
                builder.setContentTitle("Héél belangrijk!")
                        .setPriority(Notification.PRIORITY_HIGH);
                sendNotification(builder.build());
                break;
            case R.id.btnBigImageNotification:
                notif = getBigPictureStyle(builder);
                sendNotification(notif);
                break;
            case R.id.btnNougatNotification: notif = getNougatStyleNotification(builder);
                manager.notify(0123, notif);
                break;
        }

    }
    public void sendNotification (Notification notif) {
        manager.notify(NOTIF_REF++, notif);
    }
    private Notification getBigPictureStyle(Notification.Builder builder) {

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.ariel);

        return //new Notification.Builder(MainActivity.this)
        builder
        .setContentTitle("Reduced Bigpicture title")
                .setContentText("Reduced content")
                .setSmallIcon(R.drawable.ariel)
                .setLargeIcon(icon)
                .setStyle(new Notification.BigPictureStyle().bigPicture(icon))
                .build();

    }
    private Notification getNougatStyleNotification(Notification.Builder builder) {
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel("")
                .build();

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Action action = new Notification.Action.Builder(R.drawable.ariel, "Reply...", resultPendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        return //new Notification.Builder(this)
        builder
                .setSmallIcon(R.drawable.ariel)
                .setContentTitle("God")
                .setContentText("Hey, you wanna do something?")
                .addAction(action).build();
    }
}
