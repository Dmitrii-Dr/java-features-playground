package com.example.greeting.provider;

import com.example.greeting.provider.internal.GreetingPhraseStorage;

public class GreetingService {
    public String getMessage() {
        return GreetingPhraseStorage.getPhrase();
    }
}