package com.ki.service;

import com.ki.dao.MemberDao;
import com.ki.dto.Member;

import java.sql.Connection;
import java.util.List;

public class MemberService {

    private MemberDao memberDao;

    public MemberService(Connection conn) {
        this.memberDao = new MemberDao(conn);
    }

    public boolean isLoginIdDup(Connection conn, String loginId) {
        return memberDao.isLoginIdDup(conn, loginId);
    }

    public void doJoin(String loginId, String loginPw, String name) {
        memberDao.doJoin(loginId, loginPw, name);
    }

    public List<Member> foundMember() {
        return memberDao.foundMember();
    }
}
