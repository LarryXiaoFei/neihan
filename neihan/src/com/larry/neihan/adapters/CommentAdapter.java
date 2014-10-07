package com.larry.neihan.adapters;

import java.util.Date;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.larry.neihan.R;
import com.larry.neihan.bean.Comment;

public class CommentAdapter extends BaseAdapter {
	
	
	private Context context;
	private List<Comment> comments;
	private LayoutInflater inflater;
	public CommentAdapter(Context context, List<Comment> comments) {
		this.context = context;
		this.comments = comments;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return comments.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View ret = convertView;
		if(ret==null)
		{
			ret = inflater.inflate(R.layout.item_comment,parent,false);
		}
		
		if(ret!=null)
		{
			ViewHolder holder = (ViewHolder) ret.getTag();
			if(holder == null)
			{
				holder = new ViewHolder();
				holder.profileImage = (ImageView) ret.findViewById(R.id.comment_profile_image);
				holder.txtNick = (TextView) ret.findViewById(R.id.comment_profile_nick);
				holder.txtCreateTime = (TextView) ret.findViewById(R.id.comment_create_time);
				holder.txtContent = (TextView) ret.findViewById(R.id.comment_content);
				holder.chbDigg = (CheckBox) ret.findViewById(R.id.comment_digg_count);
			
				ret.setTag(holder);
			}
			Comment comment = comments.get(position);
			holder.txtNick.setText(comment.getUserName());
			Date dt = new java.util.Date(comment.getCreateTime());
			
			holder.txtCreateTime.setText(dt.toString());
			holder.txtContent.setText(comment.getText());
			
			int diggCount = comment.getDiggCount();
			holder.chbDigg.setText(Integer.toString(diggCount));
			
			int userDigg = comment.getUserDigg();
			holder.chbDigg.setEnabled(userDigg == 0);
			
		}
		
		return ret;
	}
	
	private static class ViewHolder
	{
		public ImageView profileImage;
		public TextView txtNick;
		public TextView txtCreateTime;
		public TextView txtContent;
		public CheckBox chbDigg;
	}

}
