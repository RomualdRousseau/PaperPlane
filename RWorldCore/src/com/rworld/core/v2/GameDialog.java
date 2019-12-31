package com.rworld.core.v2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class GameDialog {

	public GameDialog(GameActivity activity) {
		mActivity = activity;
	}
	
	public void show(int dialogId) {
		mResult = null;
		Message msg = mDialogHandler.obtainMessage();
		Bundle b = new Bundle();
		b.putInt("DialogId", dialogId);
        msg.setData(b);
        mDialogHandler.sendMessage(msg);
	}
	
	public void setResult(Object result) {
		synchronized(mResultLock) {
			mResult = result;
			mResultLock.notify();
		}
	}
	
	public Object getResult() {
		synchronized(mResultLock) {
			while(mResultLock == null) {
				try {
					mResultLock.wait();
				} catch (InterruptedException e) {
					mResultLock = null;
				}
			}
		}
		return mResult;
	}

	protected final Handler mDialogHandler = new Handler() {
        @Override
		public void handleMessage(Message msg) {
        	mActivity.showDialog(msg.getData().getInt("DialogId"));
        }
    };
	
	protected GameActivity mActivity = null;
	protected Object mResult = null;
	protected Object mResultLock = new Object();
}
