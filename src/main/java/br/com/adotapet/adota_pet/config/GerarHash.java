package br.com.adotapet.adota_pet.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarHash {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senha = "123456";
        String hash = encoder.encode(senha);

        System.out.println("Hash: " + hash);
        System.out.println("Senha válida: " + encoder.matches(senha, hash));
    }
}