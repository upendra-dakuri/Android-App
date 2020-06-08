package com.example.logindemo.jwt;

import androidx.core.util.Supplier;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

 public class JwtAuthenticationInterceptor implements Interceptor {
    private Supplier<String> jwtTokenSupplier;

    public JwtAuthenticationInterceptor(Supplier<String> jwtTokenSupplier) {
        this.jwtTokenSupplier = jwtTokenSupplier;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization",
                        String.format("Bearer %s", jwtTokenSupplier.get()));
        Request request = builder.build();
        return chain.proceed(request);
    }
}
