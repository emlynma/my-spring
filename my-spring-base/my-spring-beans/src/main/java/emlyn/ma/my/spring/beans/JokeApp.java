package emlyn.ma.my.spring.beans;

import emlyn.ma.my.spring.joke.launcher.JokeLauncher;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class JokeApp {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        new XmlBeanDefinitionReader(beanFactory).loadBeanDefinitions("spring.xml");

        JokeLauncher jokeLauncher = beanFactory.getBean("jokeLauncher", JokeLauncher.class);

        jokeLauncher.run();

    }

}
