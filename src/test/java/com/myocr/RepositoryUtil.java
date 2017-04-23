package com.myocr;

import org.springframework.data.repository.CrudRepository;

import java.util.Arrays;

public class RepositoryUtil {
    @SafeVarargs
    public static void deleteAll(CrudRepository<?, Long>... repositories) {
        Arrays.asList(repositories).forEach(CrudRepository::deleteAll);
    }
}
