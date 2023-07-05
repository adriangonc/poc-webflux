package utils;

import model.User;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class UserUtils {

    public Flux<User> createFakeUsers(int quantity) {

        List<User> userList = new ArrayList<User>();
        int i = 0;
        while (i < quantity) {
            var user = new User("ID00" + Integer.toString(i),
                    "Adrian" + Integer.toString(i),
                    true,
                    LocalDate.of(1988, 04, 15));

            userList.add(user);
            i++;
        }

        return Flux.fromIterable(userList);
    }

    private boolean getRandomBooleam(){
        Random random = new Random();

        return random.nextBoolean();
    }

}
