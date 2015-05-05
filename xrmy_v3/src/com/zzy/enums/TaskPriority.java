package com.zzy.enums;


public enum TaskPriority implements IEnumCode {
	REALTIME(1), HIGH(2), NORMAL(3), LOW(4), IDLE(5);

	private TaskPriority(int level) {
		this.level = level;
	}

	private int level;

	public static TaskPriority fromLevel(int level) {
		for (TaskPriority taskPriority : TaskPriority.values()) {
			if (taskPriority.level==level) {
				return taskPriority;
			}
		}
		return null;
	}
	
	public int getLevel() {
		return this.level;
	}

	public String getCode() {
		return this.level+"";
	}

	public boolean equals(String s) {
		return this.name().equalsIgnoreCase(s);
	}
}