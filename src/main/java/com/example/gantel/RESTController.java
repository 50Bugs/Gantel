package com.example.gantel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class RESTController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SectionRepository sectionRepository;

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    private Boolean Auth(User user){
        try {
            var u = userRepository.findByemail(user.getEmail());
            if(Objects.equals(u.getPassword(), user.getPassword())){
                System.out.println(u.getPassword());
                System.out.println(user.getPassword());
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @PostMapping("/user/auth")
    public String authUser(@RequestBody User user){
        if(Auth(user)){
            return Cache.addToken(user.getEmail());
        }
        return "Incorrect auth data";
    }

    @GetMapping("/user")
    public List<User> getAllUsers(@RequestParam("token") String token) {
        if(!Cache.exists(token)){
            throw new RuntimeException();
        }
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user, @RequestParam("token") String token) {
        if(!Cache.exists(token)){
            throw new RuntimeException();
        }
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (!existingUser.isPresent()) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " not found.");
        }

        User UserToUpdate = existingUser.get();
        UserToUpdate.setName(user.getName());
        UserToUpdate.setPassword(user.getPassword());
        UserToUpdate.setEmail(user.getEmail());
        UserToUpdate.setStatus(user.getStatus());

        return userRepository.save(UserToUpdate);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @RequestParam("token") String token) {
        System.out.println(token);
        if(!Cache.exists(token)){
            throw new RuntimeException("User undefined");
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //------------------------------------------------------------------------------>

    @PostMapping("/section")
    public Section createSection(@RequestBody Section section, @RequestParam("token") String token) {
        if(!Cache.exists(token)){
            throw new RuntimeException();
        }
        try {
            return sectionRepository.save(section);
        } catch (Exception e) {
            System.out.println(e);
        }
        return section;
    }

    @GetMapping("/section")
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    @GetMapping("/section/{id}")
    public Section getSectionById(@PathVariable Long id) {
        return sectionRepository.findById(id).orElseThrow(() -> new RuntimeException("Section not found"));
    }

    @PutMapping("/section")
    public Section updateSection(@RequestBody Section section, @RequestParam("token") String token) {
        if(!Cache.exists(token)){
            throw new RuntimeException();
        }
        Optional<Section> existingSection = sectionRepository.findById(section.getId());
        if (!existingSection.isPresent()) {
            throw new IllegalArgumentException("User with ID " + section.getId() + " not found.");
        }

        Section SectionToUpdate = existingSection.get();
        SectionToUpdate.setName(section.getName());
        SectionToUpdate.setCoach(section.getCoach());
        SectionToUpdate.setDays(section.getDays());
        SectionToUpdate.setPhoto(section.getPhoto());
        SectionToUpdate.setPrice(section.getPrice());
        SectionToUpdate.setSportInventory(section.getSportInventory());

        return sectionRepository.save(SectionToUpdate);
    }

    @DeleteMapping("/section/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id, @RequestParam("token") String token) {
        if(!Cache.exists(token)){
            throw new RuntimeException();
        }
        sectionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
