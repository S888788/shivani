package com.elation.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.net.URLEncoder;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_WHATSAPP = "com.elation.myapplication.action.WHATSAPP";


    // TODO: Rename parameters
    private static final String EXTRA_MESSAGE = "com.elation.myapplication.extra.PARAM1";
    private static final String EXTRA_MOBILE = "com.elation.myapplication.extra.PARAM2";


    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionWHATSAPP(Context context, String message,  String mobile) {


        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_WHATSAPP);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_MOBILE,mobile);
        context.startService(intent);

    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_WHATSAPP.equals(action)) {
                final String message = intent.getStringExtra(EXTRA_MESSAGE);
                final String mobile_number = intent.getStringExtra(EXTRA_MOBILE);
                handleActionWHATSAPP(message,mobile_number);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionWHATSAPP(String message, String  mobile_number) {

          String mob=mobile_number;
        try {
            PackageManager packageManager = getApplicationContext().getPackageManager();
            String url = "https://api.whatsapp.com/send?phone=" + mob + "&text=" + URLEncoder.encode(message, "UTF-8");
            Log.d("trr",url);
            Intent is = new Intent(Intent.ACTION_VIEW);
            is.setPackage("com.whatsapp");
            is.setType("text/plain");
            is.setData(Uri.parse(url));
            is.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            is.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (is.resolveActivity(packageManager) != null) {
                getApplicationContext().startActivity(is);
                Thread.sleep(4000);
                sendBroadcastMessage("Result" + mob);

            }
            else
            {
                Toast.makeText(this, "whatsapp not installed..", Toast.LENGTH_SHORT).show();
            }



        }
        catch (Exception e) {
            Toast.makeText(this, "cat worl", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }



        private void sendBroadcastMessage(String message)
        {
            Intent localIntent = new Intent("my.own.broadcast");
            localIntent.putExtra("result", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        }
    }

