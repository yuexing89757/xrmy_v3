
package com.zzy.model.supermodel;

import java.io.Serializable;
import com.zzy.anotation.ChangeComparison;

abstract public class ModelObject<ID extends Serializable> {

	protected ModelObject() {
	  }

	public ModelObject(ID id) {
		super();
		this.id = id;
	}

	@ChangeComparison
	protected ID id;

	/**********************************************************************
	 * below are get, set methods
	 **********************************************************************/

	public ID getId() {
		return this.id;
	}

	public void setId(final ID id) {
		this.id = id;
	}

	/*******************
	 * static functions
	 *******************//*

	protected static int makeHashCode(Object... properties) {
		int hashcode = 173; // SEED
		for (Object prop : properties) {
			if (prop != null) {
				if (prop.getClass().isArray()) {
					for (Object o : (Object[]) prop) {
						hashcode = calculateHashCode(hashcode, o);
					}
				} else {
					hashcode = calculateHashCode(hashcode, prop);
				}
			}
		}
		return hashcode;
	}

	protected static int makeHashCode(Integer hashcode, Object... properties) {
		return 37 * hashcode + makeHashCode(properties);
	}

	private static int calculateHashCode(int hashcode, Object o) {
		int objCode = 0;
		if (o instanceof NetworkObject) {
			NetworkObject netObj = (NetworkObject) o;
			if (netObj.getId() != null)
				objCode = netObj.getId().hashCode();
			if (netObj.getRemoteID() != null)
				objCode = 37 * objCode + netObj.getRemoteID().hashCode();
		} else if (o instanceof ModelObject) {
			ModelObject<?> modObj = (ModelObject<?>) o;
			if (modObj.getId() != null)
				objCode = modObj.getId().hashCode();
		} else if (o != null) {
			objCode = o.hashCode();
		}
		if (objCode == 0)
			return hashcode;
		return 37 * hashcode + objCode;
	}*/
   
	//toString  方法
	protected static String makeIdentifiableString(ModelObject<?> target, Object... objs) {
		if (target == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if (objs != null) {
			for (Object obj : objs) {
				if (obj != null) {
					String str = obj.toString();
					sb.append(str);
					sb.append(" - ");
				}
			}
		}
		return String.format("%s[%s]: %s", target.getClass().getSimpleName(), target.getId(), sb);
	}
	
	
}
