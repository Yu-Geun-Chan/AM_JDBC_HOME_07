package com.ki.service;

import com.ki.dao.MemberDao;
import com.ki.dto.Member;

import java.sql.Connection;
import java.util.Map;

public class MemberService {
    private MemberDao memberDao;

    public MemberService(Connection conn) {
        memberDao = new MemberDao(conn);
    }

    public boolean isLoginIdDup(String loginId) {
        return memberDao.isLoginIdDup(loginId);
    }

    public void doJoin(String loginId, String loginPw, String name) {
        memberDao.doJoin(loginId, loginPw, name);
    }

    public Member getMemberByloginId(String loginId) {
        return memberDao.getMemberByloginId(loginId);
    }
}
