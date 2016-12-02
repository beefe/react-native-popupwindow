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

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

/**
 * Created by heng on 16/02/26.
 * Edited by heng on 16/03/15.
 * <p>
 * Edited by heng on 16/12/02.
 * Supported RN v0.29+
 */
public class PopupWindowModule extends ReactContextBaseJavaModule {

    private static final String REACT_CLASS = "PopupWindow";

    private static final String ERR_OPTIONS_IS_NULL = "options must be not null";
    private static final String ERR_ACTIVITY_IS_NULL = "activity is null";

    private static final String ACTION_BUTTON_CLICKED = "buttonClicked";
    private static final String WINDOW_COLOR = "windowColor";
    private static final String STYLE = "style";
    private static final String SINGLE = "single";
    private static final String TITLE = "title";
    private static final String MARGIN = "margin";
    private static final String TITLE_TEXT_SIZE = "titleTextSize";
    private static final String TITLE_TEXT_COLOR = "titleTextColor";
    private static final String MESSAGE = "message";
    private static final String MESSAGE_TEXT_SIZE = "messageTextSize";
    private static final String MESSAGE_TEXT_COLOR = "messageTextColor";
    private static final String BUTTON_POSITIVE = "positive";
    private static final String BUTTON_POSITIVE_TEXT_SIZE = "positiveTextSize";
    private static final String BUTTON_POSITIVE_TEXT_COLOR = "positiveTextColor";
    private static final String BUTTON_NEGATIVE = "negative";
    private static final String BUTTON_NEGATIVE_TEXT_SIZE = "negativeTextSize";
    private static final String BUTTON_NEGATIVE_TEXT_COLOR = "negativeTextColor";


    private PopupWindow popupWindow;

    private TextView title;
    private TextView message;
    private TextView positive;
    private TextView negative;
    private LinearLayout centerLayout;
    private LinearLayout rightLayout;
    private RelativeLayout bottomLayout;

    public PopupWindowModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void showPopupWindow(ReadableMap options, final Callback callback) {
        Activity activity = getCurrentActivity();
        if (callback == null) {
            return;
        }
        if (activity == null) {
            callback.invoke(ERR_ACTIVITY_IS_NULL);
            return;
        }
        if (options == null) {
            callback.invoke(ERR_OPTIONS_IS_NULL);
            return;
        }

        View view;
        int style = 0;
        if (options.hasKey(STYLE)) {
            style = options.getInt(STYLE);
        }
        switch (style) {
            case 0:
                view = initCenter(activity);
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
                break;
            default:
                view = initBottom(activity);
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
                    layoutParams.addRule(RelativeLayout.ABOVE, R.id.cancel);
                    layoutParams.setMargins(margin, 0, margin, 0);
                    bottomLayout.setLayoutParams(layoutParams);
                }
                break;
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
                callback.invoke(null, ACTION_BUTTON_CLICKED, BUTTON_POSITIVE);
                popupWindow.dismiss();
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
                popupWindow.dismiss();
                callback.invoke(null, ACTION_BUTTON_CLICKED, BUTTON_NEGATIVE);
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

    private View initCenter(Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.popup_window_center, null);
        centerLayout = (LinearLayout) view.findViewById(R.id.centerLayout);
        title = (TextView) view.findViewById(R.id.centerTitle);
        message = (TextView) view.findViewById(R.id.centerMessage);
        positive = (TextView) view.findViewById(R.id.leftBtn);
        rightLayout = (LinearLayout) view.findViewById(R.id.rightLayout);
        negative = (TextView) view.findViewById(R.id.rightBtn);
        return view;
    }


    private View initBottom(Activity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.popup_window_bottom, null);
        bottomLayout = (RelativeLayout) view.findViewById(R.id.bottomLayout);
        message = (TextView) view.findViewById(R.id.bottomMessage);
        positive = (TextView) view.findViewById(R.id.bottomSure);
        negative = (TextView) view.findViewById(R.id.cancel);
        return view;
    }
}
