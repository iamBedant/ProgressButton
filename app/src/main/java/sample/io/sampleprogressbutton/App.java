package sample.io.sampleprogressbutton;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by @iamBedant on 13/06/17.
 */

public class App extends Application {

  @Override public void onCreate() {
    super.onCreate();
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
  }
}
