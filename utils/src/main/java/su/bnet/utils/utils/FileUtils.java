package su.bnet.utils.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import su.bnet.utils.BuildConfig;
import su.bnet.utils.extensions.ExtensionsKt;

public class FileUtils {
    public static String getPathToImages(Context context) {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    public static File createPictureJpgFile(Context context) throws IOException {
        return createPictureFile(context, "JPEG", "jpg");
    }

    public static File createPicturePngFile(Context context) throws IOException {
        return createPictureFile(context, "PNG", "png");
    }

    public static File createPictureFile(Context context, String nameFirstPart, String suffix) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",new Locale("ru")).format(System.currentTimeMillis());
        String imageFileName = nameFirstPart + "_" + timeStamp;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,          //prefix
                "" + suffix,     //suffix
                storageDir              //directory
        );
        return image;
    }

//    public static File createCacheFileToPot(Context context, String mac, OfflineUtils.FileType type) throws IOException{
////        String imageFileName = OfflineUtils.INSTANCE.getFileName(url);
//        String fName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
//        File storageDir = OfflineUtils.INSTANCE.generateDirectory(OfflineUtils.INSTANCE.getImagesDirectory(context),mac,type);
//        if (!storageDir.exists()) {
//            storageDir.mkdirs();
//        }
//        File image = File.createTempFile(
//                fName,          //prefix
//                ".jpg",     //suffix
//                storageDir              //directory
//        );
//        return image;
//    }

    public static File createPictureFile(Context context, File storageDir, String nameFirstPart, String suffix) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
        String imageFileName = nameFirstPart + "_" + timeStamp;
        if (storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,          //prefix
                "" + suffix,     //suffix
                storageDir              //directory
        );
        return image;
    }

    public static Uri getFileUri(Context context, File photoFile) {
        if (context == null || photoFile == null) {
            return null;
        }
        Log.i(ExtensionsKt.tag(FileUtils.class), "getFileUri photoFile.getAbsolutePath(): " + photoFile.getAbsolutePath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", photoFile);
        } else {
            return Uri.fromFile(photoFile);
        }
    }

//    public static String getPath(final Context context, final Uri uri) {
//
//        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//
//        // DocumentProvider
//        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
//            // ExternalStorageProvider
//            if (isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                if ("primary".equalsIgnoreCase(type)) {
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }
//
//                // TODO handle non-primary volumes
//            }
//            // DownloadsProvider
//            else if (isDownloadsDocument(uri)) {
//
//                final String id = DocumentsContract.getDocumentId(uri);
//                final Uri contentUri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//
//                return getDataColumn(context, contentUri, null, null);
//            }
//            // MediaProvider
//            else if (isMediaDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//
//                Uri contentUri = null;
//                if ("image".equals(type)) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//
//                final String selection = "_id=?";
//                final String[] selectionArgs = new String[]{
//                        split[1]
//                };
//
//                return getDataColumn(context, contentUri, selection, selectionArgs);
//            }
//        }
//        // MediaStore (and general)
//        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            return getDataColumn(context, uri, null, null);
//        }
//        // File
//        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;
//    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
