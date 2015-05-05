package com.zzy.scheduler;

import java.util.List;

import com.zzy.model.Task;


public abstract class GenericWorker {
	  abstract public boolean execute(final Task task);

	  abstract public <T extends Task> boolean execute(final List<T> tasks);

}
