/*
 * 文 件 名:  LogUtil.java
 * 版    权:  Shenzhen spt-tek. Copyright 2015-2025,  All rights reserved
 * 描    述:  <描述>
 * 作    者:  Heaven
 * 创建时间:  2015年8月12日
 */

package Log;

import android.app.ActivityManager;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <功能描述>
 * 
 * @author Heaven
 */
public class LogUtil {
    private static final String TAG = "LogUtilDemo";
    // private static final boolean LOG_ON_OFF = MyApplication.DEBUG;
    public static final String LOG_SAVE_FILE_PTAH = Environment
            .getExternalStorageDirectory() + "/log";
    public static final String LOG_SAVE_FILE_FLODER = LOG_SAVE_FILE_PTAH
            + "/app_log";
    private static Handler mHandler = null;
    private static HandlerThread mHanlderThread = null;

    public static boolean isLogOpen() {
        if (ActivityManager.isUserAMonkey()) {
            return false;
        }
        if (MyApplication.getInstance() == null) {
            return false;
        }
        return Settings.System.getInt(MyApplication.getInstance()
                .getContentResolver(), MyApplication.getInstance()
                .getPackageName(), -1) == 17;
    }

    public static void i(String tag, String msg) {
        if (isLogOpen()) {
            Log.i(TAG, "[" + tag + "] " + msg);
            writeToFile(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isLogOpen()) {
            Log.v(TAG, "[" + tag + "] " + msg);
            writeToFile(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isLogOpen()) {
            Log.e(TAG, "[" + tag + "] " + msg);
            writeToFile(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isLogOpen()) {
            Log.w(TAG, "[" + tag + "] " + msg);
            writeToFile(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isLogOpen()) {
            Log.d(TAG, "[" + tag + "] " + msg);
            writeToFile(tag, msg);
        }
    }

    public static void dd(String tag, String msg) {
        if (isLogOpen()) {
            Log.d(tag, msg);
            writeToFile(tag, msg);
        }
    }

    public static void e(Throwable ex) {
        if (isLogOpen()) {
            StringWriter writer = new StringWriter();
            PrintWriter mWriter = new PrintWriter(writer);
            ex.printStackTrace(mWriter);
            String stacktrace = writer.toString();
            mWriter.close();
            stacktrace = "[" + getTimeString() + "(" + Process.myPid()
                    + ")]*[ " + TAG + "]*[" + stacktrace + "]";
            RandomAccessFile rFile = getLogFile();
            // 将日志写入文件
            if (rFile != null) {
                try {
                    rFile.seek(rFile.length());
                    byte[] bytemsg = (stacktrace + "\n").getBytes("utf-8");
                    rFile.write(bytemsg);
                    rFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void writeToFile(String tag, String msg) {
        if (mHanlderThread == null) {
            mHanlderThread = new HandlerThread(TAG);
            mHanlderThread.start();
        }
        if (mHandler == null) {
            mHandler = new Handler(mHanlderThread.getLooper());
        }
        mHandler.post(new WriteRunnable(tag, msg));
    }

    private static class WriteRunnable implements Runnable {
        private String msg;
        private String tag;

        public WriteRunnable(String tag, String msg) {
            this.msg = msg;
            this.tag = tag;
        }

        @Override
        public void run() {
            // 获取文件路径
            msg = "[" + getTimeString() + "(" + Process.myPid() + ")]*[ " + tag
                    + "]*[" + msg + "]";
            RandomAccessFile rFile = getLogFile();
            // 将日志写入文件
            if (rFile != null) {
                try {
                    rFile.seek(rFile.length());
                    byte[] bytemsg = (msg + "\n").getBytes("utf-8");
                    rFile.write(bytemsg);
                    rFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getDateString() {
        // 生成日志时间
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINESE);// 24小时制
        String dateString = dateFormat.format(date).toString();
        return dateString;
    }

    private static String getTimeString() {
        // 生成日志时间
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.CHINESE);// 24小时制
        String dateString = dateFormat.format(date).toString();
        return dateString;
    }

    private static RandomAccessFile getLogFile() {
        // 判断是否有sd卡
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        RandomAccessFile randomAccessFile = null;
        if (sdCardExist) {
            // 检查根目录
            File rootDir = new File(LOG_SAVE_FILE_FLODER);
            if (!rootDir.exists()) {
                rootDir.mkdirs();
            }

            String filename = rootDir.getPath() + File.separator
                    + getDateString() + ".txt";
            File logfile = new File(filename);
            // 新建RandomAccessFile 文件
            try {
                randomAccessFile = new RandomAccessFile(logfile, "rw");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return randomAccessFile;
    }
}
