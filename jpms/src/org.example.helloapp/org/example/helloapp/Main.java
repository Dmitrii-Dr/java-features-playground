package org.example.helloapp;

// We can import this because our module will 'require' the provider module.
import com.example.greeting.provider.GreetingService;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the service from the other module
        GreetingService service = new GreetingService();
        String message = service.getMessage();

        // Print the message
        System.out.println(message);

        //System.out.println(GreetingPhraseStorage.getPhrase());
        // This will lead to compilation error 
        // error: package com.example.greeting.provider.internal is not visible
    }
}