package com.qinshou.administrator.carsofferassistant.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.adapter.MyDetailImageAdapter;
import com.qinshou.administrator.carsofferassistant.utils.XMLParserIndependenceUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by zyj on 2016/7/8.
 */

public class DetailCarImageUrlAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private List<Map<String,Object>> dataSource;
    private int position;
    private MyDetailImageAdapter adapter;
    private Context context;


    public DetailCarImageUrlAsyncTask(List<Map<String,Object>> dataSource,int position,MyDetailImageAdapter adapter,Context context) {
        this.dataSource = dataSource;
        this.position = position;
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();

                String imageName = params[0].substring(params[0].lastIndexOf("/") + 1);
                File downloadAfterFile = new File(context.getFilesDir(),imageName);

                if (saveImageIntoSDStorge(is,downloadAfterFile)){//如果图片保存到SD卡上成功了。
                    Bitmap bitmap = collectImageSample(downloadAfterFile.getAbsolutePath());//进行二次采样。
                    downloadAfterFile.delete();//删除文件中的图片
                    return bitmap;
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null){
            dataSource.get(position).put("iv_default_detail_image_id", bitmap);
            adapter.notifyDataSetChanged();

        }
    }

    /**
     * 把下载的图片保存到SD卡上
     * @param is
     * @param downloadAfterFile
     */
    public boolean saveImageIntoSDStorge(InputStream is,File downloadAfterFile){
        //存储图片并进行二次采样。
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(downloadAfterFile);
            byte[] buffer = new byte[1024 * 4];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {// 每循环一次
                fos.write(buffer, 0, len);
                fos.flush();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (downloadAfterFile.exists() && downloadAfterFile.length() > 0){
            return true;
        }
        return false;

    }

    private Bitmap collectImageSample(String fileNmae){
        // 1、求得窗口的宽和高
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        // 获得真实图片的宽和高（摘要信息）
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileNmae,opts);// 采样

        int realHeight = opts.outHeight;
        int realWidth = opts.outWidth;
        // 求缩放比
        int scale = 1;
        int widthScale = realWidth / width;
        int heightScale = realHeight / height;

        if (widthScale > heightScale && widthScale > scale) {
            scale = widthScale;
        }

        if (heightScale > widthScale && heightScale > scale) {
            scale = heightScale;
        }
        opts.inSampleSize = scale;
        opts.inJustDecodeBounds = false;// 将缩小后的图片加载到内存
        Log.i("TAG", "图片真实的宽：" + realWidth + "，高" + realHeight + "；界面上图片控件的宽："
                + width + "，高：" + height + "，采样后的缩放比：" + scale);

        Bitmap bm = BitmapFactory.decodeFile(fileNmae,opts);
        return bm;
    }

}
