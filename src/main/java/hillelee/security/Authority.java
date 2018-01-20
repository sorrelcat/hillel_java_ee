package hillelee.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 * Created by JavaEE on 20.01.2018.
 */
@Data
@Embeddable
public class Authority  implements GrantedAuthority{

    private String Authority;

}
