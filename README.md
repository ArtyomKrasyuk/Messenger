# Messenger

Мой тестовый проект, позволяющий людям обмениваться текстовыми сообщениями. В процессе разработки данного проекта я реализовал работу с get и post запросами, сделал обмен сообщениями через технологию websocket.
Также для хранения необходимых данных в проекте используется база данных MySQL. 

# Технологии
- Java
- Spring Boot
- MySQL
- HTML
- CSS
- JavaScript

# Реализация работы с базой данных

Работу с базой данных я реализовал с помощью Spring Data JPA. Эта технология позволила мне использовать ORM для преобразования строк sql таблицы в объекты Java. Я реализовал несколько классов сущностей
и репозитории для них.

Пример класса сущности:
```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private UUID userid;
    @Column(unique = true)
    private String phone;
    private String username;
    private String password;
}
```

Пример репозитория:

```java
public interface UserRepository extends CrudRepository<User, UUID> {
    User findByPhoneAndPassword(String phone, String password);
    User findByPhone(String phone);
    User findByUserid(UUID userid);
    boolean existsByUserid(UUID userid);
}
```

# Пример работы программы

Форма авторизации:
![Снимок экрана 2024-04-13 161416](https://github.com/ArtyomKrasyuk/Messenger/assets/160497649/cd072990-e075-4cf0-99da-0038e708e826)

Чат:
![Снимок экрана 2024-04-13 161557](https://github.com/ArtyomKrasyuk/Messenger/assets/160497649/eb1c9f3f-42c2-43e4-86ea-b2e47c6d1cbe)



