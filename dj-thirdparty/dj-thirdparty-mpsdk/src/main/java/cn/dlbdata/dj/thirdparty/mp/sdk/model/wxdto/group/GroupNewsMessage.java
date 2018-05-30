package cn.dlbdata.dj.thirdparty.mp.sdk.model.wxdto.group;


import cn.dlbdata.dj.thirdparty.mp.sdk.model.common.ConstantUtil;

/**
 * 主动文本消息
 * 
 */
public class GroupNewsMessage extends GroupBaseMessage {
	
	// 回复的消息内容
	private GroupNews mpnews;

	public GroupNewsMessage() {
		this.setMsgtype(ConstantUtil.WX_MSG_TYPE.mpnews.toString());
	}

	public GroupNews getMpnews() {
		return mpnews;
	}

	public void setMpnews(GroupNews mpnews) {
		this.mpnews = mpnews;
	}

}
