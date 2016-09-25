package com.zhi.www.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhi.www.constant.Contant;
import com.zhi.www.utils.HttpHelper;

public class MainActivity extends Activity implements View.OnClickListener {
    HttpHelper httpHelper = HttpHelper.getInstance();
    private Button mBtnShow;
    private Button mBtnDownload;
    private ImageView mIvImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();
    }

    private void initEvents() {
        mBtnShow.setOnClickListener(this);
        mBtnDownload.setOnClickListener(this);
    }

    private void initViews() {
        mBtnShow = (Button) findViewById(R.id.btn_show);
        mBtnDownload = (Button) findViewById(R.id.btn_download);
        mIvImage = (ImageView) findViewById(R.id.iv_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
                new DownloadImage().execute();
                break;
            case R.id.btn_show:
                new ShowImage().execute();
                break;
        }
    }

    class DownloadImage extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            httpHelper.downloadImage(Contant.DOWNLOAD_IMG_URL);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
        }
    }

    class ShowImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = httpHelper.getBitmap(Contant.DOWNLOAD_IMG_URL);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            mIvImage.setImageBitmap(s);
        }
    }
}