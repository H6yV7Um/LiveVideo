package com.hf.live.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hf.live.R;

import net.tsz.afinal.FinalBitmap;

import java.util.List;

/**
 * 在线预览图片
 */

public class OnlinePictureAdapter extends BaseAdapter{
	
	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> mArrayList;
	private int width;
	private RelativeLayout.LayoutParams params;
	
	private final class ViewHolder{
		ImageView imageView;
	}
	
	private ViewHolder mHolder = null;
	
	public OnlinePictureAdapter(Context context, List<String> mArrayList) {
		mContext = context;
		this.mArrayList = mArrayList;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		width = wm.getDefaultDisplay().getWidth();
		params = new RelativeLayout.LayoutParams(width/4, width/4-10);
	}

	@Override
	public int getCount() {
		return mArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_online_picture, null);
			mHolder = new ViewHolder();
			mHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(mHolder);
		}else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		
		String imgUrl = mArrayList.get(position);
		if (!TextUtils.isEmpty(imgUrl)) {
			FinalBitmap finalBitmap = FinalBitmap.create(mContext);
			finalBitmap.display(mHolder.imageView, imgUrl, null, 0);
			if (params != null) {
				mHolder.imageView.setLayoutParams(params);
			}
		}
		
		return convertView;
	}

}
