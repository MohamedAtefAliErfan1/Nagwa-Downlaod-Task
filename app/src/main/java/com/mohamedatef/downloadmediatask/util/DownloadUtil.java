package com.mohamedatef.downloadmediatask.util;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.mohamed_atef.nagwatask.data.model.MediaModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import kotlin.jvm.functions.Function1;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class DownloadUtil {
    Boolean iserror ;

    public Function1<Response<ResponseBody>, Observable<File>> processResponse(MutableLiveData<Integer> integerMutableLiveData, MediaModel mediaModel, MutableLiveData<Integer> total,MutableLiveData<Boolean> success) {
        return new Function1<Response<ResponseBody>, Observable<File>>() {
            @Override
            public Observable<File> invoke(Response<ResponseBody> responseBodyResponse) {
                return saveToDiskRx(responseBodyResponse, integerMutableLiveData, mediaModel, total,success);
            }

        };
    }

    public Observable<File> saveToDiskRx(final Response<ResponseBody> response, MutableLiveData<Integer> integerMutableLiveData, MediaModel mediaModel, MutableLiveData<Integer> totalsize,MutableLiveData<Boolean> success) {
        return Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> emitter) throws Exception {
                int read = 0;
                byte[] buffer = new byte[32768];
                int count;
                long fileSize = response.body().contentLength();
                InputStream is = new BufferedInputStream(response.body().byteStream(), 1024 * 8);
                String ext = mediaModel.getType().equals("VIDEO") ? ".mp4" : ".pdf";
                File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), mediaModel.getName() + ext);
                OutputStream fos = new FileOutputStream(outputFile);
                Long total = 0L;
                int progress;
                while ((read = is.read(buffer)) > 0) {
                    total += read;
                    int totalFileSize = (int) (fileSize / Math.pow(1024.0, 2.0));
                    double current = Math.round(total / Math.pow(1024.0, 2.0));
                    progress = (int) (total * 100 / fileSize);
                    integerMutableLiveData.postValue(progress);
                    totalsize.postValue(totalFileSize);
                    fos.write(buffer, 0, read);
                    iserror = progress < 0;
                }
                fos.close();
                is.close();
                mediaModel.setPathFileLocal(outputFile.getAbsolutePath());
                 if (!iserror)
                { mediaModel.setCompleted(true);
                emitter.onNext(outputFile.getAbsoluteFile()); }
                else
                {
                    success.setValue(false);
                }


            }


        });
    }

    public Observer<File> handleResult(MutableLiveData<Boolean> error, MutableLiveData<Boolean> success) {
        return new Observer<File>() {
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.d("TAG", "Error " + e.getMessage());
                error.postValue(true);
            }

            @Override
            public void onComplete() {
                Log.d("TAG", "onCompleted");

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(File file) {
                Log.d("TAG", "File downloaded to " + file.getAbsolutePath());
                    success.postValue(true);


            }
        };
    }

}
