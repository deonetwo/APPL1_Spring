package com.jtk.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
    public static void main(String[] args) {
    	ApplicationContext context = new ClassPathXmlApplicationContext("resources/beans.xml");
    	AppInterface app = (AppInterface) context.getBean("app");
        
        app.userNotLogin();
    }
}