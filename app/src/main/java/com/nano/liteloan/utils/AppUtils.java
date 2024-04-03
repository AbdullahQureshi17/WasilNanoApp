package com.nano.liteloan.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.text.format.Formatter;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nano.liteloan.application.MainApplication;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Muhammad Ahmad on 07/06/2020.
 */
public class AppUtils {

    private static final double IMAGE_MAX_SIZE = 1024;
    public static boolean isPinSet = true;
    private static String localIP = "10.84.12.98";
    public static String subMask = "255.255.0.0";


    public static void logOutApplication() {

        PreferenceUtils.getInstance().clearPreferences();

        PreferenceUtils.getInstance().clearPreferences();
        Intent intent = new Intent(MainApplication.getAppContext(),
                Log.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        MainApplication.getAppContext()
                .startActivity(intent);
    }

    public static ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    public static String executeCmd(String cmd, boolean sudo) {
        try {

            Process p;
            if (!sudo)
                p = Runtime.getRuntime().exec(cmd);
            else {
                p = Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
            }
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String s;
            String res = "";
            while ((s = stdInput.readLine()) != null) {
                res += s + "\n";
            }
            p.destroy();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String getIp(Context context) {

        WifiManager wm = (WifiManager) context.
                getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }

    @SuppressLint("WifiManagerLeak")
    public static String getIPAddress() {


        ConnectivityManager connectivityManager = (ConnectivityManager)
                MainApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            android.net.Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);

            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                WifiManager wifiManager = (WifiManager) MainApplication.getAppContext()
                        .getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                try {
                    return InetAddress.getByAddress(BigInteger.valueOf(ipAddress).toByteArray()).getHostAddress();
                } catch (Exception e) {
                    Log.e("ahmad", e.getMessage());
                }
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                try {
                    Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                    while (enumeration.hasMoreElements()) {
                        NetworkInterface networkInterface = enumeration.nextElement();
                        Enumeration<InetAddress> enumerationInetAddress = networkInterface.getInetAddresses();
                        while (enumerationInetAddress.hasMoreElements()) {
                            InetAddress inetAddress = enumerationInetAddress.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    Log.e("ahmad", e.getMessage());
                }
            }
        }

        return null;
    }

    public static String baseURL() {

        // return "http://10.84.13.5/loan/api/public/";

        if (isLocalIP()) {
            // Local
            return "http://10.84.12.98/easy_akhuwat_v2/api/public/";

            // return "http://116.58.62.235/easy_akhuwat_v2/api/public/";
        } else {
            // Local
            //return "http://10.84.12.98/easy_akhuwat_v2/api/public/";

            return "http://116.58.62.235/easy_akhuwat_v2/api/public/";
        }
    }

    public static Bitmap getBitmapFromView(View view, int height, int width) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Drawable bgDrawable = view.getBackground();
            if (bgDrawable != null)
                bgDrawable.draw(canvas);
            else
                canvas.drawColor(Color.WHITE);
            view.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            Log.e("ahmad", e.getMessage());
            return null;
        }

    }

    public static void saveBitmap(Bitmap bitmap, Context context) {

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";
        File imagePath = new File(mPath);

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(context, imagePath.getAbsolutePath() + "", Toast.LENGTH_LONG).show();
            Log.e("ImageSave", "Saveimage");
        } catch (FileNotFoundException e) {
            Log.e("ahmad", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("ahmad", e.getMessage(), e);
        }
    }

