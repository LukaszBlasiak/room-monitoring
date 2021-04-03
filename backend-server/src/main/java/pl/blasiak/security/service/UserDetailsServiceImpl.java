package pl.blasiak.security.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.blasiak.security.config.JwtProperties;
import pl.blasiak.security.entity.UserEntity;
import pl.blasiak.security.mapper.UserDetailsMapper;
import pl.blasiak.security.repository.UserRepository;
import pl.blasiak.security.util.PasswordUtil;

import javax.annotation.PostConstruct;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordUtil passwordUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsMapper userDetailsMapper;

    private static final char[] password = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};

    public UserDetailsServiceImpl(final PasswordUtil passwordUtil, final UserRepository userRepository,
                                  @Lazy final PasswordEncoder passwordEncoder, final JwtProperties jwtProperties,
                                  final UserDetailsMapper userDetailsMapper) {
        this.passwordUtil = passwordUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserEntity foundUser = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return this.userDetailsMapper.map(foundUser);
    }



    @PostConstruct
    private void initializeUsers() {
        final UserEntity userToSave =
                new UserEntity(null, "lukasz", true, passwordEncoder.encode(String.valueOf(password)));
        this.userRepository.save(userToSave);
        this.passwordUtil.wipePassword(password);
    }
}
