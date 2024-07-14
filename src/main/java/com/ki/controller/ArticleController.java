package com.ki.controller;

import com.ki.articleManager.Container;

import com.ki.dto.Article;
import com.ki.util.DBUtil;
import com.ki.util.SecSql;
import com.ki.util.Util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ArticleController extends Controller {
    private Connection conn;

    public ArticleController(Connection conn) {
        this.conn = conn;
    }

    public void doWrite() {
        System.out.println("== 게시글 작성 ==");
        System.out.print("제목 : ");
        String title = Container.getScanner().nextLine();
        System.out.print("내용 : ");
        String body = Container.getScanner().nextLine();

        SecSql sql = new SecSql();

        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("SET updateDate = NOW(),");
        sql.append("title = '" + title + "',");
        sql.append("`body` = '" + body + "';");

        int id = DBUtil.insert(conn, sql);

        System.out.printf("%d번 게시글이 작성되었습니다.\n", id);
    }

    public void showList() {
        System.out.println("== 게시글 목록 ==");

        SecSql sql = new SecSql();

        List<Article> articles = new ArrayList<Article>();

        sql.append("SELECT * FROM article ORDER BY DESC;");

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }

        if (articles.isEmpty()) {
            System.out.println("작성된 게시글이 없습니다.");
            return;
        }

        System.out.println("  번호  /    작성일   /     제목     /     내용    ");
        System.out.println("=".repeat(60));
        for (Article article : articles) {
            if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                System.out.printf("   %d   /    %s    /       %s       /      %s     \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
            } else
                System.out.printf("   %d   /    %s     /      %s      /      %s     \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
        }
    }

    public void showDetail(String cmd) {
        System.out.println("== 게시글 상세보기 ==");

        String[] cmdBits = cmd.split(" ");

        int id;

        try {
            id = Integer.parseInt(cmdBits[2]);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("숫자를 입력하세요");
            return;
        }

        SecSql sql = new SecSql();

        List<Article> articles = new ArrayList<Article>();

        sql.append("SELECT * FROM article WHERE id = " + id + ";");

        Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

        if (articleMap.isEmpty()) {
            System.out.printf("%d번 게시글은 없습니다.\n", id);
            return;
        }

        Article article = new Article(articleMap);

        System.out.printf("번호 : %d", article.getId());
        System.out.printf("작성일 : %s", article.getRegDate());
        System.out.printf("수정일 : %s", article.getUpdateDate());
        System.out.printf("제목 : %s", article.getTitle());
        System.out.printf("내용 : %s", article.getBody());
    }

    public void doModify(String cmd) {
        System.out.println("== 게시글 수정 ==");

        String[] cmdBits = cmd.split(" ");

        int id;

        try {
            id = Integer.parseInt(cmdBits[2]);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("숫자를 입력하세요");
            return;
        }

        SecSql sql = new SecSql();

        List<Article> articles = new ArrayList<Article>();

        sql.append("SELECT * FROM article WHERE id = " + id + ";");

        Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

        if (articleMap.isEmpty()) {
            System.out.printf("%d번 게시글은 없습니다.\n", id);
            return;
        }

        Article article = new Article(articleMap);

        System.out.printf("기존제목 : %s", article.getTitle());
        System.out.printf("기존내용 : %s", article.getBody());
        System.out.println("제목 : ");
        String newTitle = Container.getScanner().nextLine();
        System.out.println("내용 : ");
        String newBody = Container.getScanner().nextLine();

        SecSql sql2 = new SecSql();

        sql.append("UPDATE article");
        sql.append("SET updateDate = NOW(),");
        if (!newTitle.isEmpty()) {
            sql.append("title = '" + newTitle + "',");
        }
        if (!newBody.isEmpty()) {
            sql.append("`body` = '" + newBody + "',");
        }
        sql.append("WHERE id = " + id + ";");

        DBUtil.update(conn, sql2);

        System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
    }
    public void doDelete(String cmd) {
        System.out.println("== 게시글 삭제 ==");

        String[] cmdBits = cmd.split(" ");

        int id;

        try {
            id = Integer.parseInt(cmdBits[2]);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("숫자를 입력하세요");
            return;
        }

        SecSql sql = new SecSql();

        List<Article> articles = new ArrayList<Article>();

        int foundArticleId = 0;

        sql.append("DELETE * FROM article WHERE id = " + id + ";");

        foundArticleId = DBUtil.delete(conn, sql);

        if (foundArticleId == 0) {
            System.out.printf("%d번 게시글은 없습니다.\n", id);
            return;
        }
        System.out.printf("%d번 게시글은 삭제되었습니다.\n",foundArticleId);
    }
}


