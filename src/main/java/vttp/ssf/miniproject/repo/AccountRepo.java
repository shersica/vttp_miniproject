package vttp.ssf.miniproject.repo;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.miniproject.model.User;

@Repository
public class AccountRepo {
    
    @Autowired @Qualifier("myredis")
    private RedisTemplate<String,String> template;

    private String key = "users";

    public void addUser(User user){
        String hashKey = user.getUsername();
        HashOperations<String, String, String> hOps = template.opsForHash();
        hOps.put(key, hashKey, user.toJson().toString());
    }

    public User getUser(String hashKey){
        HashOperations<String, String, String> hOps = template.opsForHash();
        String userString = hOps.get(key, hashKey);
        JsonReader jsonReader = Json.createReader(new StringReader(userString));
        JsonObject userObject = jsonReader.readObject();
        String username = userObject.getString("username");
        String password = userObject.getString("password");

        return new User(username, password);
    }

    public boolean userExists(String hashKey){
        HashOperations<String, String, String> hOps = template.opsForHash();
        if(hOps.hasKey(key, hashKey)){
            return true;
        }

        return false;

    }

    


}
