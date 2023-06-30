package utils;

import model.User;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

public class Utils {

    public Flux<User> createFakeUsers(int quantity) {

        List<User> userList = null;
        int i = 0;
        while (i < quantity) {
            var user = new User("ID00" + Integer.toString(i),
                    "Adri" + Integer.toString(i),
                    true,
                    LocalDate.now());

            userList.add(user);
        }

        return Flux.fromIterable(userList);
    }

}
