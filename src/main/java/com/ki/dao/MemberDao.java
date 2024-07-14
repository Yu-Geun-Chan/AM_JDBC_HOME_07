package com.ki.dao;

import com.ki.dto.Member;
import com.ki.util.DBUtil;
import com.ki.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberDao {
    private Connection conn;

    public MemberDao(Connection conn){
        this.conn = conn;
    }
    public boolean isLoginIdDup(Connection conn, String loginId) {
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0 FROM `member` WHERE loginId = '" + loginId + "';");

        return DBUtil.selectRowBooleanValue(conn, sql);
    }

    public void doJoin(String loginId, String loginPw, String name) {
        SecSql sql = new SecSql();

        sql.append("INSERT INTO from `member`");
        sql.append("SET regDate = NOW(),");
        sql.append("loginId= '" + loginId + "',");
        sql.append("loginPw= '" + loginPw + "',");
        sql.append("`name`= '" + name + "';");
    }

    public List<Member> foundMember() {
        SecSql sql = new SecSql();

        sql.append("SELECT * FROM `member`;");

        List<Map<String, Object>> memberListMap = DBUtil.selectRows(conn, sql);

        List<Member> members = new ArrayList<>();

        for (Map<String, Object> memberMap : memberListMap) {
            members.add(new Member(memberMap));
        }
        return members;
    }
}
