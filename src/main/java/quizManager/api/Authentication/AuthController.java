package quizManager.api.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import quizManager.api.Authentication.model.AuthRequest;
import quizManager.api.Authentication.model.AuthResponse;
import quizManager.api.Authentication.model.UserPrincipal;
import quizManager.api.JwtUtil;
import quizManager.api.User.model.User;
import quizManager.api.User.repository.UserRepository;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;


    @RequestMapping(value = "/auth/createAccount", method = RequestMethod.POST)
    private ResponseEntity<?> createAccount(@RequestBody User user){
        if(userRepository.findByUsername(user.getUsername()) == null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User userToSave = new User(user.getUsername(), encoder.encode(user.getPassword()), user.getPermission());
            userRepository.save(userToSave);
            return new ResponseEntity<String>("Account created", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Username is already attached to an account", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    private ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
            ));
            User user = userRepository.findByUsername(authRequest.getUsername());
            user.setPassword("");
            UserDetails userDetails = new UserPrincipal(user);
            String jwt = jwtUtil.generateJwt(userDetails);
            return new ResponseEntity<AuthResponse>(new AuthResponse(user, jwt), HttpStatus.OK);
        } catch (BadCredentialsException e){
            return new ResponseEntity<String>("Invalid Credentials", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    private ResponseEntity<?> test(){
        return new ResponseEntity<String>("Endpoint accessed", HttpStatus.OK);
    }

}
