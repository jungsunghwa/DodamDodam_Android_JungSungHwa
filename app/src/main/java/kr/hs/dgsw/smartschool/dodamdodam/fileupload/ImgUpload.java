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
    private String fileType;
    private String uploadName;

    public ImgUpload(Context context) {
        fileUploadService = Utils.RETROFIT.create(FileUploadService.class);
        manager = TokenManager.getInstance(context);
    }

    public List<Picture> ImgUpload(File file) {

        String[] filenameArray = file.getName().split("\\.");
        String extension = filenameArray[filenameArray.length - 1];

        fileType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        uploadName = new Random().nextInt(999999999) + fileType;

        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imgToUpload = MultipartBody.Part.createFormData("image", uploadName, mFile);

        Call<retrofit2.Response<Response>> uploadRequest = fileUploadService.uploadImg(manager.getToken().getToken(), imgToUpload);

        new NetworkFileUpload(file.getName())
                .setOnTaskFinishListener(
                        string -> {
                            if (onFinishUploadListener != null) {
                                onFinishUploadListener.onFinish(string);
                            }
                        })
                .execute(uploadRequest);

        Picture picture = new Picture(file.getName(), uploadName, fileType);

        List<Picture> pictures = new ArrayList<>();
        pictures.add(picture);

        return pictures;
    }


    public interface OnFinishUploadListener {
        void onFinish(String str);
    }
}
