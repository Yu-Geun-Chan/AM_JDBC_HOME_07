package com.ki.controller;

import com.ki.articleManager.Container;
import com.ki.dto.Member;
import com.ki.service.MemberService;
import com.ki.util.DBUtil;
import com.ki.util.SecSql;

import java.sql.Connection;
import java.util.Map;


public class MemberController extends Controller {
    private Connection conn;
    private MemberService memberService;

    public MemberController(Connection conn) {
        this.conn = conn;
        this.memberService = new MemberService(conn);
    }

    public void dojoin() {
        System.out.println("== 회원가입 ==");
        String loginId = null;
        String loginPw = null;
        String name = null;
        while (true) {
            System.out.print("아이디 입력 : ");
            loginId = Container.getScanner().nextLine().trim();
            if (loginId.isEmpty() || loginId.contains(" ")) {
                System.out.println("아이디를 올바르게 입력하세요.");
                continue;
            }

            boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

            if (isLoginIdDup) {
                System.out.printf("[%s]은/는 이미 사용중인 아이디입니다.\n", loginId);
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("비밀번호 입력 : ");
            loginPw = Container.getScanner().nextLine().trim();
            if (loginPw.isEmpty() || loginPw.contains(" ")) {
                System.out.println("비밀번호를 올바르게 입력하세요.");
                continue;
            }
            System.out.print("비밀번호 확인 입력 : ");
            String loginPwCheck = Container.getScanner().nextLine().trim();

            if (!loginPw.equals(loginPwCheck)) {
                System.out.println("비밀번호가 일치하지 않습니다.");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("이름 입력 : ");
            name = Container.getScanner().nextLine().trim();
            if (name.isEmpty() || name.contains(" ")) {
                System.out.println("이름을 올바르게 입력하세요.");
                continue;
            }
            break;
        }

        memberService.doJoin(loginId, loginPw, name);

        System.out.printf("[%s]님 회원가입을 환영합니다.\n", loginId);
    }

    public void doLogin() {
        if (loginedMember != null) {
            System.out.println("로그아웃 후 이용해주세요");
        }
        System.out.println("== 로그인 ==");
        String loginId = null;
        String loginPw = null;

        while (true) {
            System.out.print("로그인 아이디 입력 : ");
            loginId = Container.getScanner().nextLine().trim();

            if (loginId.isEmpty() || loginId.contains(" ")) {
                System.out.println("로그인 아이디 입력을 올바르게 해주세요.");
                continue;
            }

            boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

            if (!isLoginIdDup) {
                System.out.printf("[%s]은/는 존재하지 않는 아이디입니다.\n", loginId);
                continue;
            }
            break;
        }

        Member member = memberService.getMemberByloginId(loginId);

        while (true) {
            System.out.print("로그인 비밀번호 입력 : ");
            loginPw = Container.getScanner().nextLine().trim();
            if (loginPw.isEmpty() || loginPw.contains(" ")) {
                System.out.println("로그인 비밀번호 입력을 올바르게 해주세요.");
                continue;
            }
            if (!member.getLoginPw().equals(loginPw)) {
                System.out.println("비밀번호가 일치하지 않습니다.");
                continue;
            }
            break;
        }
        loginedMember = member;
        System.out.printf("[%s]님 환영합니다.\n", loginedMember.getLoginId());
    }

    public void doLogout() {
        if (loginedMember == null) {
            System.out.println("로그인 후에 이용해 주세요.");
            return;
        }
        System.out.println("== 로그아웃 ==");
        loginedMember = null;
    }
}
