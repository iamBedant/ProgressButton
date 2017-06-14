package sample.io.sampleprogressbutton;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import io.iamBedant.ProgressButton.LoadingButton;
import io.iamBedant.ProgressButton.LoadingButton.OnLoadingButtonClickListener;

public class MainActivity extends AppCompatActivity implements OnLoadingButtonClickListener{


  LoadingButton mButton;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mButton = findViewById(R.id.btn);
    mButton.setOnLoadingClickListener(this);
  }

  @Override public void onLoadingButtonClickListener() {
    mButton.startLoading();
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      public void run() {
        mButton.success();
      }
    }, 5000);
  }
}
