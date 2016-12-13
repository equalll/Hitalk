package com.vivifram.second.hitalk.ui.layout;

import android.view.View;

import com.vivifram.second.hitalk.R;
import com.vivifram.second.hitalk.ui.view.BGATitlebar;

/**
 * 项目名称：Hitalk
 * 类描述：
 * 创建人：zuowei
 * 创建时间：16-12-8 下午3:35
 * 修改人：zuowei
 * 修改时间：16-12-8 下午3:35
 * 修改备注：
 */
public class AddFriendLayout extends BaseLayout{

    private View scanQrV;
    private View addressV;

    public interface OnTitleActionListener{
        void onBack();
        void showQR();
    }

    public interface OnItemClickListener{
        void onScanQr();
        void onAddAddress();
    }

    public AddFriendLayout(View rootView) {
        super(rootView);
    }

    private OnTitleActionListener onTitleActionListener;
    private OnItemClickListener onItemClickListener;

    private BGATitlebar titlebar;

    @Override
    public void onContentViewCreate(View view) {
        super.onContentViewCreate(view);

        titlebar = (BGATitlebar) findViewById(R.id.titleBar);
        titlebar.setDelegate(new BGATitlebar.BGATitlebarDelegate(){
            @Override
            public void onClickLeftCtv() {
                super.onClickLeftCtv();
                if (onTitleActionListener != null) {
                    onTitleActionListener.onBack();
                }
            }

            @Override
            public void onClickRightCtv() {
                super.onClickRightCtv();
                if (onTitleActionListener != null) {
                    onTitleActionListener.showQR();
                }
            }
        });

        scanQrV = findViewById(R.id.scanQrRl);
        addressV = findViewById(R.id.addressRl);

        View.OnClickListener onClickListener = v -> {
            switch (v.getId()){
                case R.id.scanQrRl:
                        if (onItemClickListener != null) {
                            onItemClickListener.onScanQr();
                        }
                    break;
                case R.id.addressRl:
                        if (onItemClickListener != null) {
                            onItemClickListener.onAddAddress();
                        }
                    break;
            }
        };

        scanQrV.setOnClickListener(onClickListener);
        addressV.setOnClickListener(onClickListener);
    }

    public AddFriendLayout setOnTitleActionListener(OnTitleActionListener onTitleActionListener) {
        this.onTitleActionListener = onTitleActionListener;
        return this;
    }

    public AddFriendLayout setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }
}
