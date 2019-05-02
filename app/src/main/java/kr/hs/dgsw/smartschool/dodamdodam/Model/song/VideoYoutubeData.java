package kr.hs.dgsw.smartschool.dodamdodam.Model.song;

public class VideoYoutubeData extends YoutubeData {

    public VideoYoutubeData(Video video) {
        super(video.getVideoId(), video.getThumbnail(), video.getVideoTitle(), video.getChannelTitle());
    }

}
