package com.cpimgroup.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class Constantes {
    @Value("${jwt.token.secret}")
    public static String SECRET;
}
