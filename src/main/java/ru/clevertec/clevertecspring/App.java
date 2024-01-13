package ru.clevertec.clevertecspring;

import org.apache.catalina.LifecycleException;
import ru.clevertec.clevertecspring.util.TomcatStarter;

import java.io.IOException;

/**
 * Main-класс - точка входа в приложение
 */
public class App {

    public static void main(String[] args) throws LifecycleException, IOException {
        TomcatStarter.start();
    }
}
