package br.com.banco.reposytory;


import br.com.banco.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Users, String> {
    UserDetails findByLogin(String login);
}
