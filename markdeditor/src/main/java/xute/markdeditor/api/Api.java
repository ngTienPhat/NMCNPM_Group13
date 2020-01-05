package xute.markdeditor.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {
  @Multipart
  @POST("upload")
  Call<FileUploadReponse> uploadFile(@Query("userid") String userId, @Part MultipartBody.Part file);

  @Multipart
  @POST("upload")
  Call<FileUploadReponse> uploadFile(@Part MultipartBody.Part file);
}
