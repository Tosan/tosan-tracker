package com.tosan.tools.tracker.starter.config.documentation;

import com.tosan.tools.tracker.starter.config.TrackConfig;

import java.util.*;

/**
 * @author M.khoshnevisan
 * @since 8/5/2023
 */
public class DocumentationResourceBundle implements BaseResourceBundle {

    private static final String BASE_NAME = BASE_PATH + "documentation";
    private final Map<Locale, Map<String, String>> allDocumentations;
    private final TrackConfig trackConfig;

    public DocumentationResourceBundle(TrackConfig trackConfig) {
        this.trackConfig = trackConfig;
        allDocumentations = new HashMap<>();
        List<Locale> locales = new ArrayList<>();
        LOCALES.stream().map(Locale::new).forEach(locales::add);
        if (trackConfig.getLocale() != null) {
            locales.add(trackConfig.getLocale());
        }
        for (Locale locale : locales) {
            ResourceBundle bundle = getResourceBundle(locale);
            if (bundle != null) {
                Map<String, String> documentation = new HashMap<>();
                Collections.list(bundle.getKeys()).forEach(key -> documentation.put(key.toLowerCase(), bundle.getString(key)));
                allDocumentations.put(bundle.getLocale(), documentation);
            }
        }
    }

    private ResourceBundle getResourceBundle(Locale locale) {
        try {
            return ResourceBundle.getBundle(BASE_NAME, locale);
        } catch (MissingResourceException exception) {
            return null;
        }
    }

    public Map<Locale, Map<String, String>> getAllDocumentations() {
        return Collections.unmodifiableMap(allDocumentations);
    }


    public Map<String, String> geDocumentation() {
        if (trackConfig.getLocale() != null) {
            return getAllDocumentations().get(trackConfig.getLocale());
        }
        return getAllDocumentations().get(DEFAULT_LOCALE);
    }
}
