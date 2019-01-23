package np.com.gashish.www.skin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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


/**
 * Fragment for Displaying profile feed activities
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View mProgressView;
    private String url = new SiteLocation().FeedUrl;
    private String url2 = new SiteLocation().CommentUrl;
    String Response="mess";
    ArrayList<FeedDisplay> arrayOfUsers;
    ListView listView;
    FeedAdapter adapter;

    public BlankFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment2 newInstance(String param1, String param2) {
        BlankFragment2 fragment = new BlankFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
        mProgressView=v.findViewById(R.id.login_progress);
        GetComment();
        getServerData();
        showProgress(true);
        arrayOfUsers = new ArrayList<FeedDisplay>();
        adapter = new FeedAdapter(getActivity(), arrayOfUsers);
        // Attach the adapter to a ListView
        listView = (ListView) v.findViewById(R.id.feed_display);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ik=new Intent(getActivity(),CommentActivity.class);
                ik.putExtra("name",adapter.getNametoMenu(position));
                ik.putExtra("email",adapter.getEmailtoMenu(position));
                ik.putExtra("comment",adapter.getStatustoMenu(position));
                ik.putExtra("id",adapter.getIdNow(position));
                ik.putExtra("response",Response);
                startActivity(ik);
            }
        });


        //String strtext = getArguments().getString("response");
        //Toast.makeText(getActivity(),strtext,Toast.LENGTH_SHORT).show();
//        ArrayList<FeedDisplay> arrayOfUsers = new ArrayList<FeedDisplay>();
//        JSONObject jsonResponse;
//        try {
//            JSONArray mainObject = new JSONArray(strtext);
//            for (int n = 0; n < mainObject.length(); n++) {
//                JSONObject inner = mainObject.getJSONObject(n);
//
//                String name = inner.getString("file_name");
//                String email = inner.getString("file_email");
//                String status = inner.getString("status_text");
//                String id = inner.getString("id");
//                String user_profile = inner.getString("user_profile");
//                String created_on = inner.getString("created_on");
//
//                arrayOfUsers.add(new FeedDisplay(name,email,status,id));
//                //Toast.makeText(getActivity(), "Response Success"+name+email+status, Toast.LENGTH_LONG).show();
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Toast.makeText(getActivity(),"Json error from the getResponse"+ e.toString(), Toast.LENGTH_SHORT).show();
//        }
//
//
////        arrayOfUsers.add(new FeedDisplay("Ashish gautam","a@gmail.com","hey r u all right"));
////        arrayOfUsers.add(new FeedDisplay("Ashish gautam","a@gmail.com","hey r u all right"));
////        arrayOfUsers.add(new FeedDisplay("Ashish gautam","a@gmail.com","hey r u all right"));
//
//
//        // Create the adapter to convert the array to views
//        final FeedAdapter adapter = new FeedAdapter(getActivity(), arrayOfUsers);
//        // Attach the adapter to a ListView
//        ListView listView = (ListView) v.findViewById(R.id.feed_display);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent ik=new Intent(getActivity(),CommentActivity.class);
//                ik.putExtra("name",adapter.getNametoMenu(position));
//                ik.putExtra("email",adapter.getEmailtoMenu(position));
//                ik.putExtra("comment",adapter.getStatustoMenu(position));
//                ik.putExtra("id",adapter.getIdNow(position));
//                ik.putExtra("response",Response);
//                startActivity(ik);
//            }
//        });
//        listView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ite=new Intent(getActivity(),PostActivity.class);
                startActivity(ite);
            }
        });
        return v;
    }

    //To Display Progress Bar

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

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
        }
    }

    private void getServerData(){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, new SiteLocation().FeedUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress(false);
                //Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
                JSONObject jsonResponse;
                try {
                    JSONArray mainObject = new JSONArray(response);
                    for (int n= mainObject.length()-1; n >=0; n--) {
                        JSONObject inner = mainObject.getJSONObject(n);

                        String name = inner.getString("file_name");
                        String email = inner.getString("file_email");
                        String status = inner.getString("status_text");
                        String id = inner.getString("id");
                        String user_profile = inner.getString("user_profile");
                        String created_on = inner.getString("created_on");

                        arrayOfUsers.add(new FeedDisplay(name,email,status,id));
                        //Toast.makeText(getActivity(), "Response Success"+name+email+status, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Json error from the getResponse"+ e.toString(), Toast.LENGTH_SHORT).show();
                }
                listView.setAdapter(adapter);

                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        String data = new String(error.networkResponse.data, "UTF-8");
                        int statusCode = error.networkResponse.statusCode;
                        Toast.makeText(getActivity(), "CustomRequest: Parsed Error Response = " + data, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    error.printStackTrace();
                    requestQueue.stop();

                }
            }
        });

        requestQueue.add(stringRequest);

    }

    private void GetComment(){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Response=response;
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false);
                try {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        String data = new String(error.networkResponse.data, "UTF-8");
                        int statusCode = error.networkResponse.statusCode;
                        Toast.makeText(getActivity(), "CustomRequest: Parsed Error Response = " + data, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    error.printStackTrace();
                    requestQueue.stop();

                }
            }
        });

        requestQueue.add(stringRequest);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
