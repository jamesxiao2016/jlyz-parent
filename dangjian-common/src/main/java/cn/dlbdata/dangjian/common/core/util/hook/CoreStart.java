package cn.dlbdata.dangjian.common.core.util.hook;

import java.util.List;

/**
 * 应用系统启动实现类
 * 
 * @author jsl
 */
public class CoreStart
{
	private List<IAppStart> startList;

	public void setStartList(List<IAppStart> startList)
	{
		this.startList = startList;
	}
	
	public void startUp()
	{
		for (IAppStart s : startList)
		{
			s.startUp();
		}
	}

}
