package com.example.project.service;

import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private final JavaMailSender mailSender;

    public UserServiceImpl(UserRepository userRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }


    @Override
    public void registerUserByEmail(String email) {
        // Генерация уникального логина и пароля
        String login = RandomStringUtils.randomAlphanumeric(6); // 6 символов для логина
        String password = RandomStringUtils.randomAlphanumeric(6); // 6 символов для пароля



        // Создание нового пользователя и сохранение в базе данных
        User user = new User();
        user.setFio("User"); // Значение по умолчанию
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        userRepository.save(user);

        // Отправка логина и пароля на email
        sendLoginDetailsToEmail(email, login, password);
    }

    @Override
    public boolean authenticateUser(String login, String password) {
        User user = userRepository.findByLogin(login); // Предполагается, что у вас есть метод для поиска пользователя по логину
        if (user != null) {
            return user.getPassword().equals(password); // Сравнение пароля
        }
        return false; // Пользователь не найден или неверный пароль
    }


    private void sendLoginDetailsToEmail(String email, String login, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gulzat_77-daulet@mail.ru"); // Установите адрес отправителя
        message.setTo(email);
        message.setSubject("Ваш логин и пароль для входа");
        message.setText("Детали для входа:\nЛогин: " + login + "\nПароль: " + password);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            // Логирование или обработка исключения
            System.err.println("Ошибка при отправке письма: " + e.getMessage());
        }
    }

}
