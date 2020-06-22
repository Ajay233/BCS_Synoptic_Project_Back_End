package quizManager.api.Authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import quizManager.api.Authentication.model.UserPrincipal;
import quizManager.api.User.model.User;
import quizManager.api.User.repository.UserRepository;

@Service
public class QuizManagerUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("No user found matching that username");
        }

        return new UserPrincipal(user);
    }
}
