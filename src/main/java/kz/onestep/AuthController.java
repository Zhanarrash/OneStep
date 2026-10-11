package kz.onestep;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserAccountRepository repository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (request.name() == null || request.name().isBlank()
                || request.email() == null || request.email().isBlank()
                || request.password() == null
                || request.password().length() < 8) {

            return ResponseEntity.badRequest().body(
                    Map.of("message",
                            "Атыңды, email-ді енгіз. Пароль кемінде 8 таңба болуы керек.")
            );
        }

        String email = request.email().trim().toLowerCase();

        if (repository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("message", "Бұл email бұрын тіркелген.")
            );
        }

        UserAccount user = new UserAccount(
                email,
                request.name().trim(),
                passwordEncoder.encode(request.password())
        );

        UserAccount saved = repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "id", saved.getId(),
                        "name", saved.getName(),
                        "email", saved.getEmail(),
                        "message", "Тіркелу сәтті аяқталды."
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if (request.email() == null || request.password() == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Email мен парольді енгіз.")
            );
        }

        Optional<UserAccount> result = repository.findByEmail(
                request.email().trim().toLowerCase()
        );

        if (result.isEmpty()
                || !passwordEncoder.matches(
                request.password(),
                result.get().getPasswordHash())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("message", "Email немесе пароль қате.")
            );
        }

        UserAccount user = result.get();

        return ResponseEntity.ok(
                Map.of(
                        "id", user.getId(),
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "message", "Кіру сәтті аяқталды."
                )
        );
    }

    public record RegisterRequest(
            String name,
            String email,
            String password) {
    }

    public record LoginRequest(
            String email,
            String password) {
    }
}