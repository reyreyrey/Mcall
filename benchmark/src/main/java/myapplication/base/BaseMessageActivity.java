package myapplication.base;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.ViewDataBinding;

import myapplication.utils.Log2File;
import tgio.benchmark.R;

/**
 * @作者 ：guocongcong
 * @日期：2022.08.19 15:09
 */
public abstract class BaseMessageActivity<T extends ViewDataBinding> extends BaseActivity<T>{

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String text = (String) msg.obj;
                    getHintTextView().append(text);
                    getHintTextView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            getScrollView().post(new Runnable() {
                                public void run() {
                                    getScrollView().fullScroll(View.FOCUS_DOWN);
                                }
                            });
                        }
                    });
                    break;
                case 2:
                    String showDialogMsg = (String) msg.obj;
                    new AlertDialog.Builder(context).setMessage(showDialogMsg).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).create().show();
                    break;
            }
        }
    };

    protected TextView getHintTextView(){
        return findViewById(R.id.tv_hint);
    }

    protected ScrollView getScrollView(){
        return findViewById(R.id.scrollview);
    }

    protected void sendTextMessage(String msg) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = msg + "\n";
        Log2File.w(msg + "\n");
        handler.sendMessage(message);
    }

    protected void sendDialogMessage(String msg) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = msg + "\n";
        handler.sendMessage(message);
    }
}
