package com.obigo.obigoproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.obigo.obigoproject.R;
import com.obigo.obigoproject.util.ConstantsUtil;
import com.obigo.obigoproject.vo.MessageVO;

import java.util.List;

/**
 * Created by O BI HE ROCK on 2016-12-14
 * 김용준, 최현욱
 * MessageList 출력
 */

public class MessageListAdapter extends ArrayAdapter<MessageVO> {
    private Activity messageActivity;
    private List<MessageVO> messageList;
    private MessageVO messageVO;
    private int row;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public MessageListAdapter(Activity messageActivity, int resource, List<MessageVO> messageList) {
        super(messageActivity, resource, messageList);
        this.messageActivity = messageActivity;
        this.row = resource;
        this.messageList = messageList;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.message_stub)
                .showImageForEmptyUri(R.drawable.message_empty)
                .showImageOnFail(R.drawable.message_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        imageLoader = ImageLoader.getInstance();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) messageActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if ((messageList == null) || ((position + 1) > messageList.size()))
            return view;

        messageVO = messageList.get(position);

        holder.messageTitle = (TextView) view.findViewById(R.id.message_title);
        holder.messageContent = (TextView) view.findViewById(R.id.message_content);
        holder.messageDate = (TextView) view.findViewById(R.id.message_date);
        holder.messageImage = (ImageView) view.findViewById(R.id.message_image);
        holder.messageBar = (ProgressBar) view.findViewById(R.id.message_bar);

        if (holder.messageTitle != null && null != messageVO.getTitle()
                && messageVO.getTitle().trim().length() > 0) {
            holder.messageTitle.setText(Html.fromHtml(messageVO.getTitle()));
        }
        if (holder.messageContent != null && null != messageVO.getContent()
                && messageVO.getContent().trim().length() > 0) {
            holder.messageContent.setText(Html.fromHtml(messageVO.getContent()));
        }
        if (holder.messageDate != null && null != messageVO.getSendDate()
                && messageVO.getSendDate().trim().length() > 0) {
            holder.messageDate.setText(Html.fromHtml(messageVO.getSendDate()));
        }
        if (holder.messageImage != null) {
            if (null != messageVO.getUploadFile()
                    && messageVO.getUploadFile().trim().length() > 0) {
                final ProgressBar pbar = holder.messageBar;

                imageLoader.init(ImageLoaderConfiguration
                        .createDefault(messageActivity));
                imageLoader.displayImage(ConstantsUtil.SERVER_API_URL_REAL + ConstantsUtil.SERVER_MESSAGE_IMAGE_URL
                        + messageList.get(position).getUploadFile(), holder.messageImage, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        pbar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        pbar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        pbar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        pbar.setVisibility(View.INVISIBLE);
                    }
                });

            } else {
                holder.messageImage.setImageResource(R.mipmap.ic_launcher);
            }
        }
        return view;
    }

    class ViewHolder {
        public TextView messageTitle;
        public TextView messageContent;
        public TextView messageDate;
        public ImageView messageImage;
        public ProgressBar messageBar;
    }

}
