package cbs.cine_foro.controller.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cbs.cine_foro.entity.security.DataUser;
import cbs.cine_foro.entity.security.Role;
import cbs.cine_foro.repository.security.DataUserRepo;
import cbs.cine_foro.service.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final DataUserRepo repo;
    private final PasswordEncoder passEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthenticationResponse register(RegisterRequest request) {
        DataUser user = DataUser.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        repo.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword())
                );
        // user auth, if not, launch exception...
        DataUser user = repo.findByUserName(request.getUserName())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

}
