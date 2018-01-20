package hillelee.security;

/**
 * Created by JavaEE on 20.01.2018.
 */
public interface UserRepository {

    User findUserByName(String username);

    void save(User user);
}
