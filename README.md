# SDK当前最新版本：2.2.0

# 一.PK玩SDK构成
------
PK玩SDK由SDK架包，依赖包，SDK所需的资源文件和示例工程组成。
# 二.SDK接入流程简要描述
1.获取(商户Id)AppId 和AppKey、(密钥) AppSecret

2.搭建 SDK 的环境，导入 SDK 的必要文件，参见 SDK开发环境搭建。  

3.初始化 SDK，参见初始化SDK。  

4.捕捉到初始化完成的通知后，根据需要调用登录，参见登录。  

5.支付流程请参照支付。  

6.编译应用，并首先完成自测。  

7.将应用提交给PK玩运营团队进行审核。  

8.审核通过后，与商务人员确定应用推广的渠道相关事宜。  
# 三.SDK环境搭建
## 1.复制SDK包里的资源到您的工程下的对应目录
把PKWanPaySDK-x.x.x.jar包添加至构建路径，并将资源文件复制到您工程的相应目录。
如果您的工程里没有android-support-v4.jar包的话，需要将该jar包拷贝至libs目录下，并添加至构建路径。
## 2.配置AndroidManifest.xml
**添加以下权限：**
```xml
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 <uses-permission android:name="android.permission.INTERNET" />
 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
 <uses-permission android:name="android.permission.GET_TASKS" />
 <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 <uses-permission android:name="android.permission.SYSTEM_OVERLAY_WINDOW" />
 <uses-permission android:name="android.permission.VIBRATE"/>
 <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>

**添加Android组件声明(注意横竖屏区别)：**  

**横屏：**
```xml
<!-- YuWanPaySDK横屏组件声明start-->
	  <activity
            android:name="com.pkwan.sdk.ui.PkAccountActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:label="@string/app_name"
            android:screenOrientation="landscape"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"
            android:windowSoftInputMode="adjustPan">
        </activity>
        <activity
            android:name="com.pkwan.sdk.ui.PkPayDetailActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="landscape"
            android:theme="@android:style/Theme.Translucent.NoTitleBar">
        </activity>
        <activity
            android:name="com.pkwan.sdk.ui.PkPayActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="landscape"
            android:theme="@android:style/Theme.Translucent.NoTitleBar">
        </activity>
        <activity
            android:name="com.pkwan.sdk.ui.PkMsgDialogActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="landscape"
            android:theme="@android:style/Theme.Translucent.NoTitleBar">
        </activity>
        <activity
            android:name="com.pkwan.sdk.ui.PkWebViewActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="landscape"
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:windowSoftInputMode="adjustResize">
        </activity>
        <activity
            android:name="com.pkwan.sdk.ui.PkRegisterProtocolActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="landscape"
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:windowSoftInputMode="adjustResize">
        </activity>
        <activity
            android:name="com.iapppay.pay.channel.weixinpay.WeixinWapPayActivity"
            android:configChanges="screenSize|orientation|keyboard|navigation|layoutDirection"
            android:theme="@android:style/Theme.Translucent" />

<!-- YuWanPaySDK横屏组件声明end-->
```
**竖屏：**
```xml
<!-- YuWanPaySDK竖屏组件声明start-->
	  <activity
            android:name="com.pkwan.sdk.ui.PkAccountActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:label="@string/app_name"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"
            android:windowSoftInputMode="adjustPan">
        </activity>
        <activity
            android:name="com.pkwan.sdk.ui.PkPayDetailActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar">
        </activity>
        <activity
            android:name="com.pkwan.sdk.ui.PkPayActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar">
        </activity>
        <activity
            android:name="com.pkwan.sdk.ui.PkMsgDialogActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar">
        </activity>
        <activity
            android:name="com.pkwan.sdk.ui.PkWebViewActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:windowSoftInputMode="adjustResize">
        </activity>
        <activity
            android:name="com.pkwan.sdk.ui.PkRegisterProtocolActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Light.NoTitleBar"
            android:windowSoftInputMode="adjustResize">
        </activity>
        <activity
            android:name="com.iapppay.pay.channel.weixinpay.WeixinWapPayActivity"
            android:configChanges="screenSize|orientation|keyboard|navigation|layoutDirection"
            android:theme="@android:style/Theme.Translucent" />
