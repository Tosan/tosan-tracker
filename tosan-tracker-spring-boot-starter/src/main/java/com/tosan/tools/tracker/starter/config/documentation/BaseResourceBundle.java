package com.tosan.tools.tracker.starter.config.documentation;

import java.util.List;
import java.util.Locale;

/**
 * @author M.khoshnevisan
 * @since 8/13/2023
 */
public interface BaseResourceBundle {
    String DEFAULT_LANG = "fa";
    Locale DEFAULT_LOCALE = new Locale(DEFAULT_LANG);
    List<String> LOCALES = List.of(DEFAULT_LANG);
    String BASE_PATH = "documentation/";
}
