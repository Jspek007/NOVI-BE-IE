package johan.spekman.novibeie.module_security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;


public class JwtUtil extends UsernamePasswordAuthenticationFilter {
    public static final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

    public static User getUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

    public static String getUsername(HttpServletRequest request) {
        return request.getParameter("username");
    }


    public static String getPassword(HttpServletRequest request) {
        return request.getParameter("password");
    }

    public static String createAccessToken(Authentication authentication,
                                           HttpServletRequest request) {
        User user = JwtUtil.getUser(authentication);
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 100 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("authorities",
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String createRefreshToken(Authentication authentication,
                                            HttpServletRequest request) {
        User user = JwtUtil.getUser(authentication);
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }
}
