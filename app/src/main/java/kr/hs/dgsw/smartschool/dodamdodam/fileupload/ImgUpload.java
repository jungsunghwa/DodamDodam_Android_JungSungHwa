package kr.hs.dgsw.smartschool.dodamdodam.fileupload;

import android.app.Application;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.Picture;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.NetworkFileUpload;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.FileUploadService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ImgUpload {

    private FileUploadService fileUploadService;
    private OnFinishUploadListener onFinishUploadListener;
    private TokenManager manager;
    private String fileExt;
    private String fileType;
    private String uploadName;

    public ImgUpload(Context context) {
        fileUploadService = Utils.RETROFIT.create(FileUploadService.class);
        manager = TokenManager.getInstance(context);
    }

    public List<Picture> ImgUpload(byte[] imageBytes, String originalName) {

        String[] filenameArray = originalName.split("\\.");
        String extension = filenameArray[filenameArray.length - 1];

        fileType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        fileExt = "." + extension;

        uploadName = Integer.toString(new Random().nextInt(999999999));

        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(fileType)), imageBytes);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", uploadName + fileExt, requestFile);

        RequestBody fileNameBody = RequestBody.create(MediaType.parse("text/plain"), uploadName);

//        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part imgToUpload = MultipartBody.Part.createFormData("image", uploadName, mFile);

        Call<retrofit2.Response<Response>> uploadRequest = fileUploadService.uploadImg(manager.getToken().getToken(), body, fileNameBody);

        new NetworkFileUpload(uploadName)
                .setOnTaskFinishListener(
                        string -> {
                            if (onFinishUploadListener != null) {
                                onFinishUploadListener.onFinish(string);
                            }
                        })
                .execute(uploadRequest);

        Picture picture = new Picture(originalName, uploadName + fileExt, fileExt);

        List<Picture> pictures = new ArrayList<>();
        pictures.add(picture);

        return pictures;
    }


    public interface OnFinishUploadListener {
        void onFinish(String str);
    }
}
