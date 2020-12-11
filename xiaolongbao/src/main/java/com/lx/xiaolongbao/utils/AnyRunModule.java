package com.lx.xiaolongbao.utils;

import android.content.Context;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.task.DownloadTask;
import com.lx.xiaolongbao.R;

/**
 * author  : xiaolongbao
 * time    : 2020/12/8  下午1:49$
 * desc    : 下载
 */
public class AnyRunModule {
    String TAG = "AnyRunnModule";
    private Context mContext;
    private String mUrl;
    private String mPath;
    private long mTaskId = -1;

    public AnyRunModule(Context context, String path) {
        Aria.download(this).register();
        mContext = context;
        mPath = context.getExternalFilesDir(context.getString(R.string.app_name)) + "/" + path;
    }

    @Download.onWait
    void onWait(DownloadTask task) {
        if (downloadSampleListener != null) {
            downloadSampleListener.onWait(task);
        }
    }

    @Download.onPre
    protected void onPre(DownloadTask task) {
//        Log.d(TAG, "onPre");
        if (downloadSampleListener != null) {
            downloadSampleListener.onPre(task);
        }
    }

    @Download.onTaskStart
    void taskStart(DownloadTask task) {
//        Log.d(TAG, "onStart");
        if (downloadSampleListener != null) {
            downloadSampleListener.taskStart(task);
        }
    }

    @Download.onTaskRunning
    protected void running(DownloadTask task) {
        if (downloadSampleListener != null) {
            downloadSampleListener.running(task);
        }

    }

    @Download.onTaskResume
    void taskResume(DownloadTask task) {
        if (downloadSampleListener != null) {
            downloadSampleListener.taskResume(task);
        }
//        Log.d(TAG, "resume");
    }

    @Download.onTaskStop
    void taskStop(DownloadTask task) {
//        Log.d(TAG, "stop");
        if (downloadSampleListener != null) {
            downloadSampleListener.taskStop(task);
        }
    }

    @Download.onTaskCancel
    void taskCancel(DownloadTask task) {
//        Log.d(TAG, "cancel");
        if (downloadSampleListener != null) {
            downloadSampleListener.taskCancel(task);
        }
    }

    @Download.onTaskFail
    void taskFail(DownloadTask task) {
//        Log.d(TAG, "fail");
        if (downloadSampleListener != null) {
            downloadSampleListener.taskFail(task);
        }
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        if (downloadSampleListener != null) {
            downloadSampleListener.taskComplete(task);
        }
//        L.d(TAG, "path ==> " + task.getDownloadEntity().getDownloadPath());
//        L.d(TAG, "md5Code ==> " + CommonUtil.getFileMD5(new File(task.getDownloadPath())));
    }

    /**
     * 开始下载
     * @param url 地址
     */
    public void downloadStart(String url) {
        mUrl = url;
        mTaskId = Aria.download(this)
                .load(url)
                .setFilePath(mPath)
                .resetState()
                .create();
    }

    public void stop() {
        Aria.download(this).load(mTaskId).stop();
    }

    public void cancel() {
        Aria.download(this).load(mTaskId).cancel();
    }

    public void unRegister() {
        Aria.download(this).unRegister();
    }

    public void reStart() {
        Aria.download(this).load(mTaskId).resume(true);
    }

    private DownloadSampleListener downloadSampleListener;

    public void setDownloadSampleListener(DownloadSampleListener downloadSampleListener) {
        this.downloadSampleListener = downloadSampleListener;
    }


    public interface DownloadSampleListener {
        void taskStop(DownloadTask task);

        void running(DownloadTask task);

        void taskComplete(DownloadTask task);

        void taskCancel(DownloadTask task);

        void taskFail(DownloadTask task);

        void taskResume(DownloadTask task);

        void onWait(DownloadTask task);

        void onPre(DownloadTask task);

        void taskStart(DownloadTask task);
    }
}
