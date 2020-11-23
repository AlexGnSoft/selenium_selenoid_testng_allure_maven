package com.carespeak.domain.entities.common;

import java.util.NoSuchElementException;

public enum Language {

    EN("English"),
    DU("Dutch"),
    CH("Chinese");

    private String languageName;

    private Language(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public static Language getLanguage(String language) {
        for (Language lang : values()) {
            if (lang.languageName.toLowerCase().contains(language.trim().toLowerCase())) {
                return lang;
            }
        }
        throw new NoSuchElementException("Cannot find Language object for string '" + language + "'");
    }


}
