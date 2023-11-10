package ru.skypro.homework.service;

import ru.skypro.homework.dto.authdto.Register;
import ru.skypro.homework.dto.authdto.Role;
import ru.skypro.homework.dto.userdto.NewPassDto;

/**
 * Interface with methods registration, authorization of password changes
 */
public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register, Role role);

    void updatePassword(NewPassDto newPassDto);
}
