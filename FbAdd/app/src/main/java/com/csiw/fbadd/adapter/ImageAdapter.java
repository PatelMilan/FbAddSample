package com.csiw.fbadd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.csiw.fbadd.R;
import com.csiw.fbadd.interfaces.ImageClickListener;
import com.csiw.fbadd.model.ImageResponse;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_AD = 1;
    private static final int TYPE_CONTACT = 0;
    private static final int AD_FREQUENCY = 5;

    private Context mContext;
    private List<ImageResponse> mImageResponseList;

    public ImageAdapter(Context mContext, List<ImageResponse> mImageResponseList) {
        this.mContext = mContext;
        this.mImageResponseList = mImageResponseList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        if (viewType == TYPE_AD) {
            final View mAdView = LayoutInflater.from(mContext).inflate(R.layout.rectangle_ad_item_view, parent, false);
            mAdView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    final ViewGroup.LayoutParams layoutParams = mAdView.getLayoutParams();
                    if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                        StaggeredGridLayoutManager.LayoutParams sglp =
                                (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                        sglp.setFullSpan(true);
                        mAdView.setLayoutParams(sglp);
                        final StaggeredGridLayoutManager lm =
                                (StaggeredGridLayoutManager) ((RecyclerView) parent).getLayoutManager();
                        assert lm != null;
                        lm.invalidateSpanAssignments();
                    }
                    mAdView.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
            return new ImageAdapter.AdHolder(mAdView);
        } else {
            return new ImageAdapter.ContactHolder(LayoutInflater.from(mContext).inflate(R.layout.contct_item_view, parent, false));
        }
    }

    @Override
    public int getItemCount() {
        return mImageResponseList.size() + ((int) mImageResponseList.size() / 4);
    }

    @Override
    public int getItemViewType(int position) {
        return position % AD_FREQUENCY == 0 ? TYPE_AD : TYPE_CONTACT;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_AD) {
            ImageAdapter.AdHolder adHolder = (ImageAdapter.AdHolder) holder;
            AdView adView = new AdView(mContext, mContext.getString(R.string.rectangle_placement_id), AdSize.RECTANGLE_HEIGHT_250);
            adHolder.mRectangleAd.addView(adView);
            adView.loadAd();

        } else {
            ImageAdapter.ContactHolder contactHolder = (ImageAdapter.ContactHolder) holder;
            final int index = position - (position / AD_FREQUENCY) - 1;

            Glide.with(mContext).load(mImageResponseList.get(index).getImgUrl())
                    .thumbnail(Glide.with(mContext).load(R.raw.ic_loading))
                    .fitCenter()
                    .error(R.drawable.ic_image_error)
                    .into(contactHolder.ivIcon);
            contactHolder.setImageClickListener(new ImageClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(mContext, "Image_ID"+mImageResponseList.get(index).getImgUrl()+"POS"+index, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static class AdHolder extends RecyclerView.ViewHolder {
        RelativeLayout mRectangleAd;

        AdHolder(@NonNull View view) {
            super(view);
            mRectangleAd = view.findViewById(R.id.rectangle_ad);
        }
    }

    public static class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivIcon;
        ImageClickListener mImageClickListener;
        ContactHolder(@NonNull View view) {
            super(view);
            ivIcon = view.findViewById(R.id.iv_icon);
            ivIcon.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.mImageClickListener.onItemClick(view,getLayoutPosition());
        }
        void setImageClickListener(ImageClickListener mImageClickListener){
            this.mImageClickListener = mImageClickListener;
        }
    }
}