<!-- YuWanPaySDK竖屏组件声明end-->
```
# 四.接入API
## 1.初始化SDK
```java
void PkCommplatform.init(Context context, PkAppInfo appInfo, OnInitCompleteListener 	onInitCompleteListener);
```
>* 用于初始化PK玩SDK，在应用的主Activity中必须调用一次该方法。  

>* Context：上下文对象，可以传入当前Activity。  

>* appInfo：AppId和 AppKey、论坛ID等相关参数设置。(由PK玩运营人员提供)  

>* onInitCompleteListener：初始化完成后的通知回调。  

>* 初始化状态回调(initCode)：成功返回PkConstant.INIT_SUCCESS，失败返回PkConstant.INIT_FAILED。

**使用示例：**
```java
//参数分别为AppId  AppKey  论坛ID，由PK玩运营人员分配
PkAppInfo appInfo = new PkAppInfo("10000002", "aeb92e964c1cf89a9ddc717b9a2b9fcc");
PkCommplatform.init(this, appInfo, new OnInitCompleteListener() {
	@Override
	public void onInitComplete(int initCode) {
		if (initCode == PkConstant.INIT_SUCCESS) {
			//TODO Your Code
		}
	}
});
```
## 2.销毁SDK
```java
void PkCommplatform.destory();
```
>* 用来释放SDK资源，在应用的主Activity的onDestory()方法中调用。
**使用示例：**
```java
@Override
protected void onDestroy() {
	PkCommplatform.destory();
	super.onDestroy();
}
```

## 3.登录

```java
void PkCommplatform.login(OnLoginProcessListener loginProcessListener)
```
>* PkCommplatform.init()方法成功回调时调用以及在游戏的开始游戏或登陆按钮按下时调用。  

>* loginProcessListener：登陆完成后的回调接口。  

>* 如果是第一次登陆游戏，弹出登陆窗口。如果在上一次成功登陆游戏，这次会自动登陆，并弹出自动登陆提示页面。  

>* 登陆状态回调(loginCode)：成功返回PkConstant.LOGIN_SUCCESS，失败返回PkConstant.LOGIN_FAILED，用户取消登陆框时返回PkConstant.LOGIN_CANCELED。

**使用示例：**
```java
private OnLoginProcessListener mLoginCallback = new OnLoginProcessListener() {
		@Override
		public void finishLoginProcess(int loginStatus) {
			if (PkConstant.LOGIN_SUCCESS == loginStatus) {// 登陆成功
				//TODO Your Code
			}
		}
	};
