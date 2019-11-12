package xcrash.sample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import xcrash.XCrash;

public class MyService extends Service {
    private final String TAG = "DoubleCatch";
    private int constructCnt = 0;
    private int createCnt = 0;
    private int startCommandCnt = 0;
    private int bindCnt = 0;

    public MyService() {
        Log.d(TAG, "MyService: constructCnt is " + constructCnt++);
        Log.d(TAG, "MyService: thread is " + Thread.currentThread());
        Log.d(TAG, "MyService: object is " + this.hashCode());
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "MyService: createCnt is " + createCnt++);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MyService: startCommandCnt is " + startCommandCnt++);
        Log.d(TAG, "MyService: Process is " + android.os.Process.myPid());

        String type = intent.getStringExtra("type");
        if (type != null) {
            if (type.equals("native")) {
                XCrash.testNativeCrash(false);
            } else if (type.equals("java")) {
                XCrash.testJavaCrash(false);
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "MyService: bindCnt is " + bindCnt++);
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        Log.d(TAG, "MyService: invoke finalize");
        super.finalize();
    }
}
