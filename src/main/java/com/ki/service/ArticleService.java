package com.ki.service;

import com.ki.dao.ArticleDao;
import com.ki.dto.Article;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class ArticleService {

    private ArticleDao articleDao;

    public ArticleService(Connection conn) {
        this.articleDao = new ArticleDao(conn);
    }

    public int doWrite(String title, String body, int memberId) {
        return articleDao.doWrite(title, body, memberId);
    }

    public List<Article> getArticles() {
        return articleDao.getArticles();
    }

    public Article getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public int doDelete(int id) {
        return articleDao.doDelete(id);
    }

    public int doModify(int id, String newTitle, String newBody) {
        return articleDao.doModify(id, newTitle, newBody);
    }

    public int getSize() {
        return articleDao.getSize();
    }

    public List<Article> forPrintArticles(String searchWord) {
        List<Article> articles = new ArrayList<>();

        if (!searchWord.isEmpty() || searchWord != null) {
            System.out.printf("검색어 : %s", searchWord);
        }

        for (Article article : articleDao.getArticles()) {
            if (article.getTitle().contains(searchWord)) {
                articles.add(article);
            }
        }
        if (articles.isEmpty()) {
            System.out.println("해당 게시글이 없습니다.");
            return articles;
        }
        return articles;
    }
}
