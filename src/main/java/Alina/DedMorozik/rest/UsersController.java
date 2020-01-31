package Alina.DedMorozik.rest;

import Alina.DedMorozik.model.*;
import Alina.DedMorozik.repository.StoreRepository;
import Alina.DedMorozik.repository.UserRepository;

import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class UsersController {

    private final UserRepository userRepository;

    private final StoreRepository storeRepository;

    public final int minQuantity = 10;
    public String produceUrl = "http://localhost:8090/produce";

    public UsersController(UserRepository userRepository, StoreRepository storeRepository) {

        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }


    @GetMapping("/")
    public ModelAndView indexPage(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        List<Store> stores = storeRepository.findAll();
        model.addAttribute("stores", stores);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping("/getgift")
    public ResponseEntity<?> getResultAjax(
            @Valid @RequestBody Letter search, Errors errors) {

        AjaxResponseBody response = new AjaxResponseBody();

        if (errors.hasErrors()) {
            response.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(response);
        }

        List<User> resultUsers = userRepository.findAll().stream()  // User child = userRepository.getOne(userid); we can send id from select list
                .filter(x -> x.getName().equalsIgnoreCase(search.getUsername())).collect(Collectors.toList());

        if (resultUsers.isEmpty()) {
            response.setMsg(getMsgNoUserFound());
            return ResponseEntity.ok(response);
        }
        Random random = new Random();
        Integer behavior = random.nextInt(2); // from 0 to 1
        if(behavior > 0){
            response.setBehaviorMsg(getBehaviorMsg(true));

            List<Store> resultStores = getStoriesByGiftType(search.getGifttype());

            if (resultStores.isEmpty()) {
                response.setMsg(getMsgNoGiftFound());
                return ResponseEntity.ok(response);
            }
            for (Store store:resultStores) {

                int newQ = store.getQuantity()-1;
                store.setQuantity(newQ);
                if(newQ < minQuantity) {
                    response.setProduceMsg(getProduceMessage(search.getGifttype()));
                     sendRequestProduce(store.getGift().getTitle());
                }
                storeRepository.save(store);
                response.setMsg(getInfoGiftMessage(search.getUsername(), search.getGifttype()));
            }
        }
        else
            response.setBehaviorMsg(getBehaviorMsg(false));

        return ResponseEntity.ok(response);
    }

    @RequestMapping("/produce")
    public ResponseEntity<String> handleProduceRequest(RequestEntity<String> request) {
        ResponseEntity<String> responseEntity = new ResponseEntity<>("gift " +request.getBody()+" successfully produced  ", HttpStatus.OK);
        return responseEntity;
    }

    private List<Store> getStoriesByGiftType(String gyftType) {
        List<Store> resultStores = storeRepository.findAll().stream()
                .filter(x -> x.getGift().getTitle().equalsIgnoreCase(gyftType)).collect(Collectors.toList());
        return resultStores;
    }

    private void sendRequestProduce(String gifttype) {
        RestTemplate restTemplate = new RestTemplate();
        String body = gifttype;
        try {
            RequestEntity<String> request = RequestEntity
                    .post(new URI(produceUrl))
                    .accept(MediaType.APPLICATION_JSON)
                    .body(body);

            ResponseEntity<String> gift = restTemplate.exchange(request, String.class);

            if (gift.getStatusCode().is2xxSuccessful()) {
                System.out.println(gift.getBody());
            } else {
                throw new RuntimeException("it was not possible to send request");
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("it was not possible to create url");
        }
    }

    @GetMapping("/users")
    public List<UserDto> allUsers() {
        return userRepository.findAll().stream().map(user -> new UserDto() {{
                    setId(user.getId());
                    setName(user.getName());
                }}).collect(Collectors.toList());
    }

    @GetMapping("/letter")
    public static LetterDto getLetter(@RequestParam("username") String username, @RequestParam("gifttype") String gifttype) {
        LetterDto let = new LetterDto(){{
            setUserName(username);
            setGiftType(gifttype);}};
        return let;
    }

    // todo : create languages files for messages

    public String getInfoGiftMessage(String username, String gifttype){
        return "Getting gift "+ gifttype.toLowerCase() + " for child "+ username + ", please, reload page for actual quantity...";
    }

    public String getProduceMessage(String gifttype){
        return " Too few gifts, sending request to produce " + gifttype.toLowerCase();
    }

    public String getBehaviorMsg(Boolean good){
        return good? "Behavior was good" : "Behavior was NOT good";
    }

    public String getMsgNoUserFound() {
        return "No child found";
    }

    public String getMsgNoGiftFound() {
        return "No gift found";
    }
}