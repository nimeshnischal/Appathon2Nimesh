package com.nimeshnischal.appathon2nimesh;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class DisplayActivity extends Activity {

    static final String RESPONSE = "response";
    @BindView(R.id.response_text)
    TextView responseText;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ButterKnife.bind(this);
        if(savedInstanceState == null) {
            if (Utilities.isNetworkConnected(DisplayActivity.this)) {
                Log.d("NMZ_LOG", "Before execute");
                try {
                    Log.d("NMZ_LOG", "Reached 1");
                    Log.d("NMZ_LOG", getIntent().getStringExtra(Utilities.URL));
                    Log.d("NMZ_LOG", "Reached 2");
                    URL url = new URL(getIntent().getStringExtra(Utilities.URL));
                    new RetrieveAPIResponses().execute(url);    // TODO: stop execution in onDestroy()

                } catch (Exception e) {
                    hideProgressBar();
                    Log.e("NMZ_LOG", e.toString());
                    showErrorDialog();
                }
            } else {
                hideProgressBar();
                Log.e("NMZ_LOG", "Netwok not connected");
                showErrorDialog();
            }
        }
        else {
            responseText.setText(savedInstanceState.getString(RESPONSE));
            hideProgressBar();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RESPONSE,responseText.getText().toString());
    }


    void hideProgressBar(){
        progressBar.setVisibility(GONE);
    }

    private void showErrorDialog() {
        try {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(DisplayActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(DisplayActivity.this);
            }
            builder.setTitle("No connection")
                    .setMessage("Problem in establishing connection. Please check your internet connection and try again.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }catch (Exception e){
            Toast.makeText(DisplayActivity.this, "An error occurred. Please try again!", Toast.LENGTH_LONG).show();
            finish();
        }
    }



    class RetrieveAPIResponses extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {
            try {
                URL url = urls[0];
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    Log.d("NMZ_LOG", "Before read");
                    while ((line = bufferedReader.readLine())!= null){
                        stringBuilder.append(line).append("\n");
                    }
                    Log.d("NMZ_LOG", "After read: "+stringBuilder.toString());
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally {
                    httpURLConnection.disconnect();
                }

            }
            catch (SocketTimeoutException t){
                Log.e("NMZ_LOG", "Timeout occurred: "+t.toString());
                return null;
            }
            catch (Exception e){
                Log.e("NMZ_LOG", "Error: "+e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            hideProgressBar();
            Log.d("NMZ_LOG", "Showing response: "+response);
            if(response != null) {
                responseText.setText(response);
            }
            else{
                showErrorDialog();
            }
        }
    }

}
