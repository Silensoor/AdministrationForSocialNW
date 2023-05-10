package com.example.admin.services.impl;

import com.example.admin.dto.request.PersonRq;
import com.example.admin.mappers.PersonMapper;
import com.example.admin.model.*;
import com.example.admin.model.repository.*;
import com.example.admin.services.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.stereotype.Service;

import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.sql.Timestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonSettingRepository personSettingRepository;
    private final BlockHistoryRepository blockHistoryRepository;
    private final FriendsShipsRepository friendsShipsRepository;
    private final PostRepository postRepository;
    private final Post2TagRepository post2TagRepository;
    private final CommentRepository commentRepository;
    private final PostFilesRepository postFilesRepository;
    private final LikeRepository likeRepository;
    private final NotificationRepository notificationRepository;
    private static final CopyOnWriteArrayList<Person> listPerson = new CopyOnWriteArrayList<>();

    @Override
    public void createPersonBySettings(PersonRq personRq) {
        try {
            if (personRq.getCount() != null) {
                PersonRq personRq1 = getPersonRqByDate(personRq);


                if (personRq.getIsPhoto()) {
                    personRq1.setPhoto("https://storage.yandexcloud.net/socnet37/users_photo/73f6bf23-78a0-4a8e-b657-db365df178dd/123.gif");
                }
                if (personRq.getIsDeleted()) {
                    personRq1.setIsDeleted(true);
                }


                for (int i = 0; i < personRq.getCount(); i++) {
                    Person person = PersonMapper.INSTANCE.toDTO(getPersonRqByDate(personRq));
                    person.setEmail(generateRandomEmail());
                    Long id = personRepository.save(person);
                    personSettingRepository.createPersonSetting(id);
                    person.setId(id);
                    listPerson.add(person);

                }
                return;
            }
            Person person = getPerson(personRq);
            Long id = personRepository.save(person);
            personSettingRepository.createPersonSetting(id);
            person.setId(id);
            listPerson.add(person);

        } catch (Exception e) {
            log.debug(e.getMessage());
        }


    }

    @Override
    public void createPerson() {
        try {
            Person person = getPersonNew();
            Long id = personRepository.save(person);
            personSettingRepository.createPersonSetting(id);
            person.setId(id);
            listPerson.add(person);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        try {
            if (id == null) {
                for (Person person : listPerson) {
                    deleteAllPostTags(person);
                }
                listPerson.clear();
            } else {
                Person person = personRepository.findById(id);
                deleteAllPostTags(person);
                listPerson.removeIf(person1 -> person1.getId().equals(id));
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    @Override
    public void registerUser(Integer countCircle) {
        try {


            for (int i = 0; i < countCircle; i++) {
                HttpClient client = HttpClient.newHttpClient();

                // Отправка GET-запроса и получение ответа
                HttpRequest getRequest = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8086/api/v1/auth/captcha"))
                        .header("Content-Type", "application/json")
                        .build();
                HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

                // Обработка JSON-ответа
                int count = Integer.parseInt(getResponse.body().replaceAll("\"", "").split(":")[1].split(",")[0]);


                // Генерация случайных данных
                String email = generateRandomEmail();
                String firstName = generateFirstName();
                String lastName = generateLastName();
                Integer password = generatePassword();

                // Создание JSON-объекта с данными для POST-запроса
                String json = String.format("{\"firstName\": \"%s\", \"lastName\": \"%s\", \"passwd1\": \"%s\", \"passwd2\": \"%s\", \"email\": \"%s\", \"code\": %d, \"codeSecret\": %d}",
                        firstName, lastName, password, password, email, count, count);

                // Отправка POST-запроса
                HttpRequest postRequest = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8086/api/v1/account/register"))
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .header("Content-Type", "application/json")
                        .build();
                HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

                // Обработка ответа
                if (postResponse.statusCode() == 200) {
                    System.out.println("Регистрация прошла успешно!");
                } else {
                    System.out.println("Произошла ошибка при регистрации.");
                }
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    @Override
    public void changeUser(PersonRq personRq) {
        Long id = personRq.getId();
        for (Person person : listPerson) {
            if (person.getId().equals(id)) {
                if (personRq.getFirstName() != null && !personRq.getFirstName().equals("")) {
                    person.setFirstName(personRq.getFirstName());
                    personRepository.updateFirstName(personRq.getFirstName(), id);
                }
                if (personRq.getLastName() != null && !personRq.getLastName().equals("")) {
                    person.setLastName(personRq.getLastName());
                    personRepository.updateLastName(personRq.getLastName(), id);
                }
                if (personRq.getPhone() != null && !personRq.getPhone().equals("")) {
                    person.setPhone(personRq.getPhone());
                    personRepository.updatePhone(personRq.getPhone(), id);
                }
                if (personRq.getTelegramId() != null) {
                    person.setTelegramId(Long.valueOf(personRq.getTelegramId()));
                    personRepository.updateTelegramId(personRq.getTelegramId(), id);
                }
                if (personRq.getBirthDate() != null && !personRq.getBirthDate().equals("")) {
                    Timestamp dateByString = Timestamp.valueOf(getDateByString(personRq.getBirthDate()));
                    person.setBirthDate(dateByString);
                    personRepository.updateBirthDate(dateByString, id);
                }
                if (personRq.getLastOnlineTime() != null && !personRq.getLastOnlineTime().equals("")) {
                    Timestamp dateByString = Timestamp.valueOf(getDateByString(personRq.getLastOnlineTime()));
                    person.setLastOnlineTime(dateByString);
                    personRepository.updateLastOnlineTime(dateByString, id);
                }
                if (personRq.getRegDate() != null && !personRq.getRegDate().equals("")) {
                    Timestamp dateByString = Timestamp.valueOf(getDateByString(personRq.getRegDate()));
                    person.setRegDate(dateByString);
                    personRepository.updateRegDate(dateByString, id);
                }
                if (personRq.getCountry() != null && !personRq.getCountry().equals("")) {
                    person.setCountry(person.getCountry());
                    personRepository.updateCountry(personRq.getCountry(), id);
                }
                if (personRq.getCity() != null && !personRq.getCity().equals("")) {
                    person.setCity(person.getCity());
                    personRepository.updateCity(personRq.getCity(), id);
                }
                if (personRq.getCity() != null && !personRq.getCity().equals("")) {
                    person.setCity(personRq.getCity());
                    personRepository.updateCity(personRq.getCity(), id);
                }
                String photo;
                if (personRq.getIsPhoto()) {
                    photo = "https://storage.yandexcloud.net/socnet37/users_photo/73f6bf23-78a0-4a8e-b657-db365df178dd/123.gif";
                }else {
                    photo = generateString(5);
                }
                person.setPhoto(photo);
                personRepository.updatePhoto(photo, id);
                person.setIsDeleted(personRq.getIsDeleted());
                personRepository.updateDeleted(personRq.getIsDeleted(), id);
            } else {
                if (personRq.getFirstName() != null && !personRq.getFirstName().equals("")) {
                    personRepository.updateFirstName(personRq.getFirstName(), id);
                }
                if (personRq.getLastName() != null && !personRq.getLastName().equals("")) {
                    personRepository.updateLastName(personRq.getLastName(), id);
                }
                if (personRq.getPhone() != null && !personRq.getPhone().equals("")) {
                    personRepository.updatePhone(personRq.getPhone(), id);
                }
                if (personRq.getTelegramId() != null) {
                    personRepository.updateTelegramId(personRq.getTelegramId(), id);
                }
                if (personRq.getBirthDate() != null && !personRq.getBirthDate().equals("")) {
                    Timestamp dateByString = Timestamp.valueOf(getDateByString(personRq.getBirthDate()));
                    personRepository.updateBirthDate(dateByString, id);
                }
                if (personRq.getLastOnlineTime() != null && !personRq.getLastOnlineTime().equals("")) {
                    Timestamp dateByString = Timestamp.valueOf(getDateByString(personRq.getLastOnlineTime()));
                    personRepository.updateLastOnlineTime(dateByString, id);
                }
                if (personRq.getRegDate() != null && !personRq.getRegDate().equals("")) {
                    Timestamp dateByString = Timestamp.valueOf(getDateByString(personRq.getRegDate()));
                    personRepository.updateRegDate(dateByString, id);
                }
                if (personRq.getCountry() != null && !personRq.getCountry().equals("")) {
                    personRepository.updateCountry(personRq.getCountry(), id);
                }
                if (personRq.getCity() != null && !personRq.getCity().equals("")) {
                    personRepository.updateCity(personRq.getCity(), id);
                }
                if (personRq.getCity() != null && !personRq.getCity().equals("")) {
                    personRepository.updateCity(personRq.getCity(), id);
                }
                String photo;
                if (personRq.getIsPhoto()) {
                    photo = "https://storage.yandexcloud.net/socnet37/users_photo/73f6bf23-78a0-4a8e-b657-db365df178dd/123.gif";
                }else {
                    photo = generateString(5);
                }
                personRepository.updatePhoto(photo, id);
                personRepository.updateDeleted(personRq.getIsDeleted(), id);
            }
        }

    }

    @Override
    public List<Person> getListPerson() {
        return listPerson;
    }

    private Person getPersonNew() {
        Person person = setInfo(new Person());
        person.setBirthDate(Timestamp.from(Instant.now()));
        person.setEmail(generateRandomEmail());
        person.setFirstName(generateFirstName());
        person.setIsDeleted(false);
        person.setLastName(generateLastName());
        person.setPhone(generatePhone());
        person.setRegDate(Timestamp.from(Instant.now()));
        person.setCity(generateCity());
        person.setCountry(generateCountry());
        return person;

    }

    private List<Post> deletePersonSettingsReturnPosts(Person person) {
        List<Friendships> allFriendships = friendsShipsRepository.findAllFriendships(person.getId());
        allFriendships.stream().map(Friendships::getId).forEach(friendsShipsRepository::deleteFriendUsing);
        personSettingRepository.deleteSettingByPersonId(person.getPersonSettingsId());
        return postRepository.findPostsByUserId(person.getId());
    }

    private Person getPerson(PersonRq personRq) {
        PersonRq personRqByDate = getPersonRqByDate(personRq);
        Person dto = PersonMapper.INSTANCE.toDTO(personRqByDate);

        if (dto.getEmail() == null) {
            dto.setEmail(generateRandomEmail());
        }
        if (personRq.getIsPhoto()) {
            dto.setPhoto("https://storage.yandexcloud.net/socnet37/users_photo/73f6bf23-78a0-4a8e-b657-db365df178dd/123.gif");
        }
        return dto;
    }

    private Person setInfo(Person person) {

        person.setAbout(generateString(10));
        person.setChangePasswordToken(generateString(5));
        person.setConfigurationCode(generateInt());
        person.setDeletedTime(Timestamp.from(Instant.ofEpochMilli(1701670817000L)));
        person.setIsApproved(true);
        person.setIsBlocked(false);
        person.setMessagePermissions(generateString(5));
        person.setNotificationsSessionId(generateString(8));
        person.setOnlineStatus("OFFLINE");
        person.setPassword("$2a$10$DKfACXByOkjee4VELDw7R.BeslHcGeeLbCK2N8gV3.BaYjSClnObG");
        person.setPhoto(generateString(5));
        person.setTelegramId(Long.valueOf(generateInt()));

        return person;
    }

    private String generateString(Integer count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    private String generateFirstName() {
        var random = new SecureRandom();
        var list = Arrays.asList("Alex", "Vitaliy", "Danil", "Pavel", "Viktor", "Alina", "Masha", "Dima", "Georg");
        return list.get(random.nextInt(list.size()));
    }

    private String generateLastName() {
        var random = new SecureRandom();
        var list = Arrays.asList("Morozov", "Solider", "Mamonov", "Simonov", "Gagarin", "Volodin", "Amirina", "Molotov");
        return list.get(random.nextInt(list.size()));
    }

    private String generatePhone() {
        var random = new SecureRandom();
        var list = Arrays.asList("888-888-8888", "222-222-2222", "333-333-3333", "444-444-4444", "555-555-5555");
        return list.get(random.nextInt(list.size()));
    }

    private String generateCity() {
        var random = new SecureRandom();
        var list = Arrays.asList("Аруба", "Babino", "Azovo", "Palin", "Calino", "Chero", "Calam", "Pekin");
        return list.get(random.nextInt(list.size()));
    }

    private String generateCountry() {
        var random = new SecureRandom();
        var list = Arrays.asList("Алжир", "Албания", "Вьетнам", "Афганистан", "Россия", "Китай", "Нигерия", "Ангола");
        return list.get(random.nextInt(list.size()));
    }


    private PersonRq getPersonRqByDate(PersonRq personRq) {
        if (personRq.getRegDate() == null || personRq.getRegDate().equals("")) {
            personRq.setRegDate(null);
        } else {
            personRq.setRegDate(getDateByString(personRq.getRegDate()));
        }
        if (personRq.getLastOnlineTime() == null || personRq.getLastOnlineTime().equals("")) {
            personRq.setLastOnlineTime(null);
        } else {
            personRq.setLastOnlineTime(getDateByString(personRq.getLastOnlineTime()));
        }
        if (personRq.getBirthDate() == null || personRq.getBirthDate().equals("")) {
            personRq.setBirthDate(null);
        } else {
            personRq.setBirthDate(getDateByString(personRq.getBirthDate()));
        }
        return personRq;
    }

    private String getDateByString(String dateFromRq) {
        // исходная строка с датой
        LocalDate date = LocalDate.parse(dateFromRq); // преобразуем в объект LocalDate
        LocalDateTime dateTime = date.atStartOfDay(); // добавляем время (начало дня)
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // преобразуем в строку с нужным форматом

    }

    private Integer generateInt() {
        return (int) (Math.random() * (99999 - 10000) + 10000);
    }

    private String generateRandomEmail() {
        String[] providers = {"gmail.com", "yahoo.com", "outlook.com", "mail.ru", "yandex.ru"};
        Random random = new Random();
        String text = generateString(30);
        String provider = providers[random.nextInt(providers.length)];
        return text + "@" + provider;
    }

    public int generatePassword() {
        Random random = new Random();
        return 10000000 + random.nextInt(90000000);
    }


    private void deleteAllPostTags(Person person) {
        List<Post> posts = deletePersonSettingsReturnPosts(person);
        List<Like> likesByPersonId = likeRepository.findLikesByPersonId(person.getId());
        likeRepository.deleteAll(likesByPersonId);
        List<Notification> notificationsByEntityIdOrPersonId = notificationRepository.
                getNotificationsByEntityIdOrPersonId(person.getId());
        notificationsByEntityIdOrPersonId.forEach(notificationRepository::deleteNotification);
        List<BlockHistory> blocksByPersonId = blockHistoryRepository.findBlocksByPersonId(person.getId());
        blocksByPersonId.forEach(b -> blockHistoryRepository.deleteBlockHistoryById(b.getId()));
        for (Post post : posts) {
            List<Tag> tagsByPostId = post2TagRepository.findTagsByPostId(post.getId());

            post2TagRepository.deletePost2TagByPostId(post.getId());

            tagsByPostId.stream().map(Tag::getId).forEach(post2TagRepository::deleteTegById);
            List<Comment> byPostId = commentRepository.findByPostId(post.getId());
            List<BlockHistory> blocksByPostId = blockHistoryRepository.findBlocksByPostId(post.getId());
            blocksByPostId.forEach(b -> blockHistoryRepository.deleteBlockHistoryById(b.getId()));

            for (Comment comment : byPostId) {
                List<BlockHistory> blocksByCommentId = blockHistoryRepository.findBlocksByCommentId(comment.getId());
                blocksByCommentId.forEach(b -> blockHistoryRepository.deleteBlockHistoryById(b.getId()));

            }


            List<PostFiles> postFilesByPostId = postFilesRepository.findPostFilesByPostId(post.getId());
            postFilesByPostId.forEach(d -> postFilesRepository.deletePostFileById(d.getId()));
            byPostId.forEach(commentRepository::delete);
            postRepository.deleteById(post.getId());


        }

        personRepository.deleteUser(person.getEmail());
    }
}
