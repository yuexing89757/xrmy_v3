package com.zzy.scheduler;



import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TaskDependencyManager 用来缓存数据库中的 task 依赖关系
 * 
 * <pre>
 * 查询对象 M->P->P
 * 
 * Rule:
 * 1. 若一个 目标对象 的子对象中有关联的可执行任务，则该 目标对象 不能修改
 *    => 对于 M，graph中都找不到任何相等的节点
 * 2. 若一个 目标对象 的直系父对象中有关联的可执行任务，则该 目标对象 不能修改
 *    => 对于 M 中的任何一个P，graph中任何一个M节点都与P不相等
 * </pre>
 * 
 * @author kenliu
 * 
 */
public class TaskDependencyManager {

	private static Map<String, TaskDependencyManager> tags = new ConcurrentHashMap<String, TaskDependencyManager>();
	
	public static TaskDependencyManager getInstance(String tag) {
		if (!tags.containsKey(tag)) {
			tags.put(tag, new TaskDependencyManager(tag));
		}
		return tags.get(tag);
	}

	private TaskDependencyManager() {
		throw new IllegalArgumentException("need a valid tag");
	}

	private TaskDependencyManager(String tag) {
		this.tag = tag;
	}

	private String tag;

	public String getTag() {
		return tag;
	}

	
	

}
