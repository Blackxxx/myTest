
package Log;

import android.app.Application;

public class MyApplication extends Application {
    public static MyApplication instance = null;
    public static boolean DEBUG = true;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
