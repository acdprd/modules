package su.bnet.utils.utils;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {

    public static List<MultipartBody.Part> getPartsForFile(File file) {
        List<MultipartBody.Part> res = new ArrayList<>();
        if (file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            res.add(part);
        }
        return res;
    }

}
