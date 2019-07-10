package kr.hs.dgsw.smartschool.dodamdodam.Model.song;

public class VideoYoutubeData extends YoutubeData {

    private final Video source;

    public VideoYoutubeData(Video video, String quality) {
        super(video.getVideoId(), "https://i.ytimg.com/vi/" + video.getVideoId() + '/' + quality + ".jpg", video.getVideoTitle(), video.getChannelTitle());
        source = video;
    }

    public Video getSource() {
        return source;
    }
}
