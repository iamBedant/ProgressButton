package io.iamBedant.ProgressButton;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by @iamBedant on 13/06/17.
 */

public class LoadingButton extends FrameLayout {

  private Button mTextButton;
  private ImageButton mImageButton;
  private TextView mTextView;
  Context mContext;

  //Default Values
  String mButtonText, mSuccessText, mFailText;

  int mButtonBackgroundColor, mLoadingBackgroundColor, mResultSuccessBackground,
      mResultFailBackground, mSuccessTextColor, mFailTextColor, mLoadingResourceId,
      mButtonTextColor;

  public interface OnLoadingButtonClickListener {
    void onLoadingButtonClickListener();
  }

  private OnLoadingButtonClickListener mOnLoadingButtonClickListener;

  public void setOnLoadingClickListener(OnLoadingButtonClickListener onLoadingClickListener) {
    mOnLoadingButtonClickListener = onLoadingClickListener;
  }

  public LoadingButton(Context context) {
    super(context);
    initializeViews(context);
  }

  public LoadingButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    initializeAttributes(context, attrs);
    initializeViews(context);
  }

  public LoadingButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initializeAttributes(context, attrs);
    initializeViews(context);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public LoadingButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    initializeAttributes(context, attrs);
    initializeViews(context);
  }

  private void initializeAttributes(Context context, AttributeSet attrs) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0);

    //With Default Value
    mButtonBackgroundColor = a.getColor(R.styleable.LoadingButton_button_background,
        getResources().getColor(android.R.color.holo_blue_light));
    mLoadingBackgroundColor = a.getColor(R.styleable.LoadingButton_loading_background,
        getResources().getColor(android.R.color.holo_blue_light));
    mResultSuccessBackground = a.getColor(R.styleable.LoadingButton_result_success_background,
        getResources().getColor(android.R.color.white));
    mResultFailBackground = a.getColor(R.styleable.LoadingButton_result_fail_background,
        getResources().getColor(android.R.color.white));
    mButtonTextColor = a.getColor(R.styleable.LoadingButton_button_text_color,
        getResources().getColor(android.R.color.white));
    mSuccessTextColor = a.getColor(R.styleable.LoadingButton_success_text_color,
        getResources().getColor(android.R.color.holo_green_dark));
    mFailTextColor = a.getColor(R.styleable.LoadingButton_fail_text_color,
        getResources().getColor(android.R.color.holo_red_dark));

    mLoadingResourceId =
        a.getInt(R.styleable.LoadingButton_loading_drawable, R.drawable.avd_endless_pin_jump);

    //Without Default Value
    mSuccessText = a.getString(R.styleable.LoadingButton_success_text);
    mFailText = a.getString(R.styleable.LoadingButton_fail_text);
    mButtonText = a.getString(R.styleable.LoadingButton_button_text);

    a.recycle();
  }

  private void initializeViews(Context context) {
    mContext = context;
    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.loading_button, this);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();

    mTextButton = (Button) this.findViewById(R.id.btn_txt);
    setButtonBackgroundAll(mTextButton, mButtonBackgroundColor);
    mTextButton.setTextColor(mButtonTextColor);

    if (mButtonText != null && !mButtonText.isEmpty()) {
      mTextButton.setText(mButtonText);
    } else {
      mTextButton.setText(R.string.submit_default);
    }

    mTextButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (mOnLoadingButtonClickListener != null) {
          mOnLoadingButtonClickListener.onLoadingButtonClickListener();
        }
      }
    });

    mImageButton = (ImageButton) this.findViewById(R.id.btn_img);

    setImageButtonBackgroundAll(mImageButton, mLoadingBackgroundColor);

    mImageButton.setVisibility(GONE);

    mTextView = (TextView) this.findViewById(R.id.txt_success);
    mTextView.setVisibility(GONE);
  }

  private void setImageButtonBackgroundAll(ImageButton mTextButton, int mButtonBackgroundColor) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      setImageButtonBackground(mTextButton, mButtonBackgroundColor);
    } else {
      setImageButtonBackgroundOld(mTextButton, mButtonBackgroundColor);
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void setImageButtonBackground(ImageButton mTextButton, int mButtonBackgroundColor) {
    mTextButton.setBackgroundTintList(ColorStateList.valueOf(mButtonBackgroundColor));
  }

  public void setImageButtonBackgroundOld(ImageButton mTextButton, int mButtonBackgroundColor) {
    mTextButton.getBackground().setColorFilter(mButtonBackgroundColor, PorterDuff.Mode.MULTIPLY );

  }

  private void setButtonBackgroundAll(Button mTextButton, int mButtonBackgroundColor) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      setButtonBackground(mTextButton, mButtonBackgroundColor);
    } else {
      setButtonBackgroundOld(mTextButton, mButtonBackgroundColor);
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private void setButtonBackground(Button mTextButton, int mButtonBackgroundColor) {
   mTextButton.setBackgroundTintList(ColorStateList.valueOf(mButtonBackgroundColor));
  }

  public void setButtonBackgroundOld(Button mTextButton, int mButtonBackgroundColor) {
    mTextButton.getBackground().setColorFilter(mButtonBackgroundColor, PorterDuff.Mode.LIGHTEN);
  }

  /*
      For Default Loading Animation
   */
  public void startLoading() {
    AnimatedVectorDrawableCompat animatedVector = AnimatedVectorDrawableCompat.create(mContext, mLoadingResourceId);
    mTextButton.setVisibility(GONE);
    mImageButton.setImageDrawable(animatedVector);
    animatedVector.start();
    mImageButton.setVisibility(VISIBLE);
    mTextView.setVisibility(GONE);
  }

  /*
      For Custom Loading Animation
   */
  public void startLoading(int id) {
    AnimatedVectorDrawableCompat animatedVector = AnimatedVectorDrawableCompat.create(mContext, id);
    mTextButton.setVisibility(GONE);
    mImageButton.setImageDrawable(animatedVector);
    animatedVector.start();
    mImageButton.setVisibility(VISIBLE);
    mTextView.setVisibility(GONE);
  }

  public void success() {
    mTextButton.setVisibility(GONE);
    mImageButton.setVisibility(GONE);

    if (mSuccessText != null && !mSuccessText.isEmpty()) {
      mTextView.setText(mSuccessText);
    } else {
      mTextView.setText(R.string.txt_success);
    }
    setResult(mResultSuccessBackground, mSuccessTextColor);
    mTextView.setVisibility(VISIBLE);
  }

  private void setResult(int backgroundColor, int textColor) {
    mTextView.setBackgroundColor(backgroundColor);
    mTextView.setTextColor(textColor);
  }

  /*
      For Custom Success text
   */
  public void success(String text) {
    mTextButton.setVisibility(GONE);
    mImageButton.setVisibility(GONE);
    mTextView.setText(text);
    setResult(mResultSuccessBackground, mSuccessTextColor);
    mTextView.setVisibility(VISIBLE);
  }

  public void fail() {
    mTextButton.setVisibility(GONE);
    mImageButton.setVisibility(GONE);

    if (mFailText != null && !mFailText.isEmpty()) {
      mTextView.setText(mFailText);
    } else {
      mTextView.setText(R.string.txt_fail);
    }
    setResult(mResultFailBackground, mFailTextColor);
    mTextView.setVisibility(VISIBLE);
  }

  /*
      For Custom Fail  Text
   */
  public void fail(String text) {
    mTextButton.setVisibility(GONE);
    mImageButton.setVisibility(GONE);
    mTextView.setText(text);
    setResult(mResultFailBackground, mFailTextColor);
    mTextView.setVisibility(VISIBLE);
  }

  public void backToDefault() {
    mTextButton.setVisibility(VISIBLE);
    mImageButton.setVisibility(GONE);
    mTextView.setVisibility(GONE);
  }

  public void disableClick() {
    mTextButton.setVisibility(VISIBLE);
    mTextButton.setClickable(false);
    mImageButton.setVisibility(GONE);
    mTextView.setVisibility(GONE);
  }
}
