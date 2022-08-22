package myapplication.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
public abstract class BaseMessageActivity<T extends ViewDataBinding> extends BaseActivity<T> {


    protected String code = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String text = (String) msg.obj;
                    if (text.equals("clear")) {
                        getHintTextView().setText("");
                        return;
                    }
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
                        }
                    }).create().show();
                    break;
                case 3:
                    if (dialog != null)
                        dialog.show();
                    break;
                case 4:
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                    break;
                case 5:
                    EditText editText = new EditText(context);
                    editText.setInputType(EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
                    editText.setHint("输入验证码");
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(editText);
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            code = editText.getText().toString().trim();
                            dialog.dismiss();
                            clickListener.onClick(dialog,which);
                        }
                    });
                    builder.create().show();
                    break;
                case 6:
                    String showDialogMsg1 = (String) msg.obj;
                    new AlertDialog.Builder(context).setMessage(showDialogMsg1).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                    break;
            }
        }
    };

    protected TextView getHintTextView() {
        return findViewById(R.id.tv_hint);
    }

    protected ScrollView getScrollView() {
        return findViewById(R.id.scrollview);
    }

    protected void sendTextMessage(String msg) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = msg + "\n";
        handler.sendMessage(message);
    }

    protected void sendDialogMessage(String msg) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = msg + "\n";
        handler.sendMessage(message);
    }


    protected void showProgressDialog() {
        Message message = Message.obtain();
        message.what = 3;
        handler.sendMessage(message);
    }

    protected void dismissProgressDialog() {
        Message message = Message.obtain();
        message.what = 4;
        handler.sendMessage(message);
    }
    DialogInterface.OnClickListener clickListener;
    protected void inputCode(DialogInterface.OnClickListener clickListener) {
        this.clickListener = clickListener;
        Message message = Message.obtain();
        message.what = 5;
        handler.sendMessage(message);
    }
}
