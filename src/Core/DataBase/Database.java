package Core.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;
    private Connection connection;

    public static Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    public Database() {
        try {
           // connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ngi2", "root", "shirin1234");
            connection = DriverManager.getConnection("jdbc:mysql://185.190.22.134:3301/ngi", "root", "Shirin@8723");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String query = "CREATE TABLE IF NOT EXISTS `project` (\n" +
                    "  `id-project` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `title` VARCHAR(45) NOT NULL,\n" +
                    "  `description` VARCHAR(100) NULL,\n" +
                    "  PRIMARY KEY (`id-project`));";

            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating project table: " + e.getMessage());
        }

        try {
            String query = "CREATE TABLE IF NOT EXISTS `user` (\n" +
                    "`id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "`name` VARCHAR(45) NOT NULL,\n" +
                    "`email` VARCHAR(45) NOT NULL,\n" +
                    "`password` VARCHAR(45) NOT NULL,\n" +
                    "`role` INT NOT NULL,\n" +
                    "PRIMARY KEY (`id`),\n" +
                    "UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n" +
                    "UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);";

            connection.createStatement().executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException("Error creating users table: " + e.getMessage());
        }

        try {
            String createUserProjectTableQuery = "CREATE TABLE IF NOT EXISTS `user_project` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `user_id` INT NOT NULL,\n" +
                    "  `project_id` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  FOREIGN KEY (`project_id`) REFERENCES `project` (`id-project`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ");";

            connection.createStatement().executeUpdate(createUserProjectTableQuery);

        } catch (SQLException e) {
            throw new RuntimeException("Error creating user_project table: " + e.getMessage());
        }
        try {
            String createIssuesTableQuery = "CREATE TABLE IF NOT EXISTS `issues` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `title` VARCHAR(255) NOT NULL,\n" +
                    "  `description` TEXT,\n" +
                    "  `status` INT NOT NULL DEFAULT 0,\n" + // Store status as integer with default value 0 (TODO)
                    "  `createDate` DATETIME NOT NULL,\n" +
                    "  `updateDate` DATETIME,\n" +
                    "  `type` INT NOT NULL,\n" + // Store type as integer
                    "  `priority` INT NOT NULL,\n" + // Store priority as integer
                    "  `tags` TEXT,\n" +
                    "  `project_id` INT NOT NULL,\n" +
                    "   PRIMARY KEY (`id`)\n" +
                    ");";

            connection.createStatement().executeUpdate(createIssuesTableQuery);

        } catch (SQLException e) {
            throw new RuntimeException("Error creating issues table: " + e.getMessage());
        }

    }

    public static void setInstance(Database instance) {
        Database.instance = instance;
    }

    public Connection getConnection() {
        return connection;
    }

}

