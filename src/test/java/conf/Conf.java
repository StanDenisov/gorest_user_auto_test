package conf;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import entity.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Properties;

public class Conf {
    private static Properties PROPERTIES;
    private static ArrayList<User> USERS;
    static {
        try (FileInputStream fileInputStream = new FileInputStream("src/test/resources/conf.properties")) {
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        ArrayList<User> users;
        try (Reader reader = new FileReader("src/test/resources/user.json")) {
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            // Convert JSON File to Java Object
            USERS = gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * метод для возврата строки со значением из файла с настройками
     */
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
    public static ArrayList<User> getUsersJson() {
        return USERS;
    }
}