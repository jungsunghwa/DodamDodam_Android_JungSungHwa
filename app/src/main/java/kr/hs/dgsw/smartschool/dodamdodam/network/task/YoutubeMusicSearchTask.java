package kr.hs.dgsw.smartschool.dodamdodam.network.task;

import android.content.Context;
import android.os.AsyncTask;

import androidx.core.text.HtmlCompat;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import com.google.api.services.youtube.model.Thumbnail;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.Model.song.YoutubeData;

public class YoutubeMusicSearchTask extends AsyncTask<String, Void, List<YoutubeData>> {

    private YouTube service;
    private Exception exception;
    private OnTaskListener<List<YoutubeData>> taskListener;

    public YoutubeMusicSearchTask(Context context, GoogleAccountCredential credential, OnTaskListener<List<YoutubeData>> taskListener) {
        HttpTransport transport = new NetHttpTransport.Builder().build();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        this.taskListener = taskListener;
        service = new YouTube.Builder(transport, jsonFactory, credential).setApplicationName(context.getString(R.string.app_name)).build();
    }


    @Override
    protected List<YoutubeData> doInBackground(String... strings) {
        try {
            SearchListResponse searchListResponse =
                    service.search()
                            .list("snippet")
                            .setSafeSearch("strict")
                            .setType("video")
                            .setVideoCategoryId("10") // 10 = 음악
                            .setMaxResults(15L) // 최대 15개
                            .setQ(strings[0])
                            .execute();
            List<SearchResult> searchResults = searchListResponse.getItems();
            List<YoutubeData> results = new ArrayList<>();
            if (searchResults != null) {
                for (SearchResult result : searchResults) {
                    SearchResultSnippet snippets = result.getSnippet();
                    String videoId = result.getId().getVideoId();

                    Thumbnail mediumThumb = snippets.getThumbnails().getMedium();
                    String videoTitle = HtmlCompat.fromHtml(snippets.getTitle(), HtmlCompat.FROM_HTML_MODE_COMPACT).toString();
                    String channelTitle = HtmlCompat.fromHtml(snippets.getChannelTitle(), HtmlCompat.FROM_HTML_MODE_COMPACT).toString();
                    if (mediumThumb != null) {
                        results.add(new YoutubeData(videoId, mediumThumb.getUrl(), videoTitle, channelTitle));
                    } else
                        results.add(new YoutubeData(videoId, videoTitle,  channelTitle));
                }
            }
            return results;
        } catch (Exception e) {
            exception = e;
            cancel(true);
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<YoutubeData> data) {
        super.onPostExecute(data);
        if (taskListener != null)
            taskListener.onFinished(data);
    }

    @Override
    protected void onCancelled() {
        if (exception != null) {
            if (taskListener != null)
                taskListener.onFailed(exception);
        } else if (taskListener != null)
            taskListener.onCancelled();
    }
}