package com.ki.service;

import com.ki.dao.ArticleDao;
import com.ki.dto.Article;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleService {
    private ArticleDao articleDao;

    public ArticleService(Connection conn) {
        this.articleDao = new ArticleDao(conn);
    }

    public int doWrite(String title, String body) {
        return articleDao.doWrite(title, body);
    }

    public Map<String, Object> getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public void doModify(int id, String newTitle, String newBody) {
        articleDao.doModify(id, newTitle, newBody);
    }

    public int doDelete(int id) {
        return articleDao.doDelete(id);
    }

    public List<Article> getArticles() {
        return articleDao.getArticles();
    }
}

