package com.ki.dao;

import com.ki.dto.Member;
import com.ki.util.DBUtil;
import com.ki.util.SecSql;

import java.sql.Connection;
import java.util.Map;

public class MemberDao {
    private Connection conn;
    public MemberDao(Connection conn) {
        this.conn = conn;
    }

    public boolean isLoginIdDup(String loginId) {
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0 FROM `member` WHERE loginId = ?;", loginId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }

    public void doJoin(String loginId, String loginPw, String name) {
        SecSql sql = new SecSql();
        sql.append("INSERT INTO FROM `member`");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("loginId = ?,", loginId);
        sql.append("loginPw = ?,", loginPw);
        sql.append("name = ?,", name);

        DBUtil.insert(conn, sql);
    }

    public Member getMemberByloginId(String loginId) {
        SecSql sql = new SecSql();
        sql.append("SELECT * FROM `member` WHERE loginId = ?;", loginId);


        Map<String, Object> memberMap = DBUtil.selectRow(conn, sql);

        if (memberMap.isEmpty()) {
            return null;
        }

        return new Member(memberMap);
    }
}
