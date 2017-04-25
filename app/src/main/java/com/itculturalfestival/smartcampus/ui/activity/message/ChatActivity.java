package com.itculturalfestival.smartcampus.ui.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.ui.base.BaseFragmentActivity;
import com.itculturalfestival.smartcampus.ui.fragment.message.ChatFragment;

import java.util.List;

/**
 * @creation_time: 2017/4/21
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 聊天界面
 */

public class ChatActivity extends BaseFragmentActivity {
    private EaseChatFragment chatFragment;
    private ChatFragment myChatFragment;
    private String TAG="ChatActivity";
    private android.app.AlertDialog.Builder exceptionBuilder;
    private boolean isExceptionDialogShow =  false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("conversation");
//        bundle.getString(EaseConstant.EXTRA_USER_ID);
//        chatFragment=new EaseChatFragment();
//        chatFragment.setArguments(bundle);
        myChatFragment=new ChatFragment();
        myChatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.conversation_container,myChatFragment)
                .commit();


    }

}

//    // 聊天信息输入框
//    private EditText mInputEdit;
//    // 发送按钮
//    private Button mSendBtn;
//
//    // 显示内容的 TextView
//    private TextView mContentText;
//
//    // 消息监听器
//    private EMMessageListener mMessageListener;
//    // 当前聊天的 ID
//    private String mChatId;
//    // 当前会话对象
//    private EMConversation mConversation;
//
//    /**
//     * 自定义实现Handler，主要用于刷新UI操作
//     */
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    EMMessage message = (EMMessage) msg.obj;
//                    // 这里只是简单的demo，也只是测试文字消息的收发，所以直接将body转为EMTextMessageBody去获取内容
//                    EMTextMessageBody body = (EMTextMessageBody) message.getBody();
//                    // 将新的消息内容和时间加入到下边
//                    mContentText.setText(mContentText.getText() + "\n接收：" + body.getMessage() + " - time: " + message.getMsgTime());
//                    break;
//            }
//        }
//    };
//
//    @Override
//    protected void onCreate() {
//        initLayout(R.layout.activity_chat);
//        // 获取当前会话的username(如果是群聊就是群id)
//        mChatId = getIntent().getStringExtra("ec_chat_id");
//        mMessageListener = this;
//        EaseChatFragment chatFragment = new EaseChatFragment();
//        chatFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction().add(R.id.ec_text_content, chatFragment).commit();
//
//        initView();
//        initConversation();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        // 添加消息监听
//        EMClient.getInstance().chatManager().addMessageListener(mMessageListener);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        // 移除消息监听
//        EMClient.getInstance().chatManager().removeMessageListener(mMessageListener);
//    }
//
//    /**
//     * 初始化界面
//     */
//    private void initView() {
//        mInputEdit = (EditText) findViewById(R.id.ec_edit_message_input);
//        mSendBtn = (Button) findViewById(R.id.ec_btn_send);
//        mContentText = (TextView) findViewById(R.id.ec_text_content);
//        // 设置textview可滚动，需配合xml布局设置
//        mContentText.setMovementMethod(new ScrollingMovementMethod());
//
//        // 设置发送按钮的点击事件
//        mSendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String content = mInputEdit.getText().toString().trim();
//                if (!TextUtils.isEmpty(content)) {
//                    mInputEdit.setText("");
//                    // 创建一条新消息，第一个参数为消息内容，第二个为接受者username
//                    EMMessage message = EMMessage.createTxtSendMessage(content, mChatId);
//                    // 将新的消息内容和时间加入到下边
//                    mContentText.setText(mContentText.getText() + "\n发送：" + content + " - time: " + message.getMsgTime());
//                    // 调用发送消息的方法
//                    EMClient.getInstance().chatManager().sendMessage(message);
//                    // 为消息设置回调
//                    message.setMessageStatusCallback(new EMCallBack() {
//                        @Override
//                        public void onSuccess() {
//                            // 消息发送成功，打印下日志，正常操作应该去刷新ui
//                            Log.i("ChatActivity", "send message on success");
//                        }
//
//                        @Override
//                        public void onError(int i, String s) {
//                            // 消息发送失败，打印下失败的信息，正常操作应该去刷新ui
//                            Log.i("ChatActivity", "send message on error " + i + " - " + s);
//                        }
//
//                        @Override
//                        public void onProgress(int i, String s) {
//                            // 消息发送进度，一般只有在发送图片和文件等消息才会有回调，txt不回调
//                        }
//                    });
//                }
//            }
//        });
//    }
//
//    /**
//     * 初始化会话对象，并且根据需要加载更多消息
//     */
//    private void initConversation() {
//
//        /**
//         * 初始化会话对象，这里有三个参数么，
//         * 第一个表示会话的当前聊天的 useranme 或者 groupid
//         * 第二个是绘画类型可以为空
//         * 第三个表示如果会话不存在是否创建
//         */
//        mConversation = EMClient.getInstance().chatManager().getConversation(mChatId, null, true);
//        // 设置当前会话未读数为 0
//        mConversation.markAllMessagesAsRead();
//        int count = mConversation.getAllMessages().size();
//        if (count < mConversation.getAllMsgCount() && count < 20) {
//            // 获取已经在列表中的最上边的一条消息id
//            String msgId = mConversation.getAllMessages().get(0).getMsgId();
//            // 分页加载更多消息，需要传递已经加载的消息的最上边一条消息的id，以及需要加载的消息的条数
//            mConversation.loadMoreMsgFromDB(msgId, 20 - count);
//        }
//        // 打开聊天界面获取最后一条消息内容并显示
//        if (mConversation.getAllMessages().size() > 0) {
//            EMMessage messge = mConversation.getLastMessage();
//            EMTextMessageBody body = (EMTextMessageBody) messge.getBody();
//            // 将消息内容和时间显示出来
//            mContentText.setText("聊天记录：" + body.getMessage() + " - time: " + mConversation.getLastMessage().getMsgTime());
//        }
//    }
//
//
//    @Override
//    public void onMessageReceived(List<EMMessage> list) {
//        // 循环遍历当前收到的消息
//        for (EMMessage message : list) {
//            if (message.getFrom().equals(mChatId)) {
//                // 设置消息为已读
//                mConversation.markMessageAsRead(message.getMsgId());
//
//                // 因为消息监听回调这里是非ui线程，所以要用handler去更新ui
//                Message msg = mHandler.obtainMessage();
//                msg.what = 0;
//                msg.obj = message;
//                mHandler.sendMessage(msg);
//            } else {
//                // 如果消息不是当前会话的消息发送通知栏通知
//            }
//        }
//    }
//
//    @Override
//    public void onCmdMessageReceived(List<EMMessage> list) {
//        for (int i = 0; i < list.size(); i++) {
//            // 透传消息
//            EMMessage cmdMessage = list.get(i);
//            EMCmdMessageBody body = (EMCmdMessageBody) cmdMessage.getBody();
//            Log.i("ChatActivity", body.action());
//        }
//    }
//
//    @Override
//    public void onMessageRead(List<EMMessage> list) {
//
//    }
//
//    @Override
//    public void onMessageDelivered(List<EMMessage> list) {
//
//    }
//
//    @Override
//    public void onMessageChanged(EMMessage emMessage, Object o) {
//
//    }
//}
