package com.bee.popupwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

/**
 * Created by heng on 16/2/26.
 */
public class PopupWindowModule extends BaseJavaModule {

    public static final String REACT_CLASS = "PopupWindow";

    static final String ACTION_BUTTON_CLICKED = "buttonClicked";
    static final String WINDOW_COLOR = "windowColor";
    static final String TITLE = "title";
    static final String TITLE_TEXT_SIZE = "titleTextSize";
    static final String TITLE_TEXT_COLOR = "titleTextColor";
    static final String MESSAGE = "message";
    static final String MESSAGE_TEXT_SIZE = "messageTextSize";
    static final String MESSAGE_TEXT_COLOR = "messageTextColor";
    static final String BUTTON_POSITIVE = "positive";
    static final String BUTTON_POSITIVE_TEXT_SIZE = "positiveTextSize";
    static final String BUTTON_POSITIVE_TEXT_COLOR = "positiveTextColor";
    static final String BUTTON_NEGATIVE = "negative";
    static final String BUTTON_NEGATIVE_TEXT_SIZE = "negativeTextSize";
    static final String BUTTON_NEGATIVE_TEXT_COLOR = "negativeTextColor";

    Activity activity;

    public PopupWindowModule(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void showPopupWindow(ReadableMap options, final Callback callback) {
        if (options == null) {
            if (callback != null) {
                callback.invoke("options must be not null");
            }
        } else {
            View view = LayoutInflater.from(activity).inflate(R.layout.popup_window, null);
            final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView message = (TextView) view.findViewById(R.id.message);
            TextView leftBtn = (TextView) view.findViewById(R.id.leftBtn);
            LinearLayout rightLayout = (LinearLayout) view.findViewById(R.id.rightLayout);
            TextView rightBtn = (TextView) view.findViewById(R.id.rightBtn);
            ColorDrawable drawable;
            if (options.hasKey(WINDOW_COLOR)) {
                drawable = new ColorDrawable(Color.parseColor(options.getString(WINDOW_COLOR)));
            } else {
                drawable = new ColorDrawable(Color.parseColor("#50000000"));
            }
            popupWindow.setBackgroundDrawable(drawable);
            if (options.hasKey(TITLE)) {
                title.setVisibility(View.VISIBLE);
                title.setText(options.getString(TITLE));
                if (options.hasKey(TITLE_TEXT_SIZE)) {
                    title.setTextSize(options.getInt(TITLE_TEXT_SIZE));
                }
                if (options.hasKey(TITLE_TEXT_COLOR)) {
                    title.setTextColor(Color.parseColor(options.getString(TITLE_TEXT_COLOR)));
                }
            }
            if (options.hasKey(MESSAGE)) {
                message.setText(options.getString(MESSAGE));
                if (options.hasKey(MESSAGE_TEXT_SIZE)) {
                    message.setTextSize(options.getInt(MESSAGE_TEXT_SIZE));
                }
                if (options.hasKey(MESSAGE_TEXT_COLOR)) {
                    message.setTextColor(Color.parseColor(options.getString(MESSAGE_TEXT_COLOR)));
                }
            }
            if (options.hasKey(BUTTON_POSITIVE)) {
                leftBtn.setText(options.getString(BUTTON_POSITIVE));
                if (options.hasKey(BUTTON_POSITIVE_TEXT_SIZE)) {
                    leftBtn.setTextSize(options.getInt(BUTTON_POSITIVE_TEXT_SIZE));
                }
                if (options.hasKey(BUTTON_POSITIVE_TEXT_COLOR)) {
                    leftBtn.setTextColor(Color.parseColor(options.getString(BUTTON_POSITIVE_TEXT_COLOR)));
                }
                leftBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            popupWindow.dismiss();
                            callback.invoke(null, ACTION_BUTTON_CLICKED, BUTTON_POSITIVE);
                        }
                    }
                });
            }
            if (options.hasKey(BUTTON_NEGATIVE)) {
                rightLayout.setVisibility(View.VISIBLE);
                rightBtn.setText(options.getString(BUTTON_NEGATIVE));
                if (options.hasKey(BUTTON_NEGATIVE_TEXT_SIZE)) {
                    rightBtn.setTextSize(options.getInt(BUTTON_NEGATIVE_TEXT_SIZE));
                }
                if (options.hasKey(BUTTON_NEGATIVE_TEXT_COLOR)) {
                    rightBtn.setTextColor(Color.parseColor(options.getString(BUTTON_NEGATIVE_TEXT_COLOR)));
                }
                rightBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            popupWindow.dismiss();
                            callback.invoke(null, ACTION_BUTTON_CLICKED, BUTTON_NEGATIVE);
                        }
                    }
                });
            }
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }

}
