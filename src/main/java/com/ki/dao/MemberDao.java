package com.ki.dao;

import com.ki.util.DBUtil;
import com.ki.util.SecSql;

import java.sql.Connection;

public class MemberDao {
    public boolean isLoginIdDup(Connection conn, String loginId) {
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0 FROM `member` WHERE loginId = '" + loginId + "';");

        return DBUtil.selectRowBooleanValue(conn, sql);
    }
}
