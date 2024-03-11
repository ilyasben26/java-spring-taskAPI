package com.ilyasben.taskAPI.security;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 70000;
    public static final String JWT_SECRET = "jwt_secret"; // TODO: store this in a more secure way (in an env variable)
}