//在rginit()方法的成功回调接口内调用
PkCommplatform.init(this, appInfo, new OnInitCompleteListener() {
	@Override
	public void onInitComplete(int initCode) {
	    if (initCode == PkConstant.INIT_SUCCESS) {
                PkCommplatform.login(mLoginCallback);
		//TODO Your Code
		}
	}
});
//在游戏的开始游戏或登陆按钮按下时调用
Button btn_login = (Button) findViewById(R.id.btn_login);
btn_login.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		PkCommplatform.login(mLoginCallback);
	}
});
```

## 4.注销
```java
void PkCommplatform.addOnLogoutListener(OnLogoutListener onLogoutListener)
```
>* 必须在PkCommplatform.init()方法成功回调时调用addOnLogoutListener添加注销监听接口。如果游戏需要，也可以在其他地方添加额外的注销监听接口。 

>* onLogoutListener：注销完成后会回调该对象的onLogout()方法。  

>* **注销账号会调用注销接口。**

**使用示例：**
```java
PkCommplatform.addOnLogoutListener(new OnLogoutListener() {
	@Override
	public void onLogout() {
// 注销帐号处理逻辑，cp可根据自身需要进行操作，重新弹出登录界面或是重启游戏		
Intent i = getPackageManager().getLaunchIntentForPackage(getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
	}
});
```

## 5.显示悬浮窗
```java
void PkCommplatform.showFloatWindow(Activity activity)
```
>* 必须在登陆成功时调用该方法显示悬浮窗。  

>* activity：确保为游戏主Activity。

**使用示例：**
```java
private OnLoginProcessListener mLoginCallback = new OnLoginProcessListener() {
		@Override
		public void finishLoginProcess(int loginStatus) {
			if (PkConstant.LOGIN_SUCCESS == loginStatus) {// 登陆成功
				PkCommplatform.showFloatWindow(GameMainActivity.this);
			}
		}
	};
```
## 6.支付
### a.支付接口说明
```java
void PkCommplatform.pay(RgBuyInfo buyInfo, OnPayProcessListener payListener);
```
>* buiInfo：支付的相关参数设置  

>* payListener：支付结果回调接口  

>* 支付状态回调(payCode)：支付成功返回PkConstant.PAY_SUCCESS,支付失败返回PkConstant.PAY_FAIL，支付取消返回PkConstant.PAY_CANCEL。

**使用示例：**
```java
String orderId = UUID.randomUUID().toString(); // 订单id，必须唯一，必须设置(最长64位)
String itemName = "昆仑天晶";// 道具名称 必须设置
int gredit = 100;// 支付金额 必须设置，单位：分
String note = "原样返回给游戏服务器";// 透传参数，可不设置
PkBuyInfo buyInfo = new PkBuyInfo(orderId, itemName, gredit);
buyInfo.setNote(note);
buyInfo.setExchangeRate(10);//游戏币与人民币的兑换例，如1元=10金币，则值为1。可不设置

PkCommplatform.pay(buyInfo, new OnPayProcessListener() {

@Override
public void finishPayProcess(int payCode) {
Log.d("paycode", "返回支付结果--payCode=" + payCode);
switch (payCode) {
case PkConstant.PAY_SUCCESS:// 支付成功
		Toast.makeText(GameMainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
			break;
case PkConstant.PAY_FAIL:// 支付失败
			Toast.makeText(GameMainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
			break;
case PkConstant.PAY_CANCEL:// 支付取消
			Toast.makeText(GameMainActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
			break;
		default:
		break;
		}
}
});
```

**最后，使用支付请注意以下几点：**
>* 异步购买要求应用有自己的业务服务器，同时虚拟物品必须通过业务服务器获取。  

>* 应用程序客户端只能从服务器获取用户所拥有的虚拟物品的信息，不能本地缓存。例如： 用户通过异步购买，买了一个虚拟物品“战神斧”。购买结束后，用户打开“背包”查看自己的物品信息，背包里的物品信息必须是从服务器获取的。客户端不能在购买行为结束后，为用户加入“战神斧”物品，必须从自己的业务服务器获取！只有自己的业务服务器确认并发回用户拥有了战神斧物品的信息，应用程序才能认为用户确实获得了该物品。  

>* 除了例中需要的参数必须由客户端设置外，RgBuyInfo中其他参数可以不用客户端设置，其中buyInfo.setMerchId会根据初始化时的AppId设置，buyInfo.setEncString会根据初始化时的AppKey设置，buyInfo.setNoticeUrl可传也可不传，如果客户端设置了buyInfo.setNoticeUrl，则使用客户端传递的，否则使用服务端配置的，buyInfo.setMerchName游戏名称可传可不传。  

>* buyInfo.setNote购买时客户端应用通过API传入，通过NoticeUrl原样返回给游戏服务器，开发者可利用该字段定义自己的扩展数据。例如区分游戏服务器，实现指定服务器充值。

### b.支付结果通知
使用异步购买，支付结果将以消息的方式通知到您的业务服务器或者服务端虚拟商店中。应用接入方需要在RgBuyInfo中回传通知地址。您的业务服务器需要处理用户的支付结果通知。接收由鱼丸互动移动开发平台服务器发送给各个应用服务的支付购买结果。服务端对接方式和相应的数据格式具体参见[服务端对接说明文档](server.md)。

## 7.判断是否已登陆
```java
boolean PkCommplatform.isLogined(Context context);
```
## 8.获取当前登陆验证的token
```java
String PkCommplatform.getToken(Context context);
```
## 9.上报角色、区服信息
在玩家创建角色完成后调用
```java
 void PkCommplatform.submitPlayerInfo(PkRoleInfo roleInfo)
```
>* roleInfo：角色、区服信息  

**使用示例：**
```java
String roleName ="一只小菜鸟";
String serverName ="金戈铁马（12服）";
PkRoleInfo roleInfo = new PkRoleInfo(roleName,serverName);
```


**注意：init、login、pay、destory、showFloatWindow这几个方法请务必在主线程调用。**
# 五.混淆
鱼丸互动SDK 包是以 jar包及资源文件提供给用户的，其中jar包已经半混淆状态，您在混淆自 己 APK 包的时候请不要将鱼丸互动SDK的jar包一起混淆，因为里面有些自定义 UI 控件，若被混淆后会因为无法找到相关类而抛异常。
您可以在用 ant 构建混淆包的 build.xml 里面对应位置或者在 proguard.cfg 里加入：
```java
-keep public class com.pkwan.sdk.**
```
以避免 鱼丸互动SDK 的 jar 包被混淆。
