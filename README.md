# react-native-popupwindow
Android used PopupWindow implementation of the iOS-style dialog box

### [简体中文](/README_zh_rCN.md)

## Method

### showPopupWindow(options,callback)
options : must be not null
 * windowColor : background color,default #50000000
 * style : show in activity center or bottom,default 0
  * 0 : show in center
  * 1 : show in bottom
 * margin : the distance from the screen
 * title : dialog title,default hide,only show when set value and style=0
 * titleTextSize : dialog title text size,default 17sp,only show when set value and style=0
 * titleTextColor : dialog title text color,only show when set value and style=0
 * single : only show when set value and style=0,default false
  * false : has two button(positive and negative)
  * true : single button(only has positive)
 * message : dialog message
 * messageTextSize : dialog message text size
 * messageTextColor : dialog message text color
 * positive : when style=0 and single=false,is left button、when single=true,only has this button; when style=1,is above button
 * positiveTextSize : positive button text size
 * positiveTextColor : positive button text color
 * negative : when style=0 and single=false,is right button、when single=true,this button will be hidden; when style=1,is below button
 * negativeTextSize : negative button text size
 * negativeTextColor : negative button text color

callback(err,action,button)
* err : err message
* action : return "buttonClicked"
* button : return "positive" or "negative"

## Install And Use

#### Npm Install

```shell
$ npm install --save react-native-popupwindow
```

#### Add Link

```shell
$ react-native link react-native-popupwindow
```

#### Use

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
                  ToastAndroid.show('click ok',ToastAndroid.SHORT);
                }else if(button === 'negative'){
                  ToastAndroid.show('click cancel',ToastAndroid.SHORT);
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
                  ToastAndroid.show('click ok',ToastAndroid.SHORT);
                }else if(button === 'negative'){
                  ToastAndroid.show('click cancel',ToastAndroid.SHORT);
                }
            }
          }
      });
  }
  render() {
    return (
        <View style={styles.container}>
          <Text style={styles.welcome} onPress={this.center}>
              show in center
           </Text>
          <Text style={styles.welcome} onPress={this.bottom}>
              show in bottom
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

## Run Renderings

![rendering](/popup.gif)