# Messenger

Мой тестовый проект, позволяющий людям обмениваться текстовыми сообщениями. Мной было разработано клиент-серверное приложение, в котором я реализовал работу с get и post запросами, сделал обмен сообщениями через технологию websocket.

Для хранения необходимых данных в проекте используется база данных PostgreSQL. Также я реализовал сохранение сесссии пользователя в Redis.

Для работы клиентской части приложения я внедрил в проект Nginx, его конфигурационный файл есть в этом репозитории.

Для удобного запуска проекта я поместил PostgreSQL, Redis и само Spring приложение в контейнеры Docker. Реализацию этого можно посмотреть в docker-compose.yml и Dockerfile.

# Технологии
- Java
- Spring Boot
- Spring Data
- PostgreSQL
- Redis
- Docker
- Nginx
- HTML
- CSS
- JavaScript

# Реализация работы с базой данных

Работу с базой данных я реализовал с помощью Spring Data JPA. Эта технология позволила мне использовать ORM для преобразования строк sql таблицы в объекты Java. Я реализовал несколько классов сущностей
и репозитории для них.

Пример класса сущности для PostgreSQL:
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

Пример класса сущности для Redis:
```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("session")
public class Session {
    @Id
    private String id;
    private UUID userId;
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

# Docker

Файл docker-compose.yml:
```yml
services:
 postgres:
  image: postgres
  container_name: postgres
  restart: always
  environment:
   POSTGRES_PASSWORD: root
   POSTGRES_USER: admin
  ports:
   - "5432:5432"
  volumes:
   - postgres_data:/var/lib/postgresql/data
  networks:
   - messenger
 pgadmin:
  container_name: pgadmin
  image: dpage/pgadmin4
  restart: always
  environment:
   PGADMIN_DEFAULT_EMAIL: admin@admin.com
   PGADMIN_DEFAULT_PASSWORD: root
  ports:
   - "5050:80"
  volumes:
   - pgadmin:/var/lib/pgadmin
  networks:
   - messenger
 redis:
  image: redis:latest
  restart: always
  container_name: redis
  hostname: redis
  ports:
   - "6379:6379"
  volumes:
   - redis:/var/lib/redis
  networks:
   - messenger
 messenger:
  container_name: messenger
  image: messenger
  restart: always
  environment:
   SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/messenger
   SPRING_DATASOURCE_USERNAME: admin
   SPRING_DATASOURCE_PASSWORD: root
  ports:
   - "8080:8080"
  networks:
   - messenger
  depends_on:
   - postgres
   - redis
volumes:
 postgres_data:
 pgadmin:
 redis:
networks:
 messenger:
```

Dockerfile:
```dockerfile
FROM openjdk:21-jdk-slim-buster
WORKDIR /app
COPY /target/Messenger-0.0.1-SNAPSHOT.jar /app/messenger.jar
ENTRYPOINT ["java", "-jar", "messenger.jar"]
```

#Nginx

Конфигурация:
```nginx
events {
}

http {
	server{
		include /etc/nginx/mime.types;
		listen 80;
		root /var/www/messenger;

		location = /{
			alias /var/www/messenger;
			try_files /templates/index.html =404;
		}

		location = /registration{
			alias /var/www/messenger;
			try_files /templates/registration.html =404;
		}

		location = /chat{
			alias /var/www/messenger;
			try_files /templates/chat.html =404;
		}

		location = /create_chat{
			alias /var/www/messenger;
			try_files /templates/createChat.html =404;
		}
	}
}
```

# Пример работы программы

Форма авторизации:
![Снимок экрана 2024-04-13 161416](https://github.com/ArtyomKrasyuk/Messenger/assets/160497649/cd072990-e075-4cf0-99da-0038e708e826)

Чат:
![Снимок экрана 2024-04-13 161557](https://github.com/ArtyomKrasyuk/Messenger/assets/160497649/eb1c9f3f-42c2-43e4-86ea-b2e47c6d1cbe)



