package com.elation.myapplication;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import java.util.List;

public class WhatsappAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        if (getRootInActiveWindow() == null) {
//            return;
//        }
        //
        if(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED==event.getEventType()) {
            AccessibilityNodeInfo nodeInfo = event.getSource();
            if (nodeInfo == null) {
                return;

            }

          //  AccessibilityNodeInfoCompat rootInActiveWindow = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());

            // Whatsapp Message EditText id
          //  List<AccessibilityNodeInfoCompat> messageNodeList =nodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry");
//            if (messageNodeList == null || messageNodeList.isEmpty()) {
//                return;
//            }

            // check if the whatsapp message EditText field is filled with text and ending with your suffix (explanation above)


            // Whatsapp send button id

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                List<AccessibilityNodeInfo> sendMessageNodeInfoList  = nodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");
                for (AccessibilityNodeInfo nodeInfoCompat : sendMessageNodeInfoList) {
                    nodeInfoCompat.performAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
                    try {
                        Thread.sleep(2000); // hack for certain devices in which the immediate back click is too fast to handle
                        performGlobalAction(GLOBAL_ACTION_BACK);
                        Thread.sleep(2000);
                        performGlobalAction(GLOBAL_ACTION_BACK);
                    } catch (InterruptedException ignored) {
                        Toast.makeText(this, "acessiblity catch working..", Toast.LENGTH_SHORT).show();
                    }
                }
            }
//        if (sendMessageNodeInfoList == null || sendMessageNodeInfoList.isEmpty()) {
//            return;
//        }
//
//        AccessibilityNodeInfoCompat sendMessageButton = sendMessageNodeInfoList.get(0);
//        if (!sendMessageButton.isVisibleToUser()) {
//            return;
//        }
//
//        // Now fire a click on the send button
//        sendMessageButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);


            // Now go back to your app by clicking on the Android back button twice:
            // First one to leave the conversation screen
            // Second one to leave whatsapp

        }
    }

    @Override
    public void onInterrupt() {

    }
}
