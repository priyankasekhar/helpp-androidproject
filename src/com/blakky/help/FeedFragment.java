package com.blakky.help;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.blakky.helper.UserFunctions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FeedFragment extends Fragment{

	private View rootview;
	private ListView lv;
	private List<RowItem> rowItems;
	private RowItem row;
	private ProgressDialog pDialog;
	private CustomListViewAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootview=inflater.inflate(R.layout.frag_feed, container,false);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

		lv = (ListView)rootview.findViewById(R.id.dash_feed_list);
		
		new FeedAync().execute();
		
		return rootview;
	}

	class FeedAync extends AsyncTask<String, Void, List<RowItem>>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog.show();
		}


		@Override
		protected List<RowItem> doInBackground(String... params) {
			// TODO Auto-generated method stub
			rowItems = new ArrayList<RowItem>();
			UserFunctions userFunction = new UserFunctions();
			JSONArray json = null;
			try {
				json = userFunction.getDetails();
				Log.i("tag", json+"");
				if(json!=null){
					//JSONArray spec_aray= json.getJSONArray("");
					for(int i=0;i<json.length();i++)
					{
						JSONObject json1= json.getJSONObject(i);
						String name=json1.getString("food_des");
						String desc=json1.getString("food_detail");
						String address= json1.getString("food_add");
						String contact_no = json1.getString("food_ph");
						
						
						RowItem item = new RowItem(name, desc, address, contact_no);
						rowItems.add(item);
						
					}
				}
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				rowItems = null;
				e1.printStackTrace();
				Log.i("tag", e1+"");
			}
			
			return rowItems;
		}

		@Override
		protected void onPostExecute(List<RowItem> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			adapter = new CustomListViewAdapter(getActivity(), R.layout.list_news, result);
			lv.setAdapter(adapter);
		}
	}
	
	public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

		ViewHolder holder = null;
		RowItem rowitem;
		int[] array_img = {R.drawable.hero1,R.drawable.hero2,R.drawable.hero3,R.drawable.hero4
				,R.drawable.hero5,R.drawable.hero6,R.drawable.hero7};
		List<RowItem> rowitems;
		public CustomListViewAdapter(Context context, int resource, List<RowItem> rowitems) {
			super(context, resource, rowitems);
			// TODO Auto-generated constructor stub
			this.rowitems = rowitems;
		}
		

		/*private view holder class*/
	    private class ViewHolder {
	       public ImageView food_img;
	       public TextView food_name;
	       public TextView food_desc;
	       public TextView  food_address;
	       public TextView  food_contact;

	    }


		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			rowitem=getItem(position);
			
			 LayoutInflater mInflater = (LayoutInflater)getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
			if(convertView == null)
			{
				convertView = mInflater.inflate(R.layout.list_news, parent,false);
	            holder = new ViewHolder();	
				holder.food_name=(TextView)convertView.findViewById(R.id.feed_name);
				holder.food_desc=(TextView)convertView.findViewById(R.id.feed_descp);
				holder.food_address = (TextView)convertView.findViewById(R.id.feed_address);
				holder.food_contact = (TextView)convertView.findViewById(R.id.feed_contact);
				holder.food_img = (ImageView)convertView.findViewById(R.id.feed_img);

				convertView.setTag(holder);
			}else {
				 holder = (ViewHolder) convertView.getTag();
			}
			
			holder.food_name.setText(rowItems.get(position).fname);
			holder.food_desc.setText(rowItems.get(position).fdetail);
			holder.food_address.setText("Address: "+rowItems.get(position).faddress);
			holder.food_contact.setText("Contact No.:  "+rowItems.get(position).fcontact);
		
			int i = getRandom(7);
			holder.food_img.setImageResource(array_img[i]);
			return convertView;
		}
	    
		public  int getRandom(int max){
			return (int) (Math.random()*max);
		}
	}
	
	class RowItem 
	{
		private String fname;
		private String fdetail;
		private String faddress;
		private String fcontact;
		public RowItem(String fname, String fdetail, String faddress, String fcontact) {
			super();
			this.fname = fname;
			this.fdetail = fdetail;
			this.faddress = faddress;
			this.fcontact = fcontact;
		}
		public String getFname() {
			return fname;
		}
		public void setFname(String fname) {
			this.fname = fname;
		}
		public String getFdetail() {
			return fdetail;
		}
		public void setFdetail(String fdetail) {
			this.fdetail = fdetail;
		}
		public String getFaddress() {
			return faddress;
		}
		public void setFaddress(String faddress) {
			this.faddress = faddress;
		}
		public String getFcontact() {
			return fcontact;
		}
		public void setFcontact(String fcontact) {
			this.fcontact = fcontact;
		}
		
		
		
		
		
	}
	
}
