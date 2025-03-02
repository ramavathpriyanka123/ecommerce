package com.excelr.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.excelr.model.Cosmetics;
import com.excelr.model.Electronics;
import com.excelr.model.Footwear;
import com.excelr.model.Grocery;
import com.excelr.model.KidsClothing;
import com.excelr.model.Laptops;
import com.excelr.model.MenClothing;
import com.excelr.model.Mobiles;
import com.excelr.model.Toys;
import com.excelr.model.User;
import com.excelr.model.WomenClothing;
import com.excelr.repo.CosmeticsRepository;
import com.excelr.repo.ElectronicsRepository;
import com.excelr.repo.FootwearRepository;
import com.excelr.repo.GroceryRepository;
import com.excelr.repo.KidsRepository;
import com.excelr.repo.LaptopsRepository;
import com.excelr.repo.MensRepository;
import com.excelr.repo.MobilesRepository;
import com.excelr.repo.ToysRepository;
import com.excelr.repo.UserRepository;
import com.excelr.repo.WomenRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import io.jsonwebtoken.io.IOException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class EcommerceService {
	@Autowired
	private CosmeticsRepository cosmeticsRepository;

	@Autowired
	private ElectronicsRepository electronicsRepository;

	@Autowired
	private FootwearRepository footwearRepository;

	@Autowired
	private GroceryRepository groceryRepository;

	@Autowired
	private KidsRepository kidsRepository;

	@Autowired
	private LaptopsRepository laptopsRepository;

	@Autowired
	private MensRepository mensRepository;

	@Autowired
	private MobilesRepository mobilesRepository;

	@Autowired
	private ToysRepository toysRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WomenRepository womenRepository;

	@Value("${aws.s3.bucket.name}")
	private String bucketName;

	@Value("${aws.accessKeyId}")
	private String accessKeyId;

	@Value("${aws.secretAccessKey}")
	private String secretAccessKey;

	// private final S3Client s3Client = S3Client.builder()
	// 		.region(Region.of(System.getenv("AWS_REGION")))
	// 		.credentialsProvider(StaticCredentialsProvider.create(
	// 				AwsBasicCredentials.create(System.getenv("AWS_ACCESS_KEY_ID"),
	// 						System.getenv("AWS_SECRET_ACCESS_KEY"))))
	// 		.build();

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User updateUserRole(Long id, String newRole) {
		// Fetch user by ID
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			User user = userOptional.get();

			// Remove any surrounding quotes from the role string
			newRole = newRole.replaceAll("^\"|\"$", "").trim(); // Remove quotes and trim spaces

			// Update the role
			user.setRole(newRole);

			// Save the updated user
			return userRepository.save(user);
		} else {
			// Handle user not found case
			throw new UserNotFoundException("User not found with id: " + id);
		}
	}

	public Laptops saveLaptop(String name, String description, int price, int qty, MultipartFile file)
			throws IOException {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {
			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(bucketName)
							.key(fileName)

							.contentType("image/jpeg")
							.build(),
					RequestBody.fromBytes(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error uploading file to S3: " + e.getMessage());
		}

		String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, Region.US_EAST_1.id(),
				fileName);
		System.out.println("File uploaded successfully. File URL: " + fileUrl);

		try {

			Laptops laptop = new Laptops();
			laptop.setName(name);
			laptop.setPrice(price);
			laptop.setImage(fileUrl);
			laptop.setDescription(description);
			laptop.setQty(qty);
			System.out.println("Saving Mobiles: name=" + name + ", price=" + price + ", image=" + fileUrl);

			return laptopsRepository.save(laptop);
		} catch (Exception e) {
			throw new RuntimeException("Error saving mobiles to database: " + e.getMessage());
		}
	}

	public Mobiles saveMobiles(String name, String description, int price, int qty, MultipartFile file)
			throws IOException {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {

			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(bucketName)
							.key(fileName)

							.contentType("image/jpeg")
							.build(),
					RequestBody.fromBytes(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error uploading file to S3: " + e.getMessage());
		}

		String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, Region.EU_NORTH_1.id(),
				fileName);
		System.out.println("File uploaded successfully. File URL: " + fileUrl);

		try {
			Mobiles mobiles = new Mobiles();
			mobiles.setName(name);
			mobiles.setPrice(price);
			mobiles.setImage(fileUrl);
			mobiles.setDescription(description);
			mobiles.setQty(qty);
			System.out.println("Saving Laptop: name=" + name + ", price=" + price + ", image=" + fileUrl);

			return mobilesRepository.save(mobiles);
		} catch (Exception e) {
			throw new RuntimeException("Error saving mobiles to database: " + e.getMessage());
		}
	}

	public Cosmetics saveCosmetics(String name, String description, int price, int qty, MultipartFile file)
			throws IOException {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {
			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(bucketName)
							.key(fileName)

							.contentType("image/jpeg")
							.build(),
					RequestBody.fromBytes(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error uploading file to S3: " + e.getMessage());
		}
		String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, Region.EU_NORTH_1.id(),
				fileName);
		System.out.println("File cosmetics successfully. File URL: " + fileUrl);

		try {
			Cosmetics cosmetics = new Cosmetics();
			cosmetics.setName(name);
			cosmetics.setPrice(price);
			cosmetics.setImage(fileUrl);
			cosmetics.setDescription(description);
			cosmetics.setQty(qty);
			System.out.println("Saving Cosmetics: name=" + name + ", price=" + price + ", image=" + fileUrl);

			return cosmeticsRepository.save(cosmetics);
		} catch (Exception e) {
			throw new RuntimeException("Error saving cosmetics to database: " + e.getMessage());
		}
	}

	public Electronics saveElectronics(String name, String description, int price, int qty, MultipartFile file)
			throws IOException {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {
			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(bucketName)
							.key(fileName)

							.contentType("image/jpeg")
							.build(),
					RequestBody.fromBytes(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error electronics file to S3: " + e.getMessage());
		}
		String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, Region.EU_NORTH_1.id(),
				fileName);
		System.out.println("File electronics successfully. File URL: " + fileUrl);

		try {
			Electronics electronics = new Electronics();
			electronics.setName(name);
			electronics.setPrice(price);
			electronics.setImage(fileUrl);
			electronics.setDescription(description);
			electronics.setQty(qty);
			System.out.println("Saving Cosmetics: name=" + name + ", price=" + price + ", image=" + fileUrl);

			return electronicsRepository.save(electronics);
		} catch (Exception e) {
			throw new RuntimeException("Error saving electronics to database: " + e.getMessage());
		}
	}

	public Footwear saveFootwear(String name, String description, int price, int qty, MultipartFile file)
			throws IOException {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {
			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(bucketName)
							.key(fileName)

							.contentType("image/jpeg")
							.build(),
					RequestBody.fromBytes(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error footwear file to S3: " + e.getMessage());
		}
		String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, Region.EU_NORTH_1.id(),
				fileName);
		System.out.println("File footwear successfully. File URL: " + fileUrl);

		try {
			Footwear footwear = new Footwear();
			footwear.setName(name);
			footwear.setPrice(price);
			footwear.setImage(fileUrl);
			footwear.setDescription(description);
			footwear.setQty(qty);
			System.out.println("Saving Cosmetics: name=" + name + ", price=" + price + ", image=" + fileUrl);

			return footwearRepository.save(footwear);
		} catch (Exception e) {
			throw new RuntimeException("Error saving footwear to database: " + e.getMessage());
		}
	}

	public Grocery saveGrocery(String name, String description, int price, int qty, MultipartFile file)
			throws IOException {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {
			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(bucketName)
							.key(fileName)

							.contentType("image/jpeg")
							.build(),
					RequestBody.fromBytes(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error Grocery file to S3: " + e.getMessage());
		}
		String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, Region.EU_NORTH_1.id(),
				fileName);
		System.out.println("File grocery successfully. File URL: " + fileUrl);

		try {
			Grocery grocery = new Grocery();
			grocery.setName(name);
			grocery.setPrice(price);
			grocery.setImage(fileUrl);
			grocery.setDescription(description);
			grocery.setQty(qty);
			System.out.println("Saving Cosmetics: name=" + name + ", price=" + price + ", image=" + fileUrl);

			return groceryRepository.save(grocery);
		} catch (Exception e) {
			throw new RuntimeException("Error saving grocery to database: " + e.getMessage());
		}
	}

	public KidsClothing saveKidsClothing(String name, String description, int price, int qty, MultipartFile file)
			throws IOException {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {
			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(bucketName)
							.key(fileName)

							.contentType("image/jpeg")
							.build(),
					RequestBody.fromBytes(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error KidsClothing file to S3: " + e.getMessage());
		}
		String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, Region.EU_NORTH_1.id(),
				fileName);
		System.out.println("File grocery successfully. File URL: " + fileUrl);

		try {
			KidsClothing kidsClothing = new KidsClothing();
			kidsClothing.setName(name);
			kidsClothing.setPrice(price);
			kidsClothing.setImage(fileUrl);
			kidsClothing.setDescription(description);
			kidsClothing.setQty(qty);
			System.out.println("Saving kidsClothing: name=" + name + ", price=" + price + ", image=" + fileUrl);

			return kidsRepository.save(kidsClothing);
		} catch (Exception e) {
			throw new RuntimeException("Error saving kidsClothing to database: " + e.getMessage());
		}
	}

	public MenClothing saveMenClothing(String name, String description, int price, int qty, MultipartFile file)
			throws IOException {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {
			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(bucketName)
							.key(fileName)

							.contentType("image/jpeg")
							.build(),
					RequestBody.fromBytes(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error MenClothing file to S3: " + e.getMessage());
		}
		String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, Region.EU_NORTH_1.id(),
				fileName);
		System.out.println("File grocery successfully. File URL: " + fileUrl);

		try {
			MenClothing menClothing = new MenClothing();
			menClothing.setName(name);
			menClothing.setPrice(price);
			menClothing.setImage(fileUrl);
			menClothing.setDescription(description);
			menClothing.setQty(qty);
			System.out.println("Saving menClothing: name=" + name + ", price=" + price + ", image=" + fileUrl);

			return mensRepository.save(menClothing);
		} catch (Exception e) {
			throw new RuntimeException("Error menClothing kidsClothing to database: " + e.getMessage());
		}
	}

	public Toys saveToys(String name, String description, int price, int qty, MultipartFile file) throws IOException {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {
			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(bucketName)
							.key(fileName)

							.contentType("image/jpeg")
							.build(),
					RequestBody.fromBytes(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error Toys file to S3: " + e.getMessage());
		}
		String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, Region.EU_NORTH_1.id(),
				fileName);
		System.out.println("File toys successfully. File URL: " + fileUrl);

		try {
			Toys toys = new Toys();
			toys.setName(name);
			toys.setPrice(price);
			toys.setImage(fileUrl);
			toys.setDescription(description);
			toys.setQty(qty);
			System.out.println("Saving toys: name=" + name + ", price=" + price + ", image=" + fileUrl);

			return toysRepository.save(toys);
		} catch (Exception e) {
			throw new RuntimeException("Error toys kidsClothing to database: " + e.getMessage());
		}
	}

	public WomenClothing saveWomenClothing(String name, String description, int price, int qty, MultipartFile file)
			throws IOException {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		try {
			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(bucketName)
							.key(fileName)

							.contentType("image/jpeg")
							.build(),
					RequestBody.fromBytes(file.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Error WomenClothing file to S3: " + e.getMessage());
		}
		String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, Region.EU_NORTH_1.id(),
				fileName);
		System.out.println("File WomenClothing successfully. File URL: " + fileUrl);

		try {
			WomenClothing womenClothing = new WomenClothing();
			womenClothing.setName(name);
			womenClothing.setPrice(price);
			womenClothing.setImage(fileUrl);
			womenClothing.setDescription(description);
			womenClothing.setQty(qty);
			System.out.println("Saving womenClothing: name=" + name + ", price=" + price + ", image=" + fileUrl);

			return womenRepository.save(womenClothing);
		} catch (Exception e) {
			throw new RuntimeException("Error womenClothing kidsClothing to database: " + e.getMessage());
		}

	}

	public List<Cosmetics> getCosmetics() {
		return cosmeticsRepository.findAll();
	}

	public List<Electronics> getElectronics() {
		return electronicsRepository.findAll();
	}

	public List<Footwear> getFootwear() {
		return footwearRepository.findAll();
	}

	public List<Grocery> getGrocery() {
		return groceryRepository.findAll();
	}

	public List<KidsClothing> getKidsClothing() {
		return kidsRepository.findAll();
	}

	public List<Laptops> getLaptop() {
		return laptopsRepository.findAll();
	}

	public List<MenClothing> getMenClothing() {
		return mensRepository.findAll();
	}

	public List<WomenClothing> getWomenClothing() {
		return womenRepository.findAll();
	}

	public List<Mobiles> getMobiles() {
		return mobilesRepository.findAll();
	}

	public List<Toys> getToys() {
		return toysRepository.findAll();
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public List<Electronics> searchByName(String keyword) {
		return electronicsRepository.findByNameContainingIgnoreCase(keyword);
	}

	public List<Cosmetics> searchCosmeticsByName(String keyword) {
		return cosmeticsRepository.findByNameContainingIgnoreCase(keyword);
	}

	public List<Footwear> searchFootwearByName(String keyword) {
		return footwearRepository.findByNameContainingIgnoreCase(keyword);
	}

	public List<Grocery> searchGroceryByName(String keyword) {
		return groceryRepository.findByNameContainingIgnoreCase(keyword);
	}

	public List<KidsClothing> searchKidsClothingByName(String keyword) {
		return kidsRepository.findByNameContainingIgnoreCase(keyword);
	}

	public List<Laptops> searchLaptopsByName(String keyword) {
		return laptopsRepository.findByNameContainingIgnoreCase(keyword);
	}

	public List<MenClothing> searchMensClothingByName(String keyword) {
		return mensRepository.findByNameContainingIgnoreCase(keyword);
	}

	public List<Mobiles> searchMobilesByName(String keyword) {
		return mobilesRepository.findByNameContainingIgnoreCase(keyword);
	}

	public List<Toys> searchToysByName(String keyword) {
		return toysRepository.findByNameContainingIgnoreCase(keyword);
	}

	public List<WomenClothing> searchWomenClothingByName(String keyword) {
		return womenRepository.findByNameContainingIgnoreCase(keyword);
	}

	public Laptops updateLaptop(Long id, String name, String description, int price, int qty, MultipartFile file) {

		Laptops existingLaptop = laptopsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Laptop with ID " + id + " not found"));

		try {

			existingLaptop.setName(name);
			existingLaptop.setDescription(description);
			existingLaptop.setPrice(price);
			existingLaptop.setQty(qty);

			if (file != null && !file.isEmpty()) {
				String uploadDir = "uploads/laptops/";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, file.getBytes());

				existingLaptop.setImage(filePath.toString());
			}

			return laptopsRepository.save(existingLaptop);

		} catch (Exception e) {
			throw new RuntimeException("Error updating laptop: " + e.getMessage(), e);
		}
	}

	public Mobiles updateMobiles(Long id, String name, String description, int price, int qty, MultipartFile file) {

		Mobiles existingMobile = mobilesRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Laptop with ID " + id + " not found"));

		try {
			existingMobile.setName(name);
			existingMobile.setDescription(description);
			existingMobile.setPrice(price);
			existingMobile.setQty(qty);

			if (file != null && !file.isEmpty()) {
				String uploadDir = "uploads/Mobiles/";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, file.getBytes());

				existingMobile.setImage(filePath.toString());
			}

			return mobilesRepository.save(existingMobile);

		} catch (Exception e) {
			throw new RuntimeException("Error updating laptop: " + e.getMessage(), e);
		}
	}

	public Toys updateToys(Long id, String name, String description, int price, int qty, MultipartFile file) {
		Toys existingToy = toysRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Toy with ID " + id + " not found"));

		try {
			existingToy.setName(name);
			existingToy.setDescription(description);
			existingToy.setPrice(price);
			existingToy.setQty(qty);

			if (file != null && !file.isEmpty()) {
				String uploadDir = "uploads/Toys/";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, file.getBytes());
				existingToy.setImage(filePath.toString());
			}

			return toysRepository.save(existingToy);

		} catch (Exception e) {
			throw new RuntimeException("Error updating toy: " + e.getMessage(), e);
		}
	}

	public Cosmetics updateCosmetics(Long id, String name, String description, int price, int qty, MultipartFile file) {
		Cosmetics existingCosmetic = cosmeticsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cosmetic with ID " + id + " not found"));

		try {
			existingCosmetic.setName(name);
			existingCosmetic.setDescription(description);
			existingCosmetic.setPrice(price);
			existingCosmetic.setQty(qty);

			if (file != null && !file.isEmpty()) {
				String uploadDir = "uploads/Cosmetics/";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, file.getBytes());
				existingCosmetic.setImage(filePath.toString());
			}

			return cosmeticsRepository.save(existingCosmetic);

		} catch (Exception e) {
			throw new RuntimeException("Error updating cosmetic: " + e.getMessage(), e);
		}
	}

	public KidsClothing updateKidsClothing(Long id, String name, String description, int price, int qty,
			MultipartFile file) {
		KidsClothing existingKidsClothing = kidsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("KidsClothing product with ID " + id + " not found"));

		try {
			existingKidsClothing.setName(name);
			existingKidsClothing.setDescription(description);
			existingKidsClothing.setPrice(price);
			existingKidsClothing.setQty(qty);

			if (file != null && !file.isEmpty()) {
				String uploadDir = "uploads/KidsClothing/";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, file.getBytes());
				existingKidsClothing.setImage(filePath.toString());
			}

			return kidsRepository.save(existingKidsClothing);

		} catch (Exception e) {
			throw new RuntimeException("Error updating KidsClothing product: " + e.getMessage(), e);
		}
	}

	public MenClothing updateMenClothing(Long id, String name, String description, int price, int qty,
			MultipartFile file) {
		MenClothing existingMenClothing = mensRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("MenClothing product with ID " + id + " not found"));

		try {
			existingMenClothing.setName(name);
			existingMenClothing.setDescription(description);
			existingMenClothing.setPrice(price);
			existingMenClothing.setQty(qty);

			if (file != null && !file.isEmpty()) {
				String uploadDir = "uploads/MenClothing/";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, file.getBytes());
				existingMenClothing.setImage(filePath.toString());
			}

			return mensRepository.save(existingMenClothing);

		} catch (Exception e) {
			throw new RuntimeException("Error updating MenClothing product: " + e.getMessage(), e);
		}
	}

	public WomenClothing updateWomenClothing(Long id, String name, String description, int price, int qty,
			MultipartFile file) {
		WomenClothing existingWomenClothing = womenRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("WomenClothing product with ID " + id + " not found"));

		try {
			existingWomenClothing.setName(name);
			existingWomenClothing.setDescription(description);
			existingWomenClothing.setPrice(price);
			existingWomenClothing.setQty(qty);

			if (file != null && !file.isEmpty()) {
				String uploadDir = "uploads/WomenClothing/";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, file.getBytes());
				existingWomenClothing.setImage(filePath.toString());
			}

			return womenRepository.save(existingWomenClothing);

		} catch (Exception e) {
			throw new RuntimeException("Error updating WomenClothing product: " + e.getMessage(), e);
		}
	}

	public Grocery updateGrocery(Long id, String name, String description, int price, int qty, MultipartFile file) {
		Grocery existingGrocery = groceryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Grocery product with ID " + id + " not found"));

		try {
			existingGrocery.setName(name);
			existingGrocery.setDescription(description);
			existingGrocery.setPrice(price);
			existingGrocery.setQty(qty);

			if (file != null && !file.isEmpty()) {
				String uploadDir = "uploads/Grocery/";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, file.getBytes());
				existingGrocery.setImage(filePath.toString());
			}

			return groceryRepository.save(existingGrocery);

		} catch (Exception e) {
			throw new RuntimeException("Error updating Grocery product: " + e.getMessage(), e);
		}
	}

	public Footwear updateFootwear(Long id, String name, String description, int price, int qty, MultipartFile file) {
		Footwear existingFootwear = footwearRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Footwear product with ID " + id + " not found"));

		try {
			existingFootwear.setName(name);
			existingFootwear.setDescription(description);
			existingFootwear.setPrice(price);
			existingFootwear.setQty(qty);

			if (file != null && !file.isEmpty()) {
				String uploadDir = "uploads/Footwear/";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, file.getBytes());
				existingFootwear.setImage(filePath.toString());
			}

			return footwearRepository.save(existingFootwear);

		} catch (Exception e) {
			throw new RuntimeException("Error updating footwear product: " + e.getMessage(), e);
		}
	}

	public Electronics updateElectronics(Long id, String name, String description, int price, int qty,
			MultipartFile file) {
		Electronics existingElectronics = electronicsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Electronics product with ID " + id + " not found"));

		try {
			existingElectronics.setName(name);
			existingElectronics.setDescription(description);
			existingElectronics.setPrice(price);
			existingElectronics.setQty(qty);

			if (file != null && !file.isEmpty()) {
				String uploadDir = "uploads/Electronics/";
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path filePath = uploadPath.resolve(fileName);
				Files.write(filePath, file.getBytes());
				existingElectronics.setImage(filePath.toString());
			}

			return electronicsRepository.save(existingElectronics);

		} catch (Exception e) {
			throw new RuntimeException("Error updating electronics product: " + e.getMessage(), e);
		}
	}

	public void deleteLaptop(Long id) {
		Laptops existingLaptop = laptopsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Laptop product with ID " + id + " not found"));

		try {
			laptopsRepository.delete(existingLaptop);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting laptop product: " + e.getMessage(), e);
		}
	}

	public void deleteMobiles(Long id) {
		Mobiles existingMobile = mobilesRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Mobile product with ID " + id + " not found"));

		try {
			mobilesRepository.delete(existingMobile);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting mobile product: " + e.getMessage(), e);
		}
	}

	public void deleteFootwear(Long id) {
		Footwear existingFootwear = footwearRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Footwear product with ID " + id + " not found"));

		try {
			footwearRepository.delete(existingFootwear);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting footwear product: " + e.getMessage(), e);
		}
	}

	public void deleteToys(Long id) {
		Toys existingToy = toysRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Toy product with ID " + id + " not found"));

		try {
			toysRepository.delete(existingToy);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting toy product: " + e.getMessage(), e);
		}
	}

	public void deleteElectronics(Long id) {
		Electronics existingElectronics = electronicsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Electronics product with ID " + id + " not found"));

		try {
			electronicsRepository.delete(existingElectronics);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting electronics product: " + e.getMessage(), e);
		}
	}

	public void deleteWomen(Long id) {
		WomenClothing existingWomenProduct = womenRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Women product with ID " + id + " not found"));

		try {
			womenRepository.delete(existingWomenProduct);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting women product: " + e.getMessage(), e);
		}
	}

	public void deleteMen(Long id) {
		MenClothing existingMenProduct = mensRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Men product with ID " + id + " not found"));

		try {
			mensRepository.delete(existingMenProduct);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting men product: " + e.getMessage(), e);
		}
	}

	public void deleteKids(Long id) {
		KidsClothing existingKidsProduct = kidsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Kids product with ID " + id + " not found"));

		try {
			kidsRepository.delete(existingKidsProduct);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting kids product: " + e.getMessage(), e);
		}
	}

	public void deleteGrocery(Long id) {
		Grocery existingGroceryProduct = groceryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Grocery product with ID " + id + " not found"));

		try {
			groceryRepository.delete(existingGroceryProduct);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting grocery product: " + e.getMessage(), e);
		}
	}

	public void deleteCosmetics(Long id) {
		Cosmetics existingCosmeticsProduct = cosmeticsRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cosmetics product with ID " + id + " not found"));

		try {
			cosmeticsRepository.delete(existingCosmeticsProduct);
		} catch (Exception e) {
			throw new RuntimeException("Error deleting cosmetics product: " + e.getMessage(), e);
		}
	}
}
