package com.zzy.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ExceptionUtil {

	public static String getStackTrace(Throwable t) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		t.printStackTrace(printWriter);
		return result.toString();
	}

	public static String[] getStackTraces(Throwable t) {
		StackTraceElement[] stackTrace = t.getStackTrace();
		List<String> traces = new ArrayList<String>();
		for (StackTraceElement e : stackTrace) {
			if (e.isNativeMethod()) {
				break;
			}
			traces.add(e.getClassName() + ":" + e.getMethodName() + "@"
					+ e.getFileName() + ":" + e.getLineNumber());
		}
		return traces.toArray(new String[] {});
	}
}
