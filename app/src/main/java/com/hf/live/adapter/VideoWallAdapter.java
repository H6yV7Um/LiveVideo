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
import android.widget.TextView;

import com.hf.live.R;
import com.hf.live.dto.PhotoDto;

import net.tsz.afinal.FinalBitmap;

import java.util.List;

/**
 * 视频墙
 */

public class VideoWallAdapter extends BaseAdapter{
	
	private Context mContext;
	private LayoutInflater mInflater;
	private List<PhotoDto> mArrayList;
	private int width;
	
	private final class ViewHolder{
		ImageView imageView;
		ImageView ivVideo;
		ImageView ivPortrait;
		TextView tvAddress;
		TextView tvTime;
		TextView tvUserName;
		TextView tvPraise;
		TextView tvComment;
		TextView tvTitle;
	}
	
	private ViewHolder mHolder = null;
	
	public VideoWallAdapter(Context context, List<PhotoDto> mArrayList) {
		mContext = context;
		this.mArrayList = mArrayList;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		width = wm.getDefaultDisplay().getWidth();
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
			convertView = mInflater.inflate(R.layout.adapter_video_wall, null);
			mHolder = new ViewHolder();
			mHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
			mHolder.ivVideo = (ImageView) convertView.findViewById(R.id.ivVideo);
			mHolder.ivPortrait = (ImageView) convertView.findViewById(R.id.ivPortrait);
			mHolder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
			mHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			mHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
			mHolder.tvPraise = (TextView) convertView.findViewById(R.id.tvPraise);
			mHolder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
			mHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			convertView.setTag(mHolder);
		}else {
			mHolder = (ViewHolder) convertView.getTag();
		}

		try {
			PhotoDto dto = mArrayList.get(position);

			if (!TextUtils.isEmpty(dto.imgUrl)) {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width*9/16);
				FinalBitmap finalBitmap = FinalBitmap.create(mContext);
				finalBitmap.display(mHolder.imageView, dto.imgUrl, null, 0);
				mHolder.imageView.setLayoutParams(params);
			}

			if (!TextUtils.isEmpty(dto.location)) {
				mHolder.tvAddress.setText(dto.location);
			}else {
				mHolder.tvAddress.setText(mContext.getString(R.string.no_location));
			}

			if (!TextUtils.isEmpty(dto.portraitUrl)) {
				ViewGroup.LayoutParams lp = mHolder.ivPortrait.getLayoutParams();
				int width = lp.width;
				FinalBitmap finalBitmap = FinalBitmap.create(mContext);
				finalBitmap.display(mHolder.ivPortrait, dto.portraitUrl, null, width);
			}else {
				mHolder.ivPortrait.setImageResource(R.drawable.iv_portrait);
			}

			if (!TextUtils.isEmpty(dto.nickName)) {
				mHolder.tvUserName.setText(dto.nickName);
			}else if (!TextUtils.isEmpty(dto.userName)) {
				mHolder.tvUserName.setText(dto.userName);
			}else if (!TextUtils.isEmpty(dto.phoneNumber)) {
				if (dto.phoneNumber.length() >= 7) {
					mHolder.tvUserName.setText(dto.phoneNumber.replace(dto.phoneNumber.substring(3, 7), "****"));
				}else {
					mHolder.tvUserName.setText(dto.phoneNumber);
				}
			}

			if (!TextUtils.isEmpty(dto.title)) {
				mHolder.tvTitle.setText(dto.title);
			}

			if (!TextUtils.isEmpty(dto.praiseCount)) {
				mHolder.tvPraise.setText(dto.praiseCount);
			}

			if (!TextUtils.isEmpty(dto.commentCount)) {
				mHolder.tvComment.setText(dto.commentCount);
			}

			if (!TextUtils.isEmpty(dto.workTime)) {
				mHolder.tvTime.setText(dto.workTime);
			}else {
				mHolder.tvTime.setText("--");
			}

			if (dto.getWorkstype().equals("imgs")) {
				mHolder.ivVideo.setVisibility(View.INVISIBLE);
			}else {
				mHolder.ivVideo.setVisibility(View.VISIBLE);
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		return convertView;
	}

}
