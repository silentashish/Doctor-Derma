package np.com.gashish.www.skin;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class FeedAdapter extends ArrayAdapter<FeedDisplay> {

    public FeedAdapter(Activity context, @NonNull ArrayList<FeedDisplay> objects) {
        super(context, 0, objects);
    }

    static class ViewHolder {
        TextView userName;
        TextView userEmail;
        TextView userResponse;

        ViewHolder(View v)
        {
            userEmail=(TextView)v.findViewById(R.id.user_email);
            userName=(TextView)v.findViewById(R.id.user_name);
            userResponse=(TextView)v.findViewById(R.id.user_comment);

        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        FeedAdapter.ViewHolder holder=null;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_profileholder, parent, false);
            holder= new ViewHolder(listItemView);
            listItemView.setTag(holder);
        }
        else
        {
            holder=(FeedAdapter.ViewHolder)listItemView.getTag();
        }
        //Spannable in here is to set the different type of the text in the same textView Section
        FeedDisplay currentPeople_detai = getItem(position);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        String tName=currentPeople_detai.getUserName();
        String tEmail=currentPeople_detai.getUserEmail();
        String tMessage=currentPeople_detai.getUserResponse();

        holder.userName.setText(tName);
        holder.userEmail.setText(tEmail);
        holder.userResponse.setText(tMessage);
        return listItemView;
    }

    public String getNametoMenu(int p){
        FeedDisplay fd=getItem(p);
        return fd.getUserName();
    }
    public String getEmailtoMenu(int p){
        FeedDisplay fd=getItem(p);
        return fd.getUserEmail();
    }
    public String getStatustoMenu(int p){
        FeedDisplay fd=getItem(p);
        return fd.getUserResponse();
    }
    public String getIdNow(int p){
        FeedDisplay fd=getItem(p);
        return fd.getUserId();
    }
}
