package com.zzy.biz;

import java.util.List;

import com.zzy.enums.StatusType;
import com.zzy.util.Paging;
import com.zzy.view.EmailTaskView;
import com.zzy.view.Result;



public interface EmailTaskBiz {
    public Result<Boolean> addEmailTask(String[] toMail, String title, String mailConent,
			String[] attachPath, String[] attachDescription,
			String[] attachName, String[] Cc, String[] Bcc,
			String mailType, String classify, String planTime);
    public List<EmailTaskView> getEmailTask(StatusType status,String sortColunn,String sortDir,Paging paging);
}
