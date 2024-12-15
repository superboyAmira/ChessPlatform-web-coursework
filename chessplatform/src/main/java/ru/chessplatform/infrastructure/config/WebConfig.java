package ru.chessplatform.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDateTime.class, new LocalDateTimeFormatter());
    }

    private static class LocalDateTimeFormatter implements org.springframework.format.Formatter<LocalDateTime> {

        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        @Override
        public LocalDateTime parse(String text, java.util.Locale locale) {
            return LocalDateTime.parse(text, formatter);
        }

        @Override
        public String print(LocalDateTime object, java.util.Locale locale) {
            return object.format(formatter);
        }
    }
}
