package com.elation.myapplication;

import
        androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import
        android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.elation.myapplication.Modal.MobileNumberModal;
import com.elation.myapplication.RestConnection.BASE_URL;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText et_msg,et_firstId,et_lastId,et_mob;
    Button btn_select_Spefic_number,insert_data,btn_synStatus, btn_insert_Spefic_number;

    String mobile,id1,id2;
    private final int REQ=1;
    private  Bitmap bitmap;
    Uri uri;
    ImageView imh;
    BASE_URL mManager;
    DatabaseHelper mDatabase;
    DatabaseHelperClass mDatabase2;
    int i;
    String mob;
    List<MobileNumberModal> flowerList=new ArrayList<>();
    ArrayList<String> number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.CAMERA
                        )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            // do you work now
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }

                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();

        askPermissions();

            init();
       btn_synStatus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

                   mDatabase.updatestatus(1);


           }
       });

       //insert data
        insert_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id1 = et_firstId.getText().toString().trim();
                Log.d("id1",id1);
                id2 = et_lastId.getText().toString().trim();
                Log.d("id2",id2);
                // validation here
                if (id1.length() <= 0 && id2.length() <= 0) {
                    Toast.makeText(MainActivity.this, "Enter first id. & last id !", Toast.LENGTH_SHORT).show();
                } else if (id1.length() <= 0) {
                    Toast.makeText(MainActivity.this, "Please Enter First Id.", Toast.LENGTH_SHORT).show();
                }  else if (id2.length() <= 0) {
                    Toast.makeText(MainActivity.this, "Please Enter Last Id", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(getNetworkAvailability())
                    {
                        Call<List<MobileNumberModal>> listCall = mManager.getAllnumbers().getAllSpeficeNumber(id1,id2);

                        Log.d("ussdddddd",listCall.toString());
                        listCall.enqueue(new Callback<List<MobileNumberModal>>() {
                            @Override
                            public void onResponse(Call<List<MobileNumberModal>> call, Response<List<MobileNumberModal>> response) {

                                if (response.isSuccessful()) {
                                    List<MobileNumberModal> flowerList = response.body();
                                    Log.d("flowerlist",flowerList.toString());

                                    for (int i = 0; i < flowerList.size(); i++) {
                                        MobileNumberModal flower = flowerList.get(i);

                                        mDatabase.addFlower(flower);
                                    }
                                }
                                else {
                                    int sc = response.code();
                                    switch (sc) {
                                        case 400:
                                            Log.e("Error 400", "Bad Request");
                                            break;
                                        case 404:
                                            Log.e("Error 404", "Not Found");
                                            break;
                                        default:
                                            Log.e("Error", "Generic Error");
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<List<MobileNumberModal>> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        //send msg but whatsapp button not click
        btn_insert_Spefic_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String msg=et_msg.getText().toString().trim();
                 // validation here
                if (msg.length() <= 0) {
                    Toast.makeText(MainActivity.this, "Please Enter Message.", Toast.LENGTH_SHORT).show();
                }
                else {
                    List<MobileNumberModal> allmob = mDatabase.fetchFlowers();
                    List<String> numbers = new ArrayList<String>();

                    for (int i = 0; i < allmob.size(); i++) {

                        numbers.add(allmob.get(i).getMobile_number());
                    }
                    String[] numberArray = numbers.toArray(new String[0]);

                    try {
                        PackageManager packageManager = getApplicationContext().getPackageManager();
                        if (numberArray.length != 0) {
                            for (int j = 0; j < numberArray.length; j++) {
                                String mob = numberArray[j];
                                String msgs = et_msg.getText().toString().trim();
                                Intent is = new Intent(Intent.ACTION_VIEW);
                                String url = "https://api.whatsapp.com/send?phone=" + mob + "&text=" + URLEncoder.encode(msgs, "UTF-8");
                                is.setPackage("com.whatsapp");
                                is.setType("text/plain");
                                is.setData(Uri.parse(url));

                                is.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (is.resolveActivity(packageManager) != null) {
                                    getApplicationContext().startActivity(is);
                                    Thread.sleep(4000);
                                    numbers.remove(0);


                                } else {
                                    Toast.makeText(MainActivity.this, "whatsapp not installed..", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "cat worl", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }


                }


            }
        });
        IntentFilter intentFilter=new IntentFilter("my.own.broadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalBroadcast,intentFilter);

//       // send msg but whatsapp button not click
        btn_select_Spefic_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=et_msg.getText().toString().trim();

                // validation here
                if (msg.length() <= 0) {
                    Toast.makeText(MainActivity.this, "Please Enter Message...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    List<MobileNumberModal> allmob=mDatabase.fetchFlowers();
//                    for(MobileNumberModal mobs : allmob)
//                    {
//                        String mob = mobs.getMobile_number();
//                        MyIntentService.startActionWHATSAPP(getApplicationContext(),msg,mob);
//
//                    }

                    List<String> numbers=new ArrayList<String>();

                    for (int i = 0; i < allmob.size(); i++) {

                        numbers.add(allmob.get(i).getMobile_number());
                    }
                    String[] numberArray=numbers.toArray(new String[0]);

                        for(int i=0;i<numberArray.length;i++)
                        {
                            String mob=numberArray[i];
                            MyIntentService.startActionWHATSAPP(getApplicationContext(),msg,mob);

                        }

                }




            }
        });



//        btn_shareImage.setOnClickListener(new View.OnClickListener()  {
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onClick(View v) {
//
//
//                Cursor cursor = mDatabase.fetchFlowers();
//                cursor.moveToFirst();
//                if (cursor.getCount() > 0) {
//
//                    for (i = 0; i < cursor.getCount(); i++){
//                        Log.d("hhhj", String.valueOf(cursor.getCount()));
//                        mobile= cursor.getString(1).trim();
//                        String mob=mobile;
//
//                        Toast.makeText(MainActivity.this, "lopii"+mob, Toast.LENGTH_SHORT).show();
//                     //   String url="http://43.240.65.33///EbookData/SMS/support/PICS%20FOR%20ACT%20APPS/01.jpg";
//
//
//                    }
//
//                    i=0;
//                    cursor.close();
//
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "no data fond", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });





    }

    private void init() {

        et_msg=(EditText)findViewById(R.id.et_msg);
        et_firstId=(EditText)findViewById(R.id.firstId);
        et_lastId=(EditText)findViewById(R.id.lastId);

        insert_data=(Button)findViewById(R.id.insertdata);
        btn_select_Spefic_number=(Button)findViewById(R.id.btn_data_slectSpeficNumber);
         btn_insert_Spefic_number=(Button)findViewById(R.id.btn_data_insertSpeficNumber);
        btn_synStatus=(Button)findViewById(R.id.btn_synStatus);
        mManager = new BASE_URL();
        mDatabase = new DatabaseHelper(this);
        mDatabase2=new DatabaseHelperClass(this);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQ);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==REQ && resultCode == RESULT_OK) {
            uri=data.getData();
            try {
                bitmap =MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imh.setImageBitmap(bitmap);
        }
    }

    private BroadcastReceiver myLocalBroadcast=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                String result=intent.getStringExtra("result");
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    };



    private void askPermissions() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,

                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, 10);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {
                for (int i = 0; i < grantResults.length; i++)
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        break;
                   }
                    else{
                        if (!isAccessibilityOn(MainActivity.this, WhatsappAccessibilityService.class)) {
                            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            return;
                        }
                    }
                return;
            }

        }
    }
    private boolean isAccessibilityOn (Context context, Class<? extends AccessibilityService> clazz) {
        int accessibilityEnabled = 0;
        final String service = context.getPackageName () + "/" + clazz.getCanonicalName ();
        try {
            accessibilityEnabled = Settings.Secure.getInt (context.getApplicationContext ().getContentResolver (), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ignored) {  }

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter (':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString (context.getApplicationContext ().getContentResolver (), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                colonSplitter.setString (settingValue);
                while (colonSplitter.hasNext ()) {
                    String accessibilityService = colonSplitter.next ();

                    if (accessibilityService.equalsIgnoreCase (service)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }



}