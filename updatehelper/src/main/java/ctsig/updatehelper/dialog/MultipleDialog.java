package ctsig.updatehelper.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ctsig.updatehelper.R;


/**
 * Created by wc on 2016/6/1.
 */
public class MultipleDialog extends Dialog {
    public static final int NORMAL_TYPE = 0;

    private LinearLayout mProgressLayout;
    private DoughnutProgress mDoughnutProgress;
    private RelativeLayout mSimpleLayout;
    private Button confirmBtn;
    private Button cancelBtn;
    private View whiteSpace;
    private TextView contentText;
    private TextView titleText;
    private EditText etContent;
    private ListView lvRoom;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private View mListHead;
    private TextView mTvHeadSlect;
    private TextView mTvHeadName;
    private TextView mTvContent;
    private TextView mTvContentTwo;
    private RadioButton mRbNoAliarm;
    private RadioGroup mRgp;
    private RadioButton mRbAliarm;
    private CheckBox mCbAlarm;
    private TextView mTvAlarmInfo;

    public MultipleDialog(Activity context) {
        this(context, NORMAL_TYPE);
        this.mContext = context;

    }

    public MultipleDialog(Activity context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;

        //设置为false，按返回键不能退出。默认为true
        setCancelable(true);
        //按空白处不能取消dialog
        setCanceledOnTouchOutside(false);
        //获取android系统服务
        WindowManager windowManager = context.getWindowManager();
        //获取屏幕信息
        Display display = windowManager.getDefaultDisplay();
        //创建画笔
        Point point = new Point();
        display.getSize(point);
        //设置全屏
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setBackgroundDrawableResource(R.drawable.dialog_bg);
        dialogWindow.setGravity(Gravity.CENTER);
        //WindowManager.LayoutParams.FLAG_DIM_BEHIND 让该window后所有的东西都成暗淡（dim）
        dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// dialog无title模式
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) (point.x * 0.7); // 宽度
        lp.height = (int) (point.y * 0.22); // 高度
        lp.alpha = 1.0f; // 自身透明度
        lp.dimAmount = 0.5f;// activity透明度
        dialogWindow.setAttributes(lp);

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_dialog);
        initView();

    }

    private void initView() {
        mProgressLayout = (LinearLayout) findViewById(R.id.progress_dialog);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mDoughnutProgress = (DoughnutProgress) findViewById(R.id.doughnutProgress);
        mSimpleLayout = (RelativeLayout) findViewById(R.id.simple_dialog);
        titleText = (TextView) findViewById(R.id.title_text);
        contentText = (TextView) findViewById(R.id.content_text);
        confirmBtn = (Button) findViewById(R.id.confirm_button);
        cancelBtn = (Button) findViewById(R.id.cancel_button);
        etContent = (EditText) findViewById(R.id.et_content);
        lvRoom = (ListView) findViewById(R.id.lv_room);
        whiteSpace = findViewById(R.id.white_space);
        mRgp = (RadioGroup) findViewById(R.id.rgp);
        mRbAliarm = (RadioButton) findViewById(R.id.rb_alarm);
        mRbNoAliarm = (RadioButton) findViewById(R.id.rb_no_alarm);
        mCbAlarm = (CheckBox) findViewById(R.id.cb_alarm);
        mProgressLayout.setVisibility(View.GONE);
        mSimpleLayout.setVisibility(View.GONE);
        mTvAlarmInfo = (TextView) findViewById(R.id.tv_alarm_info);

    }


    public void showProgressBar(int progressbar) {
        mTvContent.setVisibility(View.GONE);
        contentText.setVisibility(View.GONE);
        cancelBtn.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.GONE);
        mSimpleLayout.setVisibility(View.VISIBLE);
        confirmBtn.setVisibility(View.GONE);
        whiteSpace.setVisibility(View.GONE);
        etContent.setVisibility(View.GONE);
        lvRoom.setVisibility(View.GONE);
    }

    public void showProgressDialog(String content) {
        mProgressLayout.setVisibility(View.VISIBLE);
        mSimpleLayout.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(content)) {
            contentText.setText(content);
        }
    }

    //比较长的提示
    public void showProgressDialogtwo(String content) {
        setCancelable(true);
        show();
        mProgressLayout.setVisibility(View.VISIBLE);
        mTvContentTwo.setVisibility(View.GONE);
        mTvContent.setVisibility(View.VISIBLE);
        mSimpleLayout.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(content)) {
            contentText.setText(content);
        }
    }


    public void showSingleBtnDialog(int alertText, String btnText, View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            return;
        }
        mProgressLayout.setVisibility(View.GONE);
        mSimpleLayout.setVisibility(View.VISIBLE);
        confirmBtn.setVisibility(View.GONE);
        whiteSpace.setVisibility(View.GONE);
        titleText.setVisibility(View.INVISIBLE);
        etContent.setVisibility(View.GONE);
        lvRoom.setVisibility(View.GONE);
        cancelBtn.setVisibility(View.VISIBLE);
        contentText.setVisibility(View.VISIBLE);


        contentText.setText(mContext.getResources().getText(alertText));
        if (!TextUtils.isEmpty(btnText)) {
            cancelBtn.setText(btnText);
        } else {
            cancelBtn.setText("返 回");
        }
        cancelBtn.setOnClickListener(onClickListener);
    }

    //点击返回直接取消
    public void showSingleBtnDialogCancal(int alertText, String btnText, View.OnClickListener onClickListener) {
        setCancelable(true);
        show();
        mProgressLayout.setVisibility(View.GONE);
        mSimpleLayout.setVisibility(View.VISIBLE);
        confirmBtn.setVisibility(View.GONE);
        whiteSpace.setVisibility(View.GONE);
        etContent.setVisibility(View.GONE);
        lvRoom.setVisibility(View.GONE);
        String st = mContext.getResources().getString(alertText);

        contentText.setText(st);
        if (!TextUtils.isEmpty(btnText)) {
            cancelBtn.setText(btnText);
        } else {
            cancelBtn.setText("返 回");
        }
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public void showTwoBtnDialog(int alertText, String confirmText, String cancelText, View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
        if (confirmListener == null || cancelListener == null) {
            return;
        }
        titleText.setVisibility(View.INVISIBLE);
        mProgressLayout.setVisibility(View.GONE);
        etContent.setVisibility(View.GONE);
        lvRoom.setVisibility(View.GONE);
        mSimpleLayout.setVisibility(View.VISIBLE);
        confirmBtn.setVisibility(View.VISIBLE);
        whiteSpace.setVisibility(View.VISIBLE);
        contentText.setVisibility(View.VISIBLE);

        if (confirmText == null) {
            confirmBtn.setText(R.string.dialog_confirm_dp);
        } else {
            confirmBtn.setText(confirmText);
        }

        if (cancelText == null) {
            cancelBtn.setText(R.string.dialog_cancel_dp);
        } else {
            cancelBtn.setText(cancelText);
        }

        contentText.setText(mContext.getResources().getText(alertText));
        confirmBtn.setOnClickListener(confirmListener);
        cancelBtn.setOnClickListener(cancelListener);
    }

    public void showTwoBtnDialog(String alertText, String confirmText, String cancelText, View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
        if (confirmListener == null || cancelListener == null) {
            return;
        }
        titleText.setVisibility(View.INVISIBLE);
        mProgressLayout.setVisibility(View.GONE);
        etContent.setVisibility(View.GONE);
        lvRoom.setVisibility(View.GONE);
        mSimpleLayout.setVisibility(View.VISIBLE);
        confirmBtn.setVisibility(View.VISIBLE);
        whiteSpace.setVisibility(View.VISIBLE);
        contentText.setVisibility(View.VISIBLE);

        if (confirmText == null) {
            confirmBtn.setText(R.string.dialog_confirm_dp);
        } else {
            confirmBtn.setText(confirmText);
        }

        if (cancelText == null) {
            cancelBtn.setText(R.string.dialog_cancel_dp);
        } else {
            cancelBtn.setText(cancelText);
        }

        contentText.setText(alertText);
        confirmBtn.setOnClickListener(confirmListener);
        cancelBtn.setOnClickListener(cancelListener);
    }

    public void showTitleTwoBtnDialog(String tvTitle, String alertText, String confirmText, String cancelText, View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
        if (confirmListener == null || cancelListener == null) {
            return;
        }
        mProgressLayout.setVisibility(View.GONE);
        mSimpleLayout.setVisibility(View.VISIBLE);
        titleText.setVisibility(View.VISIBLE);
        contentText.setVisibility(View.VISIBLE);
        etContent.setVisibility(View.GONE);
        lvRoom.setVisibility(View.GONE);

        confirmBtn.setVisibility(View.VISIBLE);
        whiteSpace.setVisibility(View.VISIBLE);
        if (confirmText == null) {
            confirmBtn.setText(R.string.dialog_confirm_dp);
        } else {
            confirmBtn.setText(confirmText);
        }
        if (cancelText == null) {
            cancelBtn.setText(R.string.dialog_cancel_dp);
        } else {
            cancelBtn.setText(cancelText);
        }
        titleText.setText(tvTitle);
        contentText.setText(alertText);
        confirmBtn.setOnClickListener(confirmListener);
        cancelBtn.setOnClickListener(cancelListener);


    }


    public void showEditTwoBtnDialog(String tvTitle, String hintText, String confirmText, String cancelText, View.OnClickListener confirmListener, View.OnClickListener cancelListener) {
        if (confirmListener == null || cancelListener == null) {
            return;
        }
        mProgressLayout.setVisibility(View.GONE);
        mSimpleLayout.setVisibility(View.VISIBLE);
        contentText.setVisibility(View.GONE);
        etContent.setVisibility(View.VISIBLE);
        titleText.setVisibility(View.VISIBLE);
        confirmBtn.setVisibility(View.VISIBLE);
        whiteSpace.setVisibility(View.VISIBLE);
        lvRoom.setVisibility(View.GONE);
        etContent.setText("");
        if (confirmText == null) {
            confirmBtn.setText(R.string.dialog_confirm_dp);
        } else {
            confirmBtn.setText(confirmText);
        }
        if (cancelText == null) {
            cancelBtn.setText(R.string.dialog_cancel_dp);
        } else {
            cancelBtn.setText(cancelText);
        }


        if (hintText == null) {
            etContent.setHint(R.string.dialog_ethint);
        } else {
            etContent.setHint(hintText);
        }
//        etContent.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                L.d("输出长度1",s.length()+"");
//                if (s.length()>10){
//                    T.showShort(mContext,"文件名称不能超过10个字");
//
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                L.d("输出长度2",s.length()+"");
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                L.d("输出长度3",s.length()+"");
//
//
//
//
//            }
//        });
        titleText.setText(tvTitle);

        confirmBtn.setOnClickListener(confirmListener);
        cancelBtn.setOnClickListener(cancelListener);
    }


    public String getEtString() {


        return etContent.getText().toString();
    }


}
