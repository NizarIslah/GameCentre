package fall2018.csc2017.GameCentre;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jerry on 3/21/2018.
 */

public class GlobalApplication extends Application {

    private static Context appContext;

    /**
     * If you has other classes that need context object to initialize when application is created,
     * you can use the appContext here to process.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    /**
     * Gets the app context.
     *
     * @return appContext.
     */
    public static Context getAppContext() {
        return appContext;
    }
}