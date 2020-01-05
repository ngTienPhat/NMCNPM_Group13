package xute.markdeditor.api;

import android.content.Context;
import android.content.Intent;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploader {

    private ImageUploadCallback imageUploadCallback;

    public String uploadImage(String filePath, String serverToken) {
        final String[] downloadUrl = {""};
        File file = new File(filePath);
        final RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RetrofitApiClient.getClient(serverToken)
                .create(Api.class)
                .uploadFile(body)
                .enqueue(new Callback<FileUploadReponse>() {
                    @Override
                    public void onResponse(Call<FileUploadReponse> call, Response<FileUploadReponse> response) {
                        if (response.isSuccessful()) {
                            downloadUrl[0] = response.body().getUrl().toString();
                            if (imageUploadCallback != null) {
                                imageUploadCallback.onImageUploaded(response.body().getUrl());
                            }
                        } else {
                            if (imageUploadCallback != null) {
                                imageUploadCallback.onImageUploadFailed();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FileUploadReponse> call, Throwable t) {
                        if (imageUploadCallback != null) {
                            imageUploadCallback.onImageUploadFailed();
                        }
                    }
                });
        return downloadUrl[0];
    }

    public String uploadImage(Context context, String filePath, String userId) {
        final Context c = context;
        final String[] downloadUrl = {""};
        File file = new File(filePath);
        final RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RetrofitApiClient.getClient("")
                .create(Api.class)
                .uploadFile(userId, body)
                .enqueue(new Callback<FileUploadReponse>() {
                    @Override
                    public void onResponse(Call<FileUploadReponse> call, Response<FileUploadReponse> response) {
                        if (response.isSuccessful()) {
                            downloadUrl[0] = response.body().getUrl().toString();
                            if (imageUploadCallback != null) {
                                imageUploadCallback.onImageUploaded(response.body().getUrl());
                            }
                            Intent intent = new Intent();
                            intent.putExtra("url", downloadUrl[0]);
                            intent.setAction("AVATAR_UPLOADED");
                            c.sendBroadcast(intent);
                        } else {
                            if (imageUploadCallback != null) {
                                imageUploadCallback.onImageUploadFailed();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FileUploadReponse> call, Throwable t) {
                        if (imageUploadCallback != null) {
                            imageUploadCallback.onImageUploadFailed();
                        }
                    }
                });
        return downloadUrl[0];
    }

    public void setImageUploadCallback(ImageUploadCallback imageUploadCallback) {
        this.imageUploadCallback = imageUploadCallback;
    }

    public interface ImageUploadCallback {
        void onImageUploaded(String downloadUrl);
        void onImageUploadFailed();
    }
}
