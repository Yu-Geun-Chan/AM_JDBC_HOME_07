package com.ki.controller;

import com.ki.articleManager.Container;
import com.ki.dto.Member;
import com.ki.service.MemberService;
import com.ki.util.DBUtil;
import com.ki.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberController extends Controller{
    private Connection conn;

    private MemberService memberService;

    public MemberController(Connection conn) {
        this.conn = conn;
    }

    public void doJoin() {
        System.out.println("== 회원가입 ==");
        String loginId = null;
        String loginPw = null;
        String name = null;
        while (true) {
            System.out.print("아이디 입력 : ");
            loginId = Container.getScanner().nextLine().trim();

            if (loginId.isEmpty() || loginId.contains(" ")) {
                System.out.println("아이디를 입력하세요.");
                continue;
            }
            boolean isLoginIdDup = memberService.isLoginIdDup(conn, loginId);

            if (!isLoginIdDup) {
                System.out.printf("[%s]은(는) 이미 사용중인 아이디입니다.\n", loginId);
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("비밀번호 입력 : ");
            loginPw = Container.getScanner().nextLine().trim();

            if (loginPw.isEmpty() || loginPw.contains(" ")) {
                System.out.println("비밀번호를 입력하세요.");
                continue;
            }

            System.out.print("비밀번호 확인 : ");
            String enterLoginPw = Container.getScanner().nextLine().trim();

            if (enterLoginPw.isEmpty() || enterLoginPw.contains(" ")) {
                System.out.println("비밀번호 확인을 입력하세요.");
                continue;
            }

            if (!enterLoginPw.equals(loginPw)) {
                System.out.println("비밀번호가 일치하지 않습니다.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("이름 : ");
            name = Container.getScanner().nextLine().trim();

            if (name.isEmpty() || name.contains(" ")) {
                System.out.println("이름을 입력하세요.");
                continue;
            }
            break;
        }

        SecSql sql = new SecSql();

        sql.append("INSERT INTO from `member`");
        sql.append("SET regDate = NOW(),");
        sql.append("loginId= '" + loginId + "',");
        sql.append("loginPw= '" + loginPw + "',");
        sql.append("`name`= '" + name + "';");

        DBUtil.insert(conn, sql);

        System.out.printf("[%s]님 회원가입을 환영합니다.\n", name);
    }

    public void doLogin() {
        System.out.println("== 로그인 ==");
        if (loginedMember != null) {
            System.out.println("로그아웃 후 이용해주세요.");
            return;
        }

        List<Member> members = new ArrayList<Member>();

        while (true) {
            System.out.printf("아이디 : ");
            String enterLoginId = Container.getScanner().nextLine().trim();

            if (enterLoginId.isEmpty() || enterLoginId.contains(" ")) {
                System.out.println("아이디를 입력하세요.");
                continue;
            }

            System.out.print("비밀번호 : ");
            String enterLoginPw = Container.getScanner().nextLine().trim();

            if (enterLoginPw.isEmpty() || enterLoginPw.contains(" ")) {
                System.out.println("비밀번호를 입력하세요.");
                continue;
            }

            SecSql sql = new SecSql();
            sql.append("SELECT * FROM `member`;");

            List<Map<String, Object>> memberListMap = DBUtil.selectRows(conn, sql);

            for (Map<String, Object> memberMap : memberListMap) {
                members.add(new Member(memberMap));
            }
            Member member = null;

            for (Member m : members) {
                if (m.getLoginId().equals(enterLoginId)) {
                    member = m;
                }
                break;
            }

            if (member == null) {
                System.out.printf("[%s]은(는) 존재하지 않는 아이디 입니다.\n", enterLoginId);
                continue;
            }

            if (!member.getLoginPw().equals(enterLoginPw)) {
                System.out.println("비밀번호를 확인해주세요.");
                continue;
            }
            loginedMember = member;
            System.out.printf("[%s]님 환영합니다\n", loginedMember.getName());
            break;
        }
    }
    public void doLogout() {
        System.out.println("== 로그아웃 ==");

        if (loginedMember == null) {
            System.out.println("로그인 후 이용해주세요.");
        }
        loginedMember = null;
    }
}
