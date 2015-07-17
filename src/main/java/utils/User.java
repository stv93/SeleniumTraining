package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.DeletePage;
import pages.SignUpPage;

import java.util.function.Consumer;

public class User {

    private boolean isExisting;
    private String login;
    private String password;
    private String name;
    private String email;

    private User() {
        isExisting = false;
        this.login = nameGenerator();
        this.password = "password";
        this.name = login;
        this.email = login + "@testuser.com";
    }

    public static String getRandomSubstringOf(String name) {
        int index = (int) (Math.random() * name.length() - 4);
        name = name.substring(index, index + 2);
        return name;
    }

    public static User generateMockUser() {
        return new User();
    }

    public static User generateEmptyFieldsUser() {
        User user = new User();
        user.login = "";
        user.password = "";
        user.name = "";
        user.email = "";
        return user;
    }

    public static User generateSpaceFieldUser() {
        User user = new User();
        user.login = "    ";
        user.password = "     ";
        user.name = "     ";
        user.email = "     ";
        return user;
    }

    public static User setLoginAndPassword(String login, String password) {
        User user = new User();
        user.login = login;
        user.password = password;
        return user;
    }

    private String nameGenerator() {
        char[] abcArray = new char[26];
        for (int i = 0; i < abcArray.length; i++) {
            abcArray[i] = (char) ('a' + i);
        }
        String name = null;
        for (int i = 0; i < 15; i++) {
            name = name + abcArray[(int) (Math.random() * abcArray.length - 1)];
        }
        return name;
    }

    public String getName() {
        return name;
    }

    public static User setName(String name) {
        User user = new User();
        user.name = name;
        return user;
    }

    public String getEmail() {
        return email;
    }

    public static User setEmail(String email) {
        User user = new User();
        user.email = email;
        return user;
    }

    public String getLogin() {
        return login;
    }

    public static User setLogin(String login) {
        User user = new User();
        user.login = login;
        return user;
    }

    public String getPassword() {
        return password;
    }

    public static User setPassword(String password) {
        User user = new User();
        user.password = password;
        return user;
    }

    public boolean isExisting() {
        return isExisting;
    }

    private boolean executeInDriver(Consumer<WebDriver> consumer) {
        WebDriver wd = null;
        try {
            wd = new FirefoxDriver();
            consumer.accept(wd);
        } catch (Exception e) {
            return false;
        } finally {
            if (wd != null) {
                wd.quit();
            }
        }
        return true;
    }

    public User register() {
        if (!isExisting) {
            isExisting = executeInDriver(wd -> new SignUpPage(wd).get().signUpAs(User.this));
        }
        return this;
    }

    public User delete(boolean force) {
        if (isExisting || force) {
            isExisting = !executeInDriver(wd -> new DeletePage(wd, User.this.getName()).get().deleteUser());
        }
        return this;
    }

    public User delete() {
        return delete(false);
    }

    @Override
    public String toString() {
        return String.format("{User: {login: '%s', password: '%s', name: '%s', email: '%s', existing: '%s'}}", login, password, name, email, isExisting);
    }
}

