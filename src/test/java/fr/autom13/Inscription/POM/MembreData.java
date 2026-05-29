package fr.autom13.Inscription.POM;

public record MembreData(
        String login, String password,
        String firstName, String lastName,
        String gender, String birthdate,
        String address, String city,
        String zipCode, String phone,
        String email, String role, String skill
) {}
