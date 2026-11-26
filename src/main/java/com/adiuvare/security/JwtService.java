package com.adiuvare.security;

import com.adiuvare.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {


    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String generarToken(Usuario usuario) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", usuario.getIdUsuario());
        extraClaims.put("tipo", usuario.getTipoUsuario());
        extraClaims.put("nombre", usuario.getNombre());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(usuario.getCorreo())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}