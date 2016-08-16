package com.yuwan.middle.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.pkwan.sdk.PkCommplatform;
import com.pkwan.sdk.constant.PkConstant;
import com.pkwan.sdk.entity.PkAppInfo;
import com.pkwan.sdk.entity.PkBuyInfo;
import com.pkwan.sdk.entity.PkRoleInfo;
import com.pkwan.sdk.interfaces.OnInitCompleteListener;
import com.pkwan.sdk.interfaces.OnLoginProcessListener;
import com.pkwan.sdk.interfaces.OnLogoutListener;
import com.pkwan.sdk.interfaces.OnPayProcessListener;

public class MainActivity extends Activity {
	//使用时需把下面参数换成CP自己的appid和appkey
/*
    private String appid = "appid";
    private String appkey = "appkey";
*/

    private String appid = "100030";
    private String appkey = "B904EC49796ACEA96ADFA3E4EBB5D8E7";
	private View pay_btn;
    private View createRole;
    private View login_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity_main);

		PkAppInfo appInfo = new PkAppInfo(appid,appkey);
        PkCommplatform.init(MainActivity.this, appInfo, new OnInitCompleteListener() {
			@Override
			public void onInitComplete(int initCode) {
				if (initCode == PkConstant.INIT_SUCCESS) {
					Toast.makeText(MainActivity.this, "SDK初始化成功", Toast.LENGTH_LONG).show();
				}
			}
		});

      PkCommplatform.addOnLogoutListener(new OnLogoutListener() {
          @Override
          public void onLogout() {
              // 在账号登出时会回调到改方法里，CP可根据需要回到登录界面或重启游戏
              Intent intent = MainActivity.this.getPackageManager().getLaunchIntentForPackage(getPackageName());
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(intent);
          }
      });

        login_btn = findViewById(R.id.login_btn);
		login_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                PkCommplatform.login(new OnLoginProcessListener() {
					@Override
					public void finishLoginProcess(int loginCode) {
						if (PkConstant.LOGIN_SUCCESS == loginCode) {// 登陆成功
							String token = PkCommplatform.getToken(MainActivity.this);

							//显示浮标
							PkCommplatform.showFloatWindow(MainActivity.this);
							//提交游戏玩家信息
                            login_btn.setVisibility(View.GONE);
							pay_btn.setVisibility(View.VISIBLE);
                            createRole.setVisibility(View.VISIBLE);
							Toast.makeText(MainActivity.this, "token：" + token, Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		});
	
		pay_btn = findViewById(R.id.pay_btn);
		pay_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                startPay();
			}
		});

        createRole = findViewById(R.id.create_role);
        createRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subPlayerInfo();
            }
        });
	}

	private void subPlayerInfo() {
        Toast.makeText(MainActivity.this,"创建游戏角色，提交角色信息",Toast.LENGTH_SHORT).show();

		PkRoleInfo roleInfo = new PkRoleInfo("齐天大圣","争霸天下");
		PkCommplatform.submitPlayerInfo(roleInfo);
	}

	private void startPay() {
		String orderid = System.currentTimeMillis() + ""; //
		String itemName = "昆仑天晶";
		int gredit = 100;

		PkBuyInfo buyInfo = new PkBuyInfo(orderid, itemName, gredit);
		buyInfo.setNote("支付测试");
		buyInfo.setNoticeUrl("http://sdk.yuwan8.com");
		buyInfo.setExchangeRate(10);
		PkCommplatform.pay(buyInfo, new OnPayProcessListener() {
			@Override
			public void finishPayProcess(int payCode) {
				if (PkConstant.PAY_SUCCESS == payCode) {
					Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_LONG).show();
				} else if (PkConstant.PAY_FAIL == payCode) {
					Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_LONG).show();
				} else if (PkConstant.PAY_CANCEL == payCode) {
					Toast.makeText(MainActivity.this, "支付取消", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
        PkCommplatform.destory();
		super.onDestroy();
	}

	private void showExitDialog() {
		new Handler(MainActivity.this.getMainLooper()).post(new Runnable() {

			@Override
			public void run() {
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("提示");
				builder.setMessage("您确定要退出吗？");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
												int which) {
								dialog.dismiss();
								finish();

							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
												int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
			}
		});
	}
}
