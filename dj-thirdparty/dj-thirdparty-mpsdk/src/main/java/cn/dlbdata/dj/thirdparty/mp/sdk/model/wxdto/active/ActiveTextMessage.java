package cn.dlbdata.dj.thirdparty.mp.sdk.model.wxdto.active;


import cn.dlbdata.dj.thirdparty.mp.sdk.model.common.ConstantUtil;

/**
 * 主动文本消息
 * 
 */
public class ActiveTextMessage extends ActiveBaseMessage {
	
	// 回复的消息内容
	private ActiveText text;

	public ActiveTextMessage() {
		this.setMsgtype(ConstantUtil.WX_MSG_TYPE.text.toString());
	}

	public ActiveText getText() {
		return text;
	}

	public void setText(ActiveText text) {
		this.text = text;
	}
	
}
