package com.zzy.enums;

/**
 * @name LikeMode
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author Ai.jy
 * 
 * @since 2011-1-5
 * 
 * @version 1.0
 */
public enum LikeMode {

	BEFORE {
		@Override
		public LikeMode setValue(String val) {
			this.val = escape(val) + "%";
			return this;
		}
	},
	END {
		@Override
		public LikeMode setValue(String val) {
			this.val = "%" + escape(val);
			return this;
		}
	},
	MIDDLE {
		@Override
		public LikeMode setValue(String val) {
			this.val = "%" + escape(val) + "%";
			return this;
		}
	},
	NOTLIKE {
		@Override
		public LikeMode setValue(String val) {
			this.val = "%" + escape(val) + "%";
			return this;
		}
	},
	PRECISE {
		@Override
		public LikeMode setValue(String val) {
			this.val = val;
			return this;
		}
	};
	public String getValue() {
		return this.val;
	}
	
	//mysql特殊字符'%','_'转义处理
	public String escape(String val){
		if(null != val && val.indexOf("_") != -1){
			val = val.replace("_", "\\_");
		}else if(null != val && val.indexOf("%") != -1){
			val = val.replace("%", "\\%");
		}
		return val;
	}

	public abstract LikeMode setValue(String val);

	protected String val;

}
