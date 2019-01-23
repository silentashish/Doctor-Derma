package np.com.gashish.www.skin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    String name, email, Status, Id;
    TextView userName;
    TextView userEmail;
    TextView userResponse;
    EditText inputText;
    Button Submit;
    private View mProgressView;
    private View mLoginFormView;
    private String url = new SiteLocation().comment;
    private String Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        Status=getIntent().getStringExtra("comment");
        Id=getIntent().getStringExtra("id");
        Data=getIntent().getStringExtra("response");


        Toast.makeText(getApplicationContext(),name+" "+email+" "+Status+" "+Id,Toast.LENGTH_SHORT).show();

        userEmail=(TextView)findViewById(R.id.user_email);
        userName=(TextView)findViewById(R.id.user_name);
        userResponse=(TextView)findViewById(R.id.user_comment);
        inputText=(EditText)findViewById(R.id.inputComment);
        Submit=(Button) findViewById(R.id.submit);
        mProgressView = findViewById(R.id.login_progress);
        mLoginFormView = findViewById(R.id.login_form);


        userName.setText(name);
        userEmail.setText(email);
        userResponse.setText(Status);

        ArrayList<FeedDisplay> arrayOfUsers = new ArrayList<FeedDisplay>();
        JSONObject jsonResponse;
        try {
            JSONArray mainObject = new JSONArray(Data);
            for (int n = 0; n < mainObject.length(); n++) {
                JSONObject inner = mainObject.getJSONObject(n);

                String name = inner.getString("file_name");
                String email = inner.getString("file_email");
                String status = inner.getString("comment");
                String id = inner.getString("post");
                String user_profile = inner.getString("user_profile");
                String created_on = inner.getString("created_on");
//                arrayOfUsers.add(new FeedDisplay(name, email, status, id));

                if(Id.equals(id)) {

                    arrayOfUsers.add(new FeedDisplay(name, email, status, id));
                    //Toast.makeText(getActivity(), "Response Success"+name+email+status, Toast.LENGTH_LONG).show();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Json error from the getResponse"+ e.toString(), Toast.LENGTH_SHORT).show();
        }


        // Create the adapter to convert the array to views
        final FeedAdapter adapter = new FeedAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView)findViewById(R.id.feed_display);
        listView.setAdapter(adapter);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
    }
    private void sendRequest(){
        String message=inputText.getText().toString();
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(message)) {
            inputText.setError(getString(R.string.error_field_required));
            inputText.requestFocus();
        }
        else {
            showProgress(true);
            PostSubmission(message);
        }

    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void PostSubmission(final String myMessage){
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress(false);
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    showProgress(false);
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        String data = new String(error.networkResponse.data, "UTF-8");
                        int statusCode = error.networkResponse.statusCode;
                        Toast.makeText(getApplicationContext(), "CustomRequest: Parsed Error Response = " + data, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    error.printStackTrace();
                    requestQueue.stop();

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "token 6d6d0586f5fe962964755c422d4f11fd38324d73");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("comment", myMessage);
                params.put("post", Id);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}

