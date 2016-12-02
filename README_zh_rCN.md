# react-native-popupwindow
在Android端实现iOS风格的对话框

## 提供以下方法

### showPopupWindow(options,callback)
options : 参数设置
 * windowColor : 背景颜色，默认是半透明(#50000000)
 * style : 样式，在界面中间或在界面底部显示,默认为中间显示
  * 0 : 中间显示
  * 1 : 底部显示
 * margin : 对话框与屏幕的边距(style为0时默认30dp；style为1时默认9dp)
 * title : 标题，默认隐藏，设置本属性后会显示(仅当style为0时有效)
 * titleTextSize : 标题的字体大小，默认17sp(仅当style为0时有效)
 * titleTextColor : 标题的字体颜色，默认系统颜色(仅当style为0时有效)
 * single(仅当style为0时有效，不设置默认显示两个按钮)
  * false : 两个按钮
  * true : 单个按钮(此时只有positive有效，negative处于隐藏状态)
 * message : 提示信息
 * messageTextSize : 信息的字体大小(style为0时默认17sp；style为1时系统默认大小)
 * messageTextColor : 信息的字体颜色
 * positive : 确定按钮文字(style为0且single为false时为左侧按钮，single为true时只有此按钮；style为1时为上面按钮)
 * positiveTextSize : 确定按钮文字字体大小，默认17sp
 * positiveTextColor : 确定按钮文字字体颜色
 * negative : 取消按钮文字(style为0且single为false时为右侧按钮，single为true时此按钮会隐藏；style为1时为底部按钮)
 * negativeTextSize : 取消按钮文字字体大小，默认17sp
 * negativeTextColor : 取消按钮文字字体颜色

callback(err,action,button)
* err : 返回的错误信息
* action : 返回"buttonClicked"
* button : 返回"positive"或者"negative"

## 安装及使用

#### 安装npm包

```shell
$ npm install --save react-native-popupwindow
```

#### 添加link（引用）

```shell
$ react-native link react-native-popupwindow
```

#### 在你的JS文件中使用 

```javascript
import React, { Component } from 'react';

import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  ToastAndroid
} from 'react-native';

import MyPop from 'react-native-popupwindow';

let options = {
};

class PopupWindow extends Component {
  center(){
    options.style = 0;
    MyPop.showPopupWindow(options,(err,action,button) =>{
          if(err){
            ToastAndroid.show(err,ToastAndroid.SHORT);
          }else{
            if(action === 'buttonClicked'){
                if(button === 'positive'){
                  ToastAndroid.show('点击确定',ToastAndroid.SHORT);
                }else if(button === 'negative'){
                  ToastAndroid.show('点击取消',ToastAndroid.SHORT);
                }
            }
          }
      });
  }
  bottom(){
    options.style = 1;
    MyPop.showPopupWindow(options,(err,action,button) =>{
          if(err){
            ToastAndroid.show(err,ToastAndroid.SHORT);
          }else{
            if(action === 'buttonClicked'){
                if(button === 'positive'){
                  ToastAndroid.show('点击确定',ToastAndroid.SHORT);
                }else if(button === 'negative'){
                  ToastAndroid.show('点击取消',ToastAndroid.SHORT);
                }
            }
          }
      });
  }
  render() {
    return (
        <View style={styles.container}>
          <Text style={styles.welcome} onPress={this.center}>
              中间显示
           </Text>
          <Text style={styles.welcome} onPress={this.bottom}>
              底部显示
          </Text>
        </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});

AppRegistry.registerComponent('PopupWindow', () => PopupWindow);
```

## 运行效果图

![rendering](/popup.gif)