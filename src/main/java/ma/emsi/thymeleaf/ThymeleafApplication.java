package ma.emsi.thymeleaf;

import ma.emsi.thymeleaf.entities.Patient;
import ma.emsi.thymeleaf.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class ThymeleafApplication {

    public static void main(String[] args) {

        SpringApplication.run(ThymeleafApplication.class, args);
    }
    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(new Patient(null, "adam", new Date(), true, 150));
            patientRepository.save(new Patient(null, "ilyass", new Date(), false, 200));
            patientRepository.save(new Patient(null, "hicham", new Date(), false, 250));
            patientRepository.save(new Patient(null, "badr", new Date(), true, 198));
            patientRepository.findAll().forEach(p->{
                System.out.println(p.getNom());
            });
        };
    }
    @Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {
            UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("user11");
            if(u1==null)
            jdbcUserDetailsManager.createUser(User.withUsername("user11").password(passwordEncoder().encode("123")).roles("USER").build());
            UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("user22");
            if(u2==null)
            jdbcUserDetailsManager.createUser(User.withUsername("user22").password(passwordEncoder().encode("123")).roles("USER").build());
            UserDetails a2 = jdbcUserDetailsManager.loadUserByUsername("admin2");
            if(a2==null)
            jdbcUserDetailsManager.createUser(User.withUsername("admin2").password(passwordEncoder().encode("123")).roles("USER", "ADMIN").build());
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
