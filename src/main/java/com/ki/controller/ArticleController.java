package com.ki.controller;

import com.ki.articleManager.Container;
import com.ki.dto.Article;
import com.ki.service.ArticleService;

import java.sql.Connection;
import java.util.List;


public class ArticleController extends Controller {
    private Connection conn;
    private ArticleService articleService;

    public ArticleController(Connection conn) {
        this.conn = conn;
        ArticleService articleService = new ArticleService(conn);
    }

    public void doWrite() {
        System.out.println("== 게시글 작성 ==");
        System.out.print("제목 : ");
        String title = Container.getScanner().nextLine().trim();
        System.out.print("내용 : ");
        String body = Container.getScanner().nextLine().trim();

        int id = articleService.doWrite(title, body, loginedMember.getId());

        System.out.printf("%d번 게시글이 작성되었습니다.", id);
    }

    public void showList(String cmd) {
        System.out.println("== 게시글 목록 ==");

        if (articleService.getSize() == 0) {
            System.out.println("작성된 게시글이 없습니다.");
            return;
        }

        String searchWord = cmd.substring("article list".length()).trim();

        List<Article> articles = articleService.forPrintArticles(searchWord);

        System.out.println("번호  /   작성일   /   작성자   /   제목   /    내용   ");
        System.out.println("=".repeat(60));
        for (Article article : articles) {
            System.out.printf("%d   /  %s   /   %s   /   %s  /   %s   \n", article.getId(), article.getRegDate(), article.getName(), article.getTitle(), article.getBody());
        }
    }

    public void showDetail(String cmd) {
        System.out.println("== 게시글 상세보기 ==");

        String[] cmdBits = cmd.split(" ");

        int id;

        try {
            id = Integer.parseInt(cmdBits[2]);
        } catch (Exception e) {
            System.out.println("숫자를 입력하세요.");
            return;
        }

        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.printf("%d번 게시글은 없습니다.\n", id);
            return;
        }
        if (loginedMember.getId() != article.getMemberId()) {
            System.out.println("해당 글에 대한 권한이 없습니다.");
            return;
        }

        System.out.printf("번호 : %d", article.getId());
        System.out.printf("작성일자 : %s", article.getRegDate());
        System.out.printf("수정일자 : %s", article.getUpdateDate());
        System.out.printf("작성자 : %s", article.getName());
        System.out.printf("제목 : %s", article.getTitle());
        System.out.printf("내용 : %s", article.getBody());
    }

    public void doDelte(String cmd) {
        String[] cmdBits = cmd.split(" ");

        int id;

        try {
            id = Integer.parseInt(cmdBits[2]);
        } catch (Exception e) {
            System.out.println("숫자를 입력하세요.");
            return;
        }

        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.printf("%d번 게시글은 없습니다.\n", id);
            return;
        }
        if (loginedMember.getId() != article.getMemberId()) {
            System.out.println("해당 글에 대한 권한이 없습니다.");
            return;
        }

        System.out.println("== 게시글 삭제 ==");

        int foundArticleId = articleService.doDelete(id);

        System.out.printf("%d번 게시글이 삭제되었습니다.\n", foundArticleId);

    }

    public void doModify(String cmd) {
        String[] cmdBits = cmd.split(" ");

        int id;

        try {
            id = Integer.parseInt(cmdBits[2]);
        } catch (Exception e) {
            System.out.println("숫자를 입력하세요.");
            return;
        }

        Article article = articleService.getArticleById(id);

        if (article == null) {
            System.out.printf("%d번 게시글은 없습니다.\n", id);
            return;
        }
        if (loginedMember.getId() != article.getMemberId()) {
            System.out.println("해당 글에 대한 권한이 없습니다.");
            return;
        }

        System.out.println("== 게시글 수정 ==");
        System.out.print("제목 : ");
        String newTitle = Container.getScanner().nextLine().trim();
        System.out.print("내용 : ");
        String newBody = Container.getScanner().nextLine().trim();


        int foundArticleId = articleService.doModify(id, newTitle, newBody);

        System.out.printf("%d번 게시글이 수정되었습니다.\n", foundArticleId);
    }
}