    public static boolean isLocalIP() {


        try {
            InetAddress addr1 = InetAddress.getByName(getIPAddress());
            InetAddress addr2 = InetAddress.getByName(localIP);
            InetAddress subnet = InetAddress.getByName(subMask);

            byte[] addr1Bytes = addr1.getAddress();
            byte[] addr2Bytes = addr2.getAddress();
            byte[] subnetBytes = subnet.getAddress();

            for (int i = 0; i < addr1Bytes.length; i++) {
                if ((addr1Bytes[i] & subnetBytes[i]) != (addr2Bytes[i] & subnetBytes[i])) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            Log.e("IP compare Issue", e.getMessage());
        }

        return false;
    }

    public static boolean hasPermission(String permission) {
        if (canMakeSmores() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            return (MainApplication.getAppContext()
                    .checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    public static Bitmap getBitmap(String urlStr) {

        try {
            URL url = new URL(urlStr);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {

            Log.e("ahmad", e.getMessage());

            return null;
        }
    }

    private static boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static Bitmap getBitmapFromBase64(String base64) {
        byte[] arry = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(arry, 0, arry.length);
    }

    /**
     * For phone like Sony, Samsung and HTC gets the photo.
     * in landscape. To correct that the following code has been used.
     * The image display size to be passed or else it will create bitmap
     * .as per the captured image resolution.
     * Then it will lead to out of memory issue.
     *
     * @param photoPath - String
     * @return - Bitmap
     */
    public static Bitmap checkPhotoOrientationAndGetImage(String photoPath) {
        try {
            ExifInterface ei = new ExifInterface(photoPath);
            int photoOrientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            Log.d("ahmad", "photoOrientation: " + photoOrientation);
            switch (photoOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateBitmap(decodeImageFile(photoPath), 90.f);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateBitmap(decodeImageFile(photoPath), 180.f);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateBitmap(decodeImageFile(photoPath), 270.f);
                default:
                    return decodeImageFile(photoPath);
            }
        } catch (IOException ie) {
            Log.d("ahmad", "IO Exception while creating bitmap");
        }
        return null;
    }

    /**
     * This is useful to rotate the image. But this will take more memory.
     *
     * @param source - Bitmap
     * @param angle  - float
     * @return - Bitmap
     */
    private static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        Bitmap resultImage = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
        if (resultImage != source) {
            source.recycle();
        }
        if (source.getWidth() != resultImage.getWidth()) {
            resultImage = Bitmap.createScaledBitmap(resultImage, source.getWidth(),
                    source.getHeight(), false);
        }
        System.gc();
        return resultImage;
    }

    /**
     * To convert and scale image from the path to bitmap.
     *
     * @param imagePath - String
     * @return - Bitmap
     */
    public static Bitmap decodeImageFile(String imagePath) {
        File file = new File(imagePath);
        Bitmap bitmap = null;

        //Decode image size
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            BitmapFactory.decodeStream(fis, null, options);
            fis.close();

            int scale = 1;
            if (options.outHeight > IMAGE_MAX_SIZE || options.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
                        (double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
            }
            //Decode with inSampleSize
            BitmapFactory.Options options1 = new BitmapFactory.Options();
            options1.inSampleSize = scale;
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, options1);
            fis.close();
        } catch (IOException io) {
            //  io.printStackTrace();
        }
        return bitmap;
    }

    /**
     * @param contentURI
     * @param context
     * @return real path of the image
     */
    public static String getRealPathFromURI(Uri contentURI, Activity context) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI,
                null,
                null,
                null,
                null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images
                    .ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    /**
     * @param context
     * @return screen dimenisons of your device
     */
    public static DisplayMetrics getScreenDimensions(Activity context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static String encodeImageBase64(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public static boolean isMarshMallowOrGreater() {

        return (Build.VERSION.SDK_INT >= 23);
    }

    public static String getFormatAmount(int amount) {

        final NumberFormat formatter = new DecimalFormat("###,###,###,###");

        return formatter.format(amount);
    }

    public static String getFormatAmount(Double amount) {

        final NumberFormat formatter = new DecimalFormat("###,###,###,###");

        return formatter.format(amount);
    }

    public static String getFormatAmount(String number) {

        try {
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
            formatter.applyPattern("#,###,###,###");
            return formatter.format(Long.parseLong(number));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return number;
        }
    }

    public static String formatDateFromString(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat dfinput = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat dfoutput = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = dfinput.parse(inputDate);
            outputDate = dfoutput.format(parsed);

        } catch (ParseException e) {

            Log.e("ahmad", "ParseException - dateFormat" + e.getMessage());
        }

        return outputDate;

    }

    public static CharSequence updateSpan(CharSequence text, String delim, int typePace) {
        Pattern pattern = Pattern.compile(Pattern.quote(delim) + "(.*?)" + Pattern.quote(delim));
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        if (pattern != null) {
            Matcher matcher = pattern.matcher(text);
            int matchesSoFar = 0;
            while (matcher.find()) {
                int start = matcher.start() - (matchesSoFar * 2);
                int end = matcher.end() - (matchesSoFar * 2);
                StyleSpan span = new StyleSpan(typePace);
                builder.setSpan(span, start + 1, end - 1, 0);
                builder.delete(start, start + 1);
                builder.delete(end - 2, end - 1);
                matchesSoFar++;
            }
        }
        return builder;
    }

    public static long getDaysBetween(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Date parsedDate2 = null;
        try {
            parsedDate2 = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate2);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            parsedDate2 = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Calculate the number of days between two dates
        long daysBetween = getDaysBetween(new Date(), parsedDate2);

        return daysBetween;
    }

    private static long getDaysBetween(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
}
