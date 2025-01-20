package com.px.employmentsite.vo.system;

import com.px.employmentsite.model.SysLog;
import lombok.Data;

@Data
public class LogModel extends SysLog {
    public String userName;
    public String loginID;
}
