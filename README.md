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

## 运行效果图

![rendering](/result.gif)

## 安装及使用

#### 第一步 : 安装npm包

```shell
npm install --save react-native-popupwindow
```

#### 第二步 : 更新settings.gradle

```gradle
// 文件路径：android/settings.gradle 

...

include ':reactpopupwindow', ':app' 
project(':reactpopupwindow').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-popupwindow')

// 如果有其他的library，这样添加：
 
// include ':app' , ':libraryone' , ':librarytwo' , 'more...'
// project(':libraryonename').projectDir = new File(rootProject.projectDir, '../node_modules/libraryonemodule')
// project(':librarytwoname').projectDir = new File(rootProject.projectDir, '../node_modules/librarytwomodule')
// more..
```

#### 第三步 : 更新app的build.gradle

```gradle
// 文件路径：android/app/build.gradle
// file: android/app/build.gradle
...

dependencies {
    ...
    compile project(':reactpopupwindow')
}
```

#### 第四步 : 注册包

```java
...
import com.bee.popupwindow.PopupWindowPackage;     // 导入类

public class MainActivity extends ReactActivity {

...

    @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                new PopupWindowPackage(MainActivity.this)
        );
    }

```

#### 第五步 : 在你的JS文件中使用 
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
      title: '提示',
      message: '确认退出么？',
      positive: '确定',
      negative: '取消',
    };

class PopupWindow extends Component {
  right(){
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

  error(){
    MyPop.showPopupWindow(null,(err,action,button) =>{
      if(err){
        ToastAndroid.show(err,ToastAndroid.SHORT);
      }else{
        ToastAndroid.show('action = ' + action + '  button = ' + button,ToastAndroid.SHORT);
      }
    });
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome} onPress={this.right}>
          正确示例
        </Text>
        <Text style={styles.welcome} onPress={this.error}>
          错误示例
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
    color: '#333333',
    margin: 10,
  },
});

AppRegistry.registerComponent('PopupWindow', () => PopupWindow);
```
