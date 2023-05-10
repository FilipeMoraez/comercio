package br.com.comercio.login.validator;

import br.com.comercio.login.exception.WrongData;
import br.com.comercio.login.exception.WrongUser;
import br.com.comercio.login.model.User;

import java.util.Objects;
import java.util.Optional;

public class UserValidator {
    private UserValidator(){}
    public static void validEmptyData(String username, String pass){
        if(Objects.isNull(username) || Objects.isNull(pass) || username.equals("") || pass.equals("")){
            throw new WrongData();
        }
    }

    public static User validLoginAndPass(String pass, Optional<User> user) {
        if(user.isPresent()){
            if(!user.get().getPassword().equals(pass)){
                throw new WrongUser();
            }else{
                return user.get();
            }
        }else{
            throw new WrongUser();
        }
    }
}
