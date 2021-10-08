package ru.digitalleague.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.digitalleague.core.mapper.AuthorityEntityMapper;
import ru.digitalleague.core.mapper.UserAccountEntityMapper;
import ru.digitalleague.core.model.AuthorityEntity;
import ru.digitalleague.core.model.UserAccountEntity;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService, UserDetailsService {

    private final UserAccountEntityMapper userAccountEntityMapper;

    private final AuthorityEntityMapper authorityEntityMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccountEntity accountEntity = userAccountEntityMapper.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Ooops..."));
        return accountEntity;
    }

    @Override
    public UserAccountEntity registration(UserAccountEntity userAccountEntity) {
        String password = passwordEncoder.encode(userAccountEntity.getPassword());
        userAccountEntity.setPassword(password);
        userAccountEntityMapper.insert(userAccountEntity);
        List<AuthorityEntity> authorities = userAccountEntity.getAuthorities();
        authorities.forEach(authorityEntity -> authorityEntity.setUserAccountId(userAccountEntity.getId()));
        authorityEntityMapper.insertAll(authorities);
        return userAccountEntity;
    }

    @Override
    public UserAccountEntity test(String login) {
        UserAccountEntity testSqlInjection = userAccountEntityMapper.findTestSqlInjection(login);
        return testSqlInjection;
    }

    @Override
    public void deleteByPrimaryKey(Long id) {
        userAccountEntityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<String> showAllUsers() {
        List<String> listUsers = new ArrayList<>();
        userAccountEntityMapper.showAllUsers().forEach(listUsers::add);
        return listUsers;
    }

}
