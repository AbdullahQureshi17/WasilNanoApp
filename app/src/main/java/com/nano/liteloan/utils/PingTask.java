package com.nano.liteloan.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PingTask extends AsyncTask<String, Void, Void> {

    PipedOutputStream mPOut;
    PipedInputStream mPIn;
    LineNumberReader mReader;
    Process mProcess;

    @Override
    protected void onPreExecute() {
        mPOut = new PipedOutputStream();
        try {
            mPIn = new PipedInputStream(mPOut);
            mReader = new LineNumberReader(new InputStreamReader(mPIn));
        } catch (IOException e) {
            Log.e("ahmad", e.getMessage());
            cancel(true);
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        try {
            // Is a line ready to read from the "ping" command?
            while (mReader.ready()) {
                // This just displays the output, you should typically parse it I guess.
                String a = mReader.readLine().toString();
                String a1 = mReader.readLine().toString();
                //  mText.setText(mReader.readLine());
            }
        } catch (IOException t) {
            Log.e("ahmad", t.getMessage());
        }
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            mProcess = new ProcessBuilder()
                    .command("/system/bin/ping", strings[0])
                    .redirectErrorStream(true)
                    .start();

            try {
                InputStream in = mProcess.getInputStream();
                OutputStream out = mProcess.getOutputStream();
                byte[] buffer = new byte[1024];
                int count;

                // in -> buffer -> mPOut -> mReader -> 1 line of ping information to parse
                while ((count = in.read(buffer)) != -1) {
                    mPOut.write(buffer, 0, count);
                    publishProgress();
                }
                out.close();
                in.close();
                mPOut.close();
                mPIn.close();
            } finally {
                mProcess.destroy();
                mProcess = null;
            }
        } catch (IOException e) {

            Log.e("ahmad", e.getMessage());
        }
        return null;
    }
}
