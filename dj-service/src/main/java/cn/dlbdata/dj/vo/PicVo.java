package cn.dlbdata.dj.vo;

import java.io.Serializable;

public class PicVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 微信返回的字符串
	 */
	private String mediaId;
	
	private long pictureId;
	/**
	 * 当前用户id
	 */
	private long userId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public long getPictureId() {
		return pictureId;
	}

	public void setPictureId(long pictureId) {
		this.pictureId = pictureId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	
	
}
