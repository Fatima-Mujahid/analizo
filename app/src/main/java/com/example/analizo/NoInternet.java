package com.example.analizo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class NoInternet extends AppCompatActivity {

    private Class nextActivityClass;

    //Check whether the phone is connected to the internet or not
    //Context is passed because this method is called from different activities and context is used while creating ConnectivityManager for the respective activity
    public boolean isConnectedToInternet(Context context){

        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)

                    //Get network connection state
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    //Try again
    public void onClick(View view){
        //The class where lost connection is detected passes control to NoInternet activity
        //Class name is being passed with intent so the when user clicks Try Again, the respective class activity opens

        Bundle bundle = getIntent().getExtras();
        nextActivityClass = (Class<Activity>)bundle.getSerializable("class");

        //Check if the internet connection is available now or not
        if(isConnectedToInternet(NoInternet.this)){

            //If available then open the respective activity which was being opened when the internet connection lost
            Intent intent = new Intent(NoInternet.this, nextActivityClass);
            startActivity(intent);

            //Finish activity so it does not show when back button is pressed
            finish();
        }
        else{

            //If internet connection not available, then refresh the NoInternet activity
            finish();
            startActivity(getIntent());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
    }
}
