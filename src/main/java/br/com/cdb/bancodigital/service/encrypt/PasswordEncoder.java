package br.com.cdb.bancodigital.service.encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    public static String encrypt(String senha){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);
    }

    public static boolean matches (String senha, String senhaCripto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(senha,senhaCripto);
    }
}
