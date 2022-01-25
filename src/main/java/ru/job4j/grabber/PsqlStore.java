package ru.job4j.grabber;

import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import ru.job4j.grabber.utils.Store;
import ru.job4j.html.SqlRuParse;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private Connection cn;

    public PsqlStore(Properties cfg) throws SQLException {
            try {
                Class.forName(cfg.getProperty("jdbc.driver"));
            } catch (Exception e) {
                throw new IllegalStateException();
            }
            cn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
    }

    static Properties getProperties() {
        Properties conf = new Properties();
        try (InputStream in = PsqlStore.class
                .getClassLoader().getResourceAsStream("grabber.properties")
        ) {
            conf.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conf;
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = cn.prepareStatement(
                "insert into post (name, text, link, created) values (?, ?, ?, ?)"
        )) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            statement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (Statement statement = cn.createStatement()) {
            try (ResultSet res = statement.executeQuery("select * from post")) {
                while (res.next()) {
                    posts.add(fillPost(res));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement statement = cn.prepareStatement(
                "select * from post where id = ?"
        )) {
            statement.setInt(1, id);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    post = fillPost(res);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    private Post fillPost(ResultSet res) throws SQLException {
        return new Post(res.getInt("id"),
                res.getString("name"),
                res.getString("link"),
                res.getString("text"),
                res.getTimestamp("created").toLocalDateTime()
        );
    }

    public static void main(String[] args) throws SQLException, IOException, ParseException {
        PsqlStore store = new PsqlStore(getProperties());
        SqlRuParse ruParse = new SqlRuParse(new SqlRuDateTimeParser());
        ruParse.list("https://www.sql.ru/forum/job-offers/")
                .stream().forEach(post -> store.save(post));

        store.getAll().stream().forEach(post -> System.out.println(String.format(
                "id %s name %s link %s desc %s date %s",
                post.getId(),
                post.getTitle(),
                post.getLink(),
                post.getDescription(),
                post.getCreated()
        )));

        Post post = store.findById(2);
        System.out.println(String.format(
                "id %s name %s link %s desc %s date %s",
                post.getId(),
                post.getTitle(),
                post.getLink(),
                post.getDescription(),
                post.getCreated()
        ));
    }
}
