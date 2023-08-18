package pl.januarydecember.expensestrackerapi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.januarydecember.expensestrackerapi.jwt.JwtService;
import pl.januarydecember.expensestrackerapi.models.Role;
import pl.januarydecember.expensestrackerapi.models.User;
import pl.januarydecember.expensestrackerapi.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.basic)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();
        if(request.getPhoneNumber()!=0){
            user.setPhoneNumber(request.getPhoneNumber());
        }
        repo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repo.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        if ( user.isEnabled()
                && user.isAccountNonLocked()
                && user.isCredentialsNonExpired()
                && user.isAccountNonExpired()
        )
            return AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .build();
        else
            throw new Exception("Nie udało się zalogować! Skontaktuj się z administartorem. ");
    }
}
