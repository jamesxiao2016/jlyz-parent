package cn.dlbdata.dj.thirdparty.mp.sdk.model.domain.res;

/**
 * ClassName: VideoMessage
 * @Description: 视频消息
 */
public class VideoMessage extends BaseMessage{
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
