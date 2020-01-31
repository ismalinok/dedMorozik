package Alina.DedMorozik;

import Alina.DedMorozik.model.Gift;
import Alina.DedMorozik.model.Store;
import Alina.DedMorozik.model.User;
import Alina.DedMorozik.repository.GiftRepository;
import Alina.DedMorozik.repository.StoreRepository;
import Alina.DedMorozik.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DedMorozikApp {

	public static void main(String[] args) {
		SpringApplication.run(DedMorozikApp.class, args);
	}

	@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
	@Autowired
	private UserRepository userRepository;

	@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
	@Autowired
	private GiftRepository giftRepository;

	@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
	@Autowired
	private StoreRepository storeRepository;

	@PostConstruct
	public void init() {
		saveUsers();
		saveGifts();
	}

	public void saveUsers(){
		userRepository.save(new User("Maria"));
		userRepository.save(new User("Veronika"));
		userRepository.save(new User("Ivan"));
		userRepository.save(new User("Mikle"));
	}

	public void saveGifts(){

		Gift g1 = new Gift("Chocolate");
		Gift g2 = new Gift("Teddy bear");
		Gift g3 = new Gift("Train");
		Gift g4 = new Gift("Lego");
		List<Gift> lg = new ArrayList<Gift>(Arrays.asList(g1,g2,g3,g4));

		int q = 10;
		Store s;
		for (Gift g : lg){
			giftRepository.save(g);
			s = new Store(g,q);
			storeRepository.save(s);
			q+=5;
		}
	}
}
