package com.vivifram.second.hitalk.ui.page.layout;

import android.view.MotionEvent;
import android.view.View;

import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.vivifram.second.hitalk.R;
import com.vivifram.second.hitalk.bean.Emojicon;
import com.vivifram.second.hitalk.bean.IMessageWrap;
import com.vivifram.second.hitalk.cache.MessageCacheQueue;
import com.zuowei.utils.bridge.EaterManager;
import com.zuowei.utils.bridge.params.chat.MessageParam;

import java.util.List;

/**
 * Created by zuowei on 16-8-6.
 */
public class HitalkFragmentSub1Layout extends BaseFragmentLayout{
    static final int ITEM_TAKE_PICTURE = 1;
    static final int ITEM_PICTURE = 2;
    static final int ITEM_LOCATION = 3;
    protected int[] itemStrings = { R.string.attach_take_pic, R.string.attach_picture, R.string.attach_location };
    protected int[] itemdrawables = { R.drawable.chat_takepic_selector, R.drawable.chat_image_selector,
            R.drawable.chat_location_selector };
    protected int[] itemIds = { ITEM_TAKE_PICTURE, ITEM_PICTURE, ITEM_LOCATION };
    private ChatMessageListLayout mChatLt;
    private ChatInputMenuLayout mChatMenuLayout;
    private MenuItemClickListener mItemClickListener;
    private MessageCacheQueue mCache;
    private ChatInputMenuLayout.ChatInputMenuListener chatInputMenuListener;

    public HitalkFragmentSub1Layout(View root) {
        super(root);
    }

    @Override
    public void onViewCreate(View root) {
        super.onViewCreate(root);
        mCache = new MessageCacheQueue();
        mChatLt = new ChatMessageListLayout(root);
        mChatLt.onViewCreate(root);
        mChatLt.setMessageFetcher(() -> mCache.poll());

        mChatLt.setOntouchListener((v, event) -> {
            mChatMenuLayout.hideKeyboard();
            mChatMenuLayout.hideExtendMenuContainer();
            return false;
        });

        mChatMenuLayout = (ChatInputMenuLayout) findViewById(R.id.chatInput);
        mItemClickListener = new MenuItemClickListener();
        registerExtendMenuItem();

        mChatMenuLayout.init(null);
        mChatMenuLayout.setChatInputMenuListener(chatInputMenuListener);
    }

    public ChatMessageListLayout getChatLt() {
        return mChatLt;
    }

    public void pushMessages(List<IMessageWrap<AVIMTypedMessage>> messages){
        if (messages != null) {
            mCache.add(messages);
        }
    }

    public void pushMessagesAndRefreshToBottom(List<IMessageWrap<AVIMTypedMessage>> messages){
        pushMessages(messages);
        mChatLt.refreshSelectLast();
    }

    public void pushMessagesAndRefreshToTop(List<IMessageWrap<AVIMTypedMessage>> messages){
        pushMessages(messages);
        mChatLt.refreshSeekTo(0);
    }

    @Override
    public void onViewDestroy() {
        super.onViewDestroy();
        mChatLt.onViewDestroy();
    }

    private void registerExtendMenuItem(){
        for(int i = 0; i < itemStrings.length; i++){
            mChatMenuLayout.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], mItemClickListener);
        }
    }

    public void setMessageListFreshListener(ChatMessageListLayout.IMessageListFreshListener mMessageListFreshListener) {
        if (mChatLt != null) {
            mChatLt.setMessageListFreshListener(mMessageListFreshListener);
        }
    }

    class MenuItemClickListener implements ChatExtendMenuLayout.ChatExtendMenuItemClickListener{

        @Override
        public void onClick(int itemId, View view) {

        }
    }
}
