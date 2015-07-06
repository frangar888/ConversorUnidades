package com.example.conversorunidades;

import java.util.ArrayList;

import android.app.*;
import android.content.Context;
import android.view.*;
import android.widget.*;

public class SpinnerAdapter extends ArrayAdapter<ItemData> {
	int groupid;
	Activity context;
	ArrayList<ItemData> list;
	LayoutInflater inflater;
	public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<ItemData> 
	list){
	super(context,id,list);
	this.list=list;
	inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	this.groupid=groupid;
	}		

	public View getView(int position, View convertView, ViewGroup parent ){
	View itemView=inflater.inflate(groupid,parent,false);
	ImageView imageView=(ImageView)itemView.findViewById(R.id.img);
	imageView.setImageResource(list.get(position).getImageId());
	TextView textView=(TextView)itemView.findViewById(R.id.txt);
	textView.setText(list.get(position).getText());
	return itemView;
	}		

	public View getDropDownView(int position, View convertView, ViewGroup 
	parent){
	return getView(position,convertView,parent);

	}
}
