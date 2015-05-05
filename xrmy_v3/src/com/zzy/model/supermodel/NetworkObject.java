/**
 * Project name: ads-backend-model
 *
 * package_name: com.pzoom.ads.platform.backend.model
 *
 * Filename : NetworkObject.java
 *
 * Edition information :
 *
 * Date : 2010-12-30
 *
 * Copyright Pzoomtech.com 2010, All Rights Reserved.
 *
 */
package com.zzy.model.supermodel;

import com.sun.jmx.snmp.tasks.Task;
import com.zzy.enums.AdNetwork;


/**
 *
 * @name NetworkObject
 *
 * @description CLASS_DESCRIPTION
 *
 *              MORE_INFORMATION
 *
 * @author WangXL
 *
 * @since 2011-1-27
 *
 * @version 1.0
 */
abstract public class NetworkObject extends ModelObject<Long> {

	protected AdNetwork network;
	protected Task task;
	protected String failuresCode;
	protected NetworkObject() {
		 //this.network = getNetwork();
	}

	public NetworkObject(Long id) {
		super(id);
		this.network = getNetwork();
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	public String getFailuresCode() {
		return failuresCode;
	}

	public void setFailuresCode(String failuresCode) {
		this.failuresCode = failuresCode;
	}
	

	/**
	 *
	 *
	 * getNetwork
	 *
	 * get Network
	 *
	 * @return Network
	 *
	 * @since 1.0
	 */
	abstract public AdNetwork getNetwork();

	abstract public Long getRemoteID();

	abstract public void setRemoteID(Long remoteID);

	public String write(String split){return "";}
	
	public String write(String split,Object... objs){
		StringBuilder sb = new StringBuilder();
		if (objs != null) {
			for (Object obj : objs) {
				if (obj != null) {
					String str = obj.toString();
					sb.append(str);
					sb.append(split);
				}
			}
		}
		return (String) sb.subSequence(0, sb.length()-1);
	}
	
}
