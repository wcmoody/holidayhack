package com.northpolewonderland.santagram;

import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.support.v7.p021a.C0562e;
import android.util.Log;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends C0562e {
    private static String TAG;
    private static int splashInterval;
    UncaughtExceptionHandler paramThrowable;

    /* renamed from: com.northpolewonderland.santagram.SplashScreen.1 */
    class C09801 implements UncaughtExceptionHandler {
        final /* synthetic */ SplashScreen f2612a;

        C09801(SplashScreen splashScreen) {
            this.f2612a = splashScreen;
        }

        public void uncaughtException(Thread thread, Throwable th) {
            C0987b.m4775a(this.f2612a.getApplicationContext(), th);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    /* renamed from: com.northpolewonderland.santagram.SplashScreen.2 */
    class C09812 implements Runnable {
        final /* synthetic */ SplashScreen f2613a;

        C09812(SplashScreen splashScreen) {
            this.f2613a = splashScreen;
        }

        private void m4768a() {
        }

        public void run() {
            this.f2613a.startActivity(new Intent(this.f2613a, Home.class));
            m4768a();
        }
    }

    /* renamed from: com.northpolewonderland.santagram.SplashScreen.3 */
    class C09823 implements Runnable {
        final /* synthetic */ JSONObject f2614a;
        final /* synthetic */ SplashScreen f2615b;

        C09823(SplashScreen splashScreen, JSONObject jSONObject) {
            this.f2615b = splashScreen;
            this.f2614a = jSONObject;
        }

        public void run() {
            C0987b.m4776a(this.f2615b.getString(2131165205), this.f2614a);
        }
    }

    /* renamed from: com.northpolewonderland.santagram.SplashScreen.4 */
    class C09834 implements Runnable {
        final /* synthetic */ JSONObject f2616a;
        final /* synthetic */ SplashScreen f2617b;

        C09834(SplashScreen splashScreen, JSONObject jSONObject) {
            this.f2617b = splashScreen;
            this.f2616a = jSONObject;
        }

        public void run() {
            C0987b.m4776a(this.f2617b.getString(2131165216), this.f2616a);
        }
    }

    static {
        splashInterval = 2000;
        TAG = "SantaGram";
    }

    private float cpuUsage() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/proc/stat", "r");
            String[] split = randomAccessFile.readLine().split(" +");
            long parseLong = Long.parseLong(split[4]);
            long parseLong2 = ((((Long.parseLong(split[2]) + Long.parseLong(split[3])) + Long.parseLong(split[5])) + Long.parseLong(split[6])) + Long.parseLong(split[7])) + Long.parseLong(split[8]);
            try {
                Thread.sleep(360);
            } catch (Exception e) {
            }
            randomAccessFile.seek(0);
            String readLine = randomAccessFile.readLine();
            randomAccessFile.close();
            String[] split2 = readLine.split(" +");
            long parseLong3 = Long.parseLong(split2[4]);
            long parseLong4 = Long.parseLong(split2[8]) + ((((Long.parseLong(split2[2]) + Long.parseLong(split2[3])) + Long.parseLong(split2[5])) + Long.parseLong(split2[6])) + Long.parseLong(split2[7]));
            return ((float) (parseLong4 - parseLong2)) / ((float) ((parseLong4 + parseLong3) - (parseLong + parseLong2)));
        } catch (IOException e2) {
            e2.printStackTrace();
            return 0.0f;
        }
    }

    private void postDeviceAnalyticsData() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("username", "guest");
            jSONObject.put("password", "busyreindeer78");
            jSONObject.put("type", "launch");
            jSONObject.put("model", Build.MODEL);
            jSONObject.put("sdkint", VERSION.SDK_INT);
            jSONObject.put("device", Build.DEVICE);
            jSONObject.put("product", Build.PRODUCT);
            jSONObject.put("manuf", Build.MANUFACTURER);
            jSONObject.put("lversion", System.getProperty("os.version"));
            jSONObject.put("screenDensityW", getWindow().getWindowManager().getDefaultDisplay().getWidth());
            jSONObject.put("screenDensityH", getWindow().getWindowManager().getDefaultDisplay().getHeight());
            jSONObject.put("locale", Locale.getDefault().getISO3Country());
            jSONObject.put("appVersion", getString(2131165207));
            jSONObject.put("udid", Secure.getString(getContentResolver(), "android_id"));
            new Thread(new C09823(this, jSONObject)).start();
        } catch (JSONException e) {
            Log.e(TAG, "Error in postDeviceAnalyticsData: " + e.getMessage());
        }
    }

    private void postExceptionData(Throwable th) {
        JSONObject jSONObject = new JSONObject();
        Log.i(getString(2131165204), "Exception: sending exception data to " + getString(2131165216));
        try {
            jSONObject.put("operation", "WriteCrashDump");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("message", th.getMessage());
            jSONObject2.put("lmessage", th.getLocalizedMessage());
            jSONObject2.put("strace", Log.getStackTraceString(th));
            jSONObject2.put("model", Build.MODEL);
            jSONObject2.put("sdkint", String.valueOf(VERSION.SDK_INT));
            jSONObject2.put("device", Build.DEVICE);
            jSONObject2.put("product", Build.PRODUCT);
            jSONObject2.put("lversion", System.getProperty("os.version"));
            jSONObject2.put("vmheapsz", String.valueOf(Runtime.getRuntime().totalMemory()));
            jSONObject2.put("vmallocmem", String.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
            jSONObject2.put("vmheapszlimit", String.valueOf(Runtime.getRuntime().maxMemory()));
            jSONObject2.put("natallocmem", String.valueOf(Debug.getNativeHeapAllocatedSize()));
            jSONObject2.put("cpuusage", String.valueOf(cpuUsage()));
            jSONObject2.put("totalstor", String.valueOf(totalStorage()));
            jSONObject2.put("freestor", String.valueOf(freeStorage()));
            jSONObject2.put("busystor", String.valueOf(busyStorage()));
            jSONObject2.put("udid", Secure.getString(getContentResolver(), "android_id"));
            jSONObject.put("data", jSONObject2);
            new Thread(new C09834(this, jSONObject)).start();
        } catch (JSONException e) {
            Log.e(TAG, "Error posting message to " + getString(2131165216) + " -- " + e.getMessage());
        }
    }

    public long busyStorage() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        return (statFs.getBlockCountLong() * statFs.getBlockSizeLong()) - (statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong());
    }

    public long freeStorage() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new C09801(this));
        C0987b.m4774a(getApplicationContext(), getClass().getSimpleName());
        postDeviceAnalyticsData();
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(2130968640);
        new Handler().postDelayed(new C09812(this), (long) splashInterval);
    }

    public long totalStorage() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        return statFs.getBlockSizeLong() * statFs.getBlockCountLong();
    }
}
