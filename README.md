# react-native-popupwindow
Android PopupWindow for react-native module 。

## 写在前面
因为在国内很多APP设计都只考虑iOS的界面风格，完全不管Android，直接把iOS的设计图拿来让Android照做，
然而react-native提供的Alert使用的是Android原生的Dialog，so,本module应运而生 —— 使用Android原生的PopupWindow来实现iOS风格
的对话框。

## 提供以下方法

### showPopupWindow(options,callback)
options : 弹层的设置，全部支持自定义
* windowColor : 弹层的背景颜色，默认是半透明(#50000000)
* title : 弹层的标题，默认隐藏，设置本属性后会显示
* titleTextSize : 标题的字体大小，默认17sp
* titleTextColor : 标题的字体颜色，系统默认颜色
* message : 弹层的信息
* messageTextSize : 信息的字体大小 ，默认17sp
* messageTextColor : 信息的字体颜色，系统默认颜色
* positive : 左侧按钮文字
* positiveTextSize : 左侧按钮文字字体大小，默认15sp
* positiveTextColor : 左侧按钮文字字体颜色，系统默认颜色
* negative : 右侧按钮文字
* negativeTextSize : 右侧按钮文字字体大小，默认15sp
* negativeTextColor : 右侧按钮文字字体颜色，系统默认颜色

callback(err,action,button) : 回调
* err : 当options为null时返回
* action : 返回"buttonClicked"
* button : 返回"positive" || "negative"
