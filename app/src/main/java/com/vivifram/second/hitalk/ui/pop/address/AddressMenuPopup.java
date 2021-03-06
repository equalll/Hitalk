package com.vivifram.second.hitalk.ui.pop.address;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.vivifram.second.hitalk.R;
import com.vivifram.second.hitalk.ui.pop.BasePopupWindow;
import com.zuowei.utils.common.DisplayUtil;

/**
 * Created by zuowei on 16-10-22.
 */

public class AddressMenuPopup extends BasePopupWindow {

    private View popupView;

    private TextView addFriendTv;
    private TextView createGroupTv;

    private onMenuItemClick onMenuItemClick;

    public interface  onMenuItemClick{
        void onItemClick(int pos);
    }

    public AddressMenuPopup(Activity context) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupView.measure(
                View.MeasureSpec.
                        makeMeasureSpec(DisplayUtil.getDisplayMetrics(mContext).widthPixels, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.
                        makeMeasureSpec(DisplayUtil.getDisplayMetrics(mContext).heightPixels, View.MeasureSpec.AT_MOST));

        addFriendTv = (TextView) findViewById(R.id.addFriendTv);
        addFriendTv.setOnClickListener(view->{
            mPopupWindow.dismiss();
            if (onMenuItemClick != null) {
                onMenuItemClick.onItemClick(0);
            }
        });

        createGroupTv = (TextView) findViewById(R.id.createGroupTv);
        createGroupTv.setOnClickListener(view->{
            mPopupWindow.dismiss();
            if (onMenuItemClick != null) {
                onMenuItemClick.onItemClick(1);
            }
        });

    }

    public AddressMenuPopup setOnMenuItemClick(AddressMenuPopup.onMenuItemClick onMenuItemClick) {
        this.onMenuItemClick = onMenuItemClick;
        return this;
    }

    @Override
    public void showPopupWindow(View v) {
        try {
            int screenWidth = DisplayUtil.getDisplayMetrics(v.getContext()).widthPixels;
            int arrowPadding = DisplayUtil.dip2px(2);
            mPopupWindow.showAsDropDown(v,-getPopupView().getMeasuredWidth() + v.getWidth() - v.getPaddingRight() - arrowPadding,-v.getHeight() / 3);
            if (getShowAnimation() != null && mAnimaView != null) {
                mAnimaView.clearAnimation();
                mAnimaView.startAnimation(getShowAnimation());
            }
            if (getShowAnimation() == null && getShowAnimator() != null && mAnimaView != null) {
                getShowAnimator().start();
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected Animation getShowAnimation() {
        AnimationSet set=new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(getScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,1,Animation.RELATIVE_TO_SELF,0));
        set.addAnimation(getDefaultAlphaAnimation());
        return set;
    }

    @Override
    protected View getClickToDismissView() {
        return null;
    }

    @Override
    public View getPopupView() {
        if (popupView == null) {
            popupView = getPopupViewById(R.layout.address_menu_popup_layout);
        }
        return popupView;
    }

    @Override
    public View getAnimaView() {
        return mPopupView.findViewById(R.id.menuBubble);
    }
}
