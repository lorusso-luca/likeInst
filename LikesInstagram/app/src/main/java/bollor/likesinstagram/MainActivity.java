package bollor.likesinstagram;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText urlPhotoEdit;
    EditText numEdit;
    Button start;
    BufferedReader in = null;
    String resultTot = "";
    TextView textNumber;
    TextView textResult;
    ProgressDialog dialog;
    String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        urlPhotoEdit = (EditText) findViewById(R.id.TextUrl);
        numEdit = (EditText) findViewById(R.id.number);
        start = (Button) findViewById(R.id.buttonStart);
        textNumber = (TextView) findViewById(R.id.TextNumber);
        textNumber.setText("How many likes?");
        textResult = (TextView) findViewById(R.id.textViewResult);


    }

    public void onStart() {
        super.onStart();
        start.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    dialog = ProgressDialog.show(MainActivity.this, "Adding likes",
                            "loading...", true);
                    String urlPhoto = urlPhotoEdit.getText().toString();
                    String num = numEdit.getText().toString();

                    URL = "https://api.joinsta.com/v1/?link=" + urlPhoto + "&maxlikes=" + num + "";
                    startLike(URL);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startLike(String URL) {
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(URL);
    }

    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("")) {
                startLike(URL);
            }
            textResult.setText(s);
            dialog.cancel();


        }


    }
}