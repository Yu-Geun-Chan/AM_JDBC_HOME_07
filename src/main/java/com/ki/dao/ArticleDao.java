package com.ki.dao;

import com.ki.dto.Article;
import com.ki.util.DBUtil;
import com.ki.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {
    private Connection conn;

    public ArticleDao(Connection conn) {
        this.conn = conn;
    }

    public int doWrite(String title, String body, int memberId) {
        SecSql sql = new SecSql();

        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(),");
        sql.append("updateDate = NOW(),");
        sql.append("memberId = ?", memberId);
        sql.append("title = ?,", title);
        sql.append("`body` = ?;", body);

        return DBUtil.insert(conn, sql);
    }

    public List<Article> getArticles() {
        SecSql sql = new SecSql();

        sql.append("SELECT A.* FROM article A");
        sql.append("INNER JOIN `member` M");
        sql.append("ON A.memberId = M.id");
        sql.append("ORDER BY DESC;");

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

        List<Article> articles = new ArrayList<>();

        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }
        return articles;
    }

    public Article getArticleById(int id) {
        SecSql sql = new SecSql();

        sql.append("SELECT * FROM article A");
        sql.append("INNER JOIN `member` M");
        sql.append("ON A.memberId = M.id");
        sql.append("WHERE A.id =?;", id);

        Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

        if (articleMap.isEmpty()) {
            return null;
        }

        return new Article(articleMap);
    }

    public int doDelete(int id) {
        SecSql sql = new SecSql();

        sql.append("DELETE * FROM article WHERE id = ?;", id);

        return DBUtil.delete(conn, sql);
    }

    public int doModify(int id, String newTitle, String newBody) {
        SecSql sql = new SecSql();

        sql.append("UPDATE article");
        sql.append("SET updateDate = NOW(),");
        if (!newTitle.isEmpty()) {
            sql.append("title = ?,", newTitle);
        }
        if (!newBody.isEmpty()) {
            sql.append("`body` = ?,", newBody);
        }
        sql.append("WHERE id = ?;", id);

        return DBUtil.update(conn, sql);
    }

    public int getSize() {
        return getArticles().size();
    }
}
