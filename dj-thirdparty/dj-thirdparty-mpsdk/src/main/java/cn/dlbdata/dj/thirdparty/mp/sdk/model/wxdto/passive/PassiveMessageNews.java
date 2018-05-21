package cn.dlbdata.dj.thirdparty.mp.sdk.model.wxdto.passive;

import java.util.List;

import cn.dlbdata.dj.thirdparty.mp.sdk.model.common.ConstantUtil;


public class PassiveMessageNews extends PassiveBaseMessage {

	private int ArticleCount;

	private List<PassiveArticle> Articles;

	public PassiveMessageNews() {
		this.setMsgType(ConstantUtil.WX_MSG_TYPE.news.toString());
	}
	
	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<PassiveArticle> getArticles() {
		return Articles;
	}

	public void setArticles(List<PassiveArticle> articles) {
		Articles = articles;
	}
	
}
