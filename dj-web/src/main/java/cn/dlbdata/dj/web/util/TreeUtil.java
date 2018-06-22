package cn.dlbdata.dj.web.util;

import cn.dlbdata.dj.db.vo.dept.DeptTreeVo;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {
	public static List<DeptTreeVo> RecursiveAddress(List<DeptTreeVo> treeNodes) {
		List<DeptTreeVo> trees = new ArrayList<>();
		for (DeptTreeVo treeNode : treeNodes) {
			if (null == treeNode.getParentId() || treeNode.getParentId() == 0) {
				trees.add(findAddressChildren(treeNode, treeNodes));
			}
		}
		return trees;
	}

	/**
	 * 递归查找地址子节点
	 * 
	 * @param treeNodes
	 * @return
	 */
	public static DeptTreeVo findAddressChildren(DeptTreeVo treeNode, List<DeptTreeVo> treeNodes) {
		for (DeptTreeVo it : treeNodes) {
			if (treeNode.getDeptId().equals(it.getParentId())) {
				if (treeNode.getChildren() == null) {
					treeNode.setChildren(new ArrayList<>());
				}
				treeNode.getChildren().add(findAddressChildren(it, treeNodes));
			}
		}
		return treeNode;
	}

}