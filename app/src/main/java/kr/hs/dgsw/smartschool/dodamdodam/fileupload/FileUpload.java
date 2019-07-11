package kr.hs.dgsw.smartschool.dodamdodam.fileupload;

import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.NetworkFileUpload;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.FileUploadService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class FileUpload {

    private FileUploadService fileUploadService;
    private OnFinishUploadListener onFinishUploadListener;
    private TokenManager manager;
    private String fileName;
    private String fileType;
    private String fileExt;

    public FileUpload() {
        fileUploadService = Utils.RETROFIT.create(FileUploadService.class);
    }

    public String FileUpload(byte[] imageBytes, String originalName, String uploadName) {
        fileName = uploadName;

        String[] filenameArray = originalName.split("\\.");
        String extension = filenameArray[filenameArray.length - 1];

        fileType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        fileExt = "." + extension;

        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(fileType)), imageBytes);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", fileName + fileExt, requestFile);

        RequestBody fileNameBody = RequestBody.create(MediaType.parse("text/plain"), fileName);

        Call<Response> uploadRequest = fileUploadService.uploadFile(manager.getToken().toString(), fileNameBody.toString(), body);

        new NetworkFileUpload(fileName)
                .setOnTaskFinishListener(
                        string -> {
                            if (onFinishUploadListener != null) {
                                onFinishUploadListener.onFinish(string);
                            }
                        })
                .execute(uploadRequest);

        return fileName;
    }


    public interface OnFinishUploadListener {
        void onFinish(String str);
    }
}
