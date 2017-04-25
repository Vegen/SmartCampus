package com.itculturalfestival.smartcampus.ui.fragment.message;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.itculturalfestival.smartcampus.domain.RobotUser;
import com.itculturalfestival.smartcampus.other.Constant;
import com.itculturalfestival.smartcampus.other.EaseUiHelper;
import com.itculturalfestival.smartcampus.util.APPConfig;
import com.itculturalfestival.smartcampus.util.SPUtil;

import java.util.Map;

/**
 * @creation_time: 2017/4/21
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class ChatFragment extends EaseChatFragment implements EaseChatFragmentHelper {

    private static final int ITEM_FILE = 12;

    private static final int REQUEST_CODE_SELECT_FILE = 12;

    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    /**
     * if it is chatBot
     */
    private boolean isRobot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        if (chatType== Constant.CHATTYPE_SINGLE){
            Map<String,RobotUser> robotMap = EaseUiHelper.getInstance().getRobotList();
            if(robotMap!=null && robotMap.containsKey(toChatUsername)){
                isRobot = true;
            }
        }
        super.setUpView();
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if(isRobot){
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }
        //设置要发送扩展消息用户昵称
        message.setAttribute(Constant.USER_NAME, (String) SPUtil.getParam(getActivity().getApplicationContext(), APPConfig.USER_NAME,"nike"));
        //设置要发送扩展消息用户头像
        message.setAttribute(Constant.HEAD_IMAGE_URL, (String) SPUtil.getParam(getActivity().getApplicationContext(),APPConfig.USER_HEAD_IMG,""));

    }


    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }



    @Override
    public void onEnterToChatDetails() {
    }

    @Override
    public void onAvatarClick(String username) {
    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_FILE: //file
                selectFileFromLocal();
                break;
            default:
                break;
        }
        //keep exist extend menu
        return false;
    }

    /**
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }


    /**
     * chat row provider
     *
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 12;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if(message.getType() == EMMessage.Type.TXT){
                //voice call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                }else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            return null;
        }

    }
}

