# react-native-popupwindow
Android PopupWindow for react-native module 。

## 写在前面
因为在国内很多APP设计都只考虑iOS的界面风格，完全不管Android，直接把iOS的设计图拿来让Android照做，
然而react-native提供的Alert使用的是Android原生的Dialog，so,本module应运而生 —— 使用Android原生的PopupWindow来实现iOS风格
的对话框。

## 提供以下方法

### showPopupWindow(options,callback)
options : 参数设置，不允许为空
 * windowColor : 背景颜色，默认是半透明(#50000000)
 * style : 样式，目前提供在界面中间和界面底部两种显示
  * 0 : 中间显示，不设置默认为0
  * 1 : 底部显示
 * margin : 布局与屏幕的边距大小(style为0时默认30dp；style为1时默认9dp，包括取消按钮的边距)
 * title : 标题，默认隐藏，设置本属性后会显示(仅当style为0时有效)
 * titleTextSize : 标题的字体大小，默认17sp(仅当style为0时有效)
 * titleTextColor : 标题的字体颜色，默认系统颜色(仅当style为0时有效)
 * single(仅当style为0时有效，不设置默认显示两个按钮)
  * false : 两个按钮
  * true : 单个按钮(此时只有positive有效，negative处于隐藏状态)
 * message : 提示信息
 * messageTextSize : 信息的字体大小(style为0时默认17sp；style为1时系统默认大小)
 * messageTextColor : 信息的字体颜色(style为0时默认系统颜色；style为1时默认值#aaa)
 * positive : 确定按钮文字(style为0且single为false时为左侧按钮，single为true时只有此按钮；style为1时为上面按钮)
 * positiveTextSize : 确定按钮文字字体大小，默认17sp
 * positiveTextColor : 确定按钮文字字体颜色(style为0时系统默认颜色；style为1时默认值#ffff4444)
 * negative : 取消按钮文字(style为0且single为false时为右侧按钮，single为true时此按钮会隐藏；style为1时为底部单独按钮)
 * negativeTextSize : 取消按钮文字字体大小，默认17sp
 * negativeTextColor : 取消按钮文字字体颜色(style为0时系统默认颜色；style为1时默认值#ff0099cc)

callback(err,action,button) : 回调
* err : 当options为null时返回
* action : 返回"buttonClicked"
* button : 返回"positive" || "negative"

## 运行效果图

![rendering](/popup.gif)

## 安装及使用

#### 安装rnpm包(已安装rnpm包的请忽略本步骤)
```shell
$ npm install rnpm -g
```

#### 安装npm包
```shell
$ npm install --save react-native-popupwindow
```

#### 添加link
```shell
$ rnpm link react-native-popupwindow
```

#### 手动修改MainActivity
因为本Module重载了PopupWindowPackage的构造函数，增加了一个Activity的参数，需要手动改下MainActivity.java文件
```java
...
@Override
    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
            new MainReactPackage(),
            new PopupWindowPackage(MainActivity.this)// 通过link命令行添加的是new PopupWindowPackage(),需要传入MainActivity.this这个参数
        );
    }
...    
```

#### 在你的JS文件中使用 
```javascript
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  View,
  ToastAndroid,
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
