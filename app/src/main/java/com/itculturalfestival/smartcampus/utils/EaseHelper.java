package com.itculturalfestival.smartcampus.utils;

import android.util.Log;

import com.hyphenate.chat.EMOptions;

/**
 * Created by vegen on 2018/3/27.
 */

public class EaseHelper {
    private EMOptions initChatOptions(){
        Log.d("", "init HuanXin Options");

        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);

        /**
         * NOTE:你需要设置自己申请的Sender ID来使用Google推送功能，详见集成文档
         */
        options.setFCMNumber("921300338324");
        //you need apply & set your own id if you want to use Mi push notification
        options.setMipushConfig("2882303761517426801", "5381742660801");

        //set custom servers, commonly used in private deployment
//        if(demoModel.isCustomServerEnable() && demoModel.getRestServer() != null && demoModel.getIMServer() != null) {
//            options.setRestServer(demoModel.getRestServer());
//            options.setIMServer(demoModel.getIMServer());
//            if(demoModel.getIMServer().contains(":")) {
//                options.setIMServer(demoModel.getIMServer().split(":")[0]);
//                options.setImPort(Integer.valueOf(demoModel.getIMServer().split(":")[1]));
//            }
//        }
//
//        if (demoModel.isCustomAppkeyEnabled() && demoModel.getCutomAppkey() != null && !demoModel.getCutomAppkey().isEmpty()) {
//            options.setAppKey(demoModel.getCutomAppkey());
//        }
//
//        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());
//        options.setDeleteMessagesAsExitGroup(getModel().isDeleteMessagesAsExitGroup());
//        options.setAutoAcceptGroupInvitation(getModel().isAutoAcceptGroupInvitation());
//        // Whether the message attachment is automatically uploaded to the Hyphenate server,
//        options.setAutoTransferMessageAttachments(getModel().isSetTransferFileByUser());
//        // Set Whether auto download thumbnail, default value is true.
//        options.setAutoDownloadThumbnail(getModel().isSetAutodownloadThumbnail());
        return options;
    }

}
