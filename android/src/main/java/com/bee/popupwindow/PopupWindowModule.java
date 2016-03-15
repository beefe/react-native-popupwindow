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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

/**
 * Created by heng on 16/02/26.
 * Edited by heng on 16/03/15.
 */
public class PopupWindowModule extends BaseJavaModule {

    public static final String REACT_CLASS = "PopupWindow";

    static final String ACTION_BUTTON_CLICKED = "buttonClicked";
    static final String WINDOW_COLOR = "windowColor";
    static final String STYLE = "style";
    static final String SINGLE = "single";
    static final String TITLE = "title";
    static final String MARGIN = "margin";
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

    PopupWindow popupWindow;

    TextView title;
    TextView message;
    TextView positive;
    TextView negative;
    LinearLayout centerLayout;
    LinearLayout rightLayout;
    RelativeLayout bottomLayout;

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
            View view;
            int style = 0;
            if (options.hasKey(STYLE)) {
                style = options.getInt(STYLE);
            }
            if (style == 0) {
                view = initCenter();
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
                if (options.hasKey(SINGLE)) {
                    boolean single = options.getBoolean(SINGLE);
                    if (single) {
                        rightLayout.setVisibility(View.GONE);
                    }
                }
                if (options.hasKey(MARGIN)) {
                    int margin = options.getInt(MARGIN);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(margin, 0, margin, 0);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    centerLayout.setLayoutParams(params);
                }
            } else {
                view = initBottom();
                if (options.hasKey(MARGIN)) {
                    int margin = options.getInt(MARGIN);
                    RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    textParams.setMargins(margin, margin, margin, margin);
                    textParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    negative.setLayoutParams(textParams);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.addRule(RelativeLayout.ABOVE,R.id.cancel);
                    layoutParams.setMargins(margin, 0, margin, 0);
                    bottomLayout.setLayoutParams(layoutParams);
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
                positive.setText(options.getString(BUTTON_POSITIVE));
                if (options.hasKey(BUTTON_POSITIVE_TEXT_SIZE)) {
                    positive.setTextSize(options.getInt(BUTTON_POSITIVE_TEXT_SIZE));
                }
                if (options.hasKey(BUTTON_POSITIVE_TEXT_COLOR)) {
                    positive.setTextColor(Color.parseColor(options.getString(BUTTON_POSITIVE_TEXT_COLOR)));
                }
            }
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        popupWindow.dismiss();
                        callback.invoke(null, ACTION_BUTTON_CLICKED, BUTTON_POSITIVE);
                    }
                }
            });
            if (options.hasKey(BUTTON_NEGATIVE)) {
                negative.setText(options.getString(BUTTON_NEGATIVE));
                if (options.hasKey(BUTTON_NEGATIVE_TEXT_SIZE)) {
                    negative.setTextSize(options.getInt(BUTTON_NEGATIVE_TEXT_SIZE));
                }
                if (options.hasKey(BUTTON_NEGATIVE_TEXT_COLOR)) {
                    negative.setTextColor(Color.parseColor(options.getString(BUTTON_NEGATIVE_TEXT_COLOR)));
                }
            }
            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        popupWindow.dismiss();
                        callback.invoke(null, ACTION_BUTTON_CLICKED, BUTTON_NEGATIVE);
                    }
                }
            });
            popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            ColorDrawable drawable;
            if (options.hasKey(WINDOW_COLOR)) {
                drawable = new ColorDrawable(Color.parseColor(options.getString(WINDOW_COLOR)));
            } else {
                drawable = new ColorDrawable(Color.parseColor("#50000000"));
            }
            popupWindow.setBackgroundDrawable(drawable);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }

    private View initCenter() {
        View view = LayoutInflater.from(activity).inflate(R.layout.popup_window_center, null);
        centerLayout = (LinearLayout) view.findViewById(R.id.centerLayout);
        title = (TextView) view.findViewById(R.id.centerTitle);
        message = (TextView) view.findViewById(R.id.centerMessage);
        positive = (TextView) view.findViewById(R.id.leftBtn);
        rightLayout = (LinearLayout) view.findViewById(R.id.rightLayout);
        negative = (TextView) view.findViewById(R.id.rightBtn);
        return view;
    }


    private View initBottom() {
        View view = LayoutInflater.from(activity).inflate(R.layout.popup_window_bottom, null);
        bottomLayout = (RelativeLayout) view.findViewById(R.id.bottomLayout);
        message = (TextView) view.findViewById(R.id.bottomMessage);
        positive = (TextView) view.findViewById(R.id.bottomSure);
        negative = (TextView) view.findViewById(R.id.cancel);
        return view;
    }
}
