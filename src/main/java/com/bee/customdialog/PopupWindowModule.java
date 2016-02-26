package com.bee.customdialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

/**
 * Created by heng on 16/2/26.
 */
public class PopupWindowModule extends ReactContextBaseJavaModule {

    public static final String REACT_CLASS = "PopupWindow";

    static final String ACTION_BUTTON_CLICKED = "buttonClicked";
    static final String KEY_MESSAGE = "message";
    static final String KEY_BUTTON_POSITIVE = "buttonPositive";
    static final String KEY_BUTTON_NEGATIVE = "buttonNegative";

    public PopupWindowModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void showPopupWindow(ReadableMap options, final Callback callback) {
        View view = LayoutInflater.from(getReactApplicationContext()).inflate(R.layout.popup_window, null);
        final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        TextView text = (TextView) view.findViewById(R.id.text);
        TextView leftBtn = (TextView) view.findViewById(R.id.leftBtn);
        LinearLayout rightLayout = (LinearLayout) view.findViewById(R.id.rightLayout);
        TextView rightBtn = (TextView) view.findViewById(R.id.rightBtn);
        if (options.hasKey(KEY_MESSAGE)) {
            text.setText(options.getString(KEY_MESSAGE));
        }
        if (options.hasKey(KEY_BUTTON_POSITIVE)) {
            leftBtn.setText(options.getString(KEY_BUTTON_POSITIVE));
            leftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        popupWindow.dismiss();
                        callback.invoke(ACTION_BUTTON_CLICKED,KEY_BUTTON_POSITIVE);
                    }
                }
            });
        }
        if (options.hasKey(KEY_BUTTON_NEGATIVE)) {
            rightLayout.setVisibility(View.VISIBLE);
            rightBtn.setText(options.getString(KEY_BUTTON_NEGATIVE));
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        popupWindow.dismiss();
                        callback.invoke(ACTION_BUTTON_CLICKED,KEY_BUTTON_NEGATIVE);
                    }
                }
            });
        }
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#50000000")));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

}
