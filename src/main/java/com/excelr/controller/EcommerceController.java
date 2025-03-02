package com.excelr.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import com.excelr.repo.UserRepository;
import com.excelr.service.EcommerceService;
import com.excelr.util.JwtUtil;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class EcommerceController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private EcommerceService ecommerceService;
	
	@Autowired
    public EcommerceController(EcommerceService ecommerceService) {
        this.ecommerceService = ecommerceService;
    }
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {

	    String username = loginData.get("username");
	    String password = loginData.get("password");
	    Optional<User> user = userRepository.findByUsername(username);

	    if (user.isPresent() && user.get().getPassword().equals(password)) {
	        Map<String, String> response = new HashMap<>();
	        String token = jwtUtil.generateToken(username);

	        response.put("login", "success");
	        response.put("token", token);
	        response.put("role", user.get().getRole());
	        response.put("username", user.get().getUsername()); 
	        response.put("id", String.valueOf(user.get().getId())); 

	        return ResponseEntity.ok(response);
	    } else {
	        Map<String, String> response1 = new HashMap<>();
	        response1.put("login", "fail");
	        return ResponseEntity.status(401).body(response1);
	    }
	}

	 @GetMapping("/admin/users")
	    public ResponseEntity<List<User>> getAllUsers() {
	        List<User> users = ecommerceService.getAllUsers();
	        return ResponseEntity.ok(users);
	    }

	 @PutMapping("/admin/users/{id}/role")
	 public ResponseEntity<User> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
	     String newRole = requestBody.get("role"); 
	     if (newRole == null || (!newRole.equals("ROLE_USER") && !newRole.equals("ROLE_ADMIN"))) {
	         return ResponseEntity.badRequest().body(null);  
	     }
	     User updatedUser = ecommerceService.updateUserRole(id, newRole);  
	     return ResponseEntity.ok(updatedUser);
	 }

	
	@GetMapping("/user/laptops")
	public List<Laptops> getLatops() {
		return ecommerceService.getLaptop();
	}
	
	@GetMapping("/user/mobiles")
	public List<Mobiles> getMobiles() {
		return ecommerceService.getMobiles();
	}
	
	@GetMapping("/user/men")
	public List<MenClothing> getMenClothing() {
		return ecommerceService.getMenClothing();
	}
	@GetMapping("/user/women")
	public List<WomenClothing> getWomenClothing(){
		return ecommerceService.getWomenClothing();
		
	}
	@GetMapping("/user/kids")
	public List<KidsClothing> getKidsClothing(){
		return ecommerceService.getKidsClothing();
	}
	@GetMapping("/user/footwear")
	public List<Footwear> getFootwear(){
		return ecommerceService.getFootwear();
	}
	@GetMapping("/user/toys")
	public List<Toys> getToys(){
		return ecommerceService.getToys();
	}
	@GetMapping("/user/grocery")
	public List<Grocery> getGroceries(){
		return ecommerceService.getGrocery();
	}
	@GetMapping("/user/electronics")
	public List<Electronics> getElectronics(){
		return ecommerceService.getElectronics();
	}
	@GetMapping("/user/cosmetics")
	public List<Cosmetics> getCosmetics(){
		return ecommerceService.getCosmetics();
	}
	
	@PostMapping("/admin/upload/laptops")
	public ResponseEntity<?> uploadLaptops(@RequestParam String name,@RequestParam String description,@RequestParam int price,@RequestParam int qty,@RequestParam  MultipartFile file){
		if(name == null || name.isEmpty() || price <=0 || file == null ||file.isEmpty() || qty<=0 || description == null || description.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid input parameters ");
		}else {
			Laptops saveLaptops = null;
			try {
				saveLaptops = ecommerceService.saveLaptop(name, description, price, qty, file);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(saveLaptops);
		}	
		
	}
	@PostMapping("/admin/upload/mobiles")
	public ResponseEntity<?> uploadMobiles(@RequestParam String name,@RequestParam String description,@RequestParam int price,@RequestParam int qty,@RequestParam  MultipartFile file){
		if(name == null || name.isEmpty() || price <=0 || file == null ||file.isEmpty() || qty<=0 || description == null || description.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid input parameters ");
		}else {
			Mobiles saveMobiles = null;
			try {
				saveMobiles = ecommerceService.saveMobiles(name, description, price, qty, file);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(saveMobiles);
		}	
		
	}
	@PostMapping("/admin/upload/cosmetics")
	public ResponseEntity<?> uploadCosmetics(@RequestParam String name,@RequestParam String description,@RequestParam int price,@RequestParam int qty,@RequestParam  MultipartFile file){
		if(name == null || name.isEmpty() || price <=0 || file == null ||file.isEmpty() || qty<=0 || description == null || description.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid input parameters ");
		}else {
			Cosmetics saveCosmetics = null;
			try {
				saveCosmetics = ecommerceService.saveCosmetics(name, description, price, qty, file);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(saveCosmetics);
		}	
		
	}
	@PostMapping("/admin/upload/electronics")
	public ResponseEntity<?> uploadElectronics(@RequestParam String name,@RequestParam String description,@RequestParam int price,@RequestParam int qty,@RequestParam  MultipartFile file){
		if(name == null || name.isEmpty() || price <=0 || file == null ||file.isEmpty() || qty<=0 || description == null || description.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid input parameters ");
		}else {
			Electronics saveElectronics = null;
			try {
				saveElectronics = ecommerceService.saveElectronics(name, description, price, qty, file);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(saveElectronics);
		}	
		
	}
	@PostMapping("/admin/upload/toys")
	public ResponseEntity<?> uploadToys(@RequestParam String name,@RequestParam String description,@RequestParam int price,@RequestParam int qty,@RequestParam  MultipartFile file){
		if(name == null || name.isEmpty() || price <=0 || file == null ||file.isEmpty() || qty<=0 || description == null || description.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid input parameters ");
		}else {
			Toys saveToys = null;
			try {
				saveToys = ecommerceService.saveToys(name, description, price, qty, file);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(saveToys);
		}	
		
	}
	@PostMapping("/admin/upload/footwear")
	public ResponseEntity<?> uploadFootwear(@RequestParam String name,@RequestParam String description,@RequestParam int price,@RequestParam int qty,@RequestParam  MultipartFile file){
		if(name == null || name.isEmpty() || price <=0 || file == null ||file.isEmpty() || qty<=0 || description == null || description.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid input parameters ");
		}else {
			Footwear saveFootwear = null;
			try {
				saveFootwear = ecommerceService.saveFootwear(name, description, price, qty, file);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(saveFootwear);
		}	
		
	}
	@PostMapping("/admin/upload/grocery")
	public ResponseEntity<?> uploadGrocery(@RequestParam String name,@RequestParam String description,@RequestParam int price,@RequestParam int qty,@RequestParam  MultipartFile file){
		if(name == null || name.isEmpty() || price <=0 || file == null ||file.isEmpty() || qty<=0 || description == null || description.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid input parameters ");
		}else {
			Grocery savegGrocery = null;
			try {
				savegGrocery = ecommerceService.saveGrocery(name, description, price, qty, file);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(savegGrocery);
		}	
		
	}
	
	
	@PostMapping("/admin/upload/kidsclothing")
	public ResponseEntity<?> uploadKidsclothing(@RequestParam String name,@RequestParam String description,@RequestParam int price,@RequestParam int qty,@RequestParam  MultipartFile file){
		if(name == null || name.isEmpty() || price <=0 || file == null ||file.isEmpty() || qty<=0 || description == null || description.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid input parameters ");
		}else {
			KidsClothing savekKidsClothing = null;
			try {
				savekKidsClothing = ecommerceService.saveKidsClothing(name, description, price, qty, file);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(savekKidsClothing);
		}	
		
	}
	@PostMapping("/admin/upload/menclothing")
	public ResponseEntity<?> uploadMenclothing(@RequestParam String name,@RequestParam String description,@RequestParam int price,@RequestParam int qty,@RequestParam  MultipartFile file){
		if(name == null || name.isEmpty() || price <=0 || file == null ||file.isEmpty() || qty<=0 || description == null || description.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid input parameters ");
		}else {
			MenClothing saveMenClothing = null;
			try {
				saveMenClothing = ecommerceService.saveMenClothing(name, description, price, qty, file);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(saveMenClothing);
		}	
		
	}
	
	@PostMapping("/admin/upload/womenclothing")
	public ResponseEntity<?> uploadWomenclothing(@RequestParam String name,@RequestParam String description,@RequestParam int price,@RequestParam int qty,@RequestParam  MultipartFile file){
		if(name == null || name.isEmpty() || price <=0 || file == null ||file.isEmpty() || qty<=0 || description == null || description.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid input parameters ");
		}else {
			WomenClothing saveWomenClothing = null;
			try {
				saveWomenClothing = ecommerceService.saveWomenClothing(name, description, price, qty, file);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(saveWomenClothing);
		}	
		
	}
	
	@PostMapping("/register")
	public User saveRegister(@RequestBody User user) {
		return ecommerceService.saveUser(user);
	}
	@CrossOrigin(origins = "*")
	@RestController
	@RequestMapping("/user/search")
	public class SearchController {

	    private final EcommerceService ecommerceService;

	    public SearchController(EcommerceService ecommerceService) {
	        this.ecommerceService = ecommerceService;
	    }

	    @GetMapping
	    public ResponseEntity<?> searchByKeyword(@RequestParam String keyword) {
	      
	        List<Electronics> electronics = ecommerceService.searchByName(keyword);
	        List<Cosmetics> cosmetics = ecommerceService.searchCosmeticsByName(keyword);
	        List<Footwear> footwear = ecommerceService.searchFootwearByName(keyword);
	        List<Grocery> grocery = ecommerceService.searchGroceryByName(keyword);
	        List<KidsClothing> kidsClothing = ecommerceService.searchKidsClothingByName(keyword);
	        List<Laptops> laptops = ecommerceService.searchLaptopsByName(keyword);
	        List<MenClothing> menClothing = ecommerceService.searchMensClothingByName(keyword);
	        List<Mobiles> mobiles = ecommerceService.searchMobilesByName(keyword);
	        List<Toys> toys = ecommerceService.searchToysByName(keyword);
	        List<WomenClothing> womenClothing = ecommerceService.searchWomenClothingByName(keyword);

	        
	        Map<String, Object> result = new LinkedHashMap<>();
	        result.put("electronics", electronics);
	        result.put("cosmetics", cosmetics);
	        result.put("footwear", footwear);
	        result.put("grocery", grocery);
	        result.put("kidsClothing", kidsClothing);
	        result.put("laptops", laptops);
	        result.put("menClothing", menClothing);
	        result.put("mobiles", mobiles);
	        result.put("toys", toys);
	        result.put("womenClothing", womenClothing);

	        return ResponseEntity.ok(result);
	    }
	}
	@GetMapping("/laptops")
	public List<Laptops> laptopdb1() {
		return ecommerceService.getLaptop();
	}
	
	@GetMapping("/mobiles")
	public List<Mobiles> mobiledb1() {
		return ecommerceService.getMobiles();
	}
	
	@GetMapping("/men")
	public List<MenClothing> mendb1() {
		return ecommerceService.getMenClothing();
	}
	@GetMapping("/women")
	public List<WomenClothing> womendb1(){
		return ecommerceService.getWomenClothing();
		
	}
	@GetMapping("/kids")
	public List<KidsClothing> kidsdb1(){
		return ecommerceService.getKidsClothing();
	}
	@GetMapping("/footwear")
	public List<Footwear> footwear1(){
		return ecommerceService.getFootwear();
	}
	@GetMapping("/toys")
	public List<Toys> toysdb1(){
		return ecommerceService.getToys();
	}
	@GetMapping("/grocery")
	public List<Grocery> grocerydb1(){
		return ecommerceService.getGrocery();
	}
	@GetMapping("/electronics")
	public List<Electronics> electronicsdb1(){
		return ecommerceService.getElectronics();
	}
	@GetMapping("/cosmetics")
	public List<Cosmetics> cosmeticsdb1(){
		return ecommerceService.getCosmetics();
	}
	
	@GetMapping("/admin/laptops")
	public List<Laptops> laptopdb() {
		return ecommerceService.getLaptop();
	}
	
	@GetMapping("/admin/mobiles")
	public List<Mobiles> mobiledb() {
		return ecommerceService.getMobiles();
	}
	
	@GetMapping("/admin/men")
	public List<MenClothing> mendb() {
		return ecommerceService.getMenClothing();
	}
	@GetMapping("/admin/women")
	public List<WomenClothing> womendb(){
		return ecommerceService.getWomenClothing();
		
	}
	@GetMapping("/admin/kids")
	public List<KidsClothing> kidsdb(){
		return ecommerceService.getKidsClothing();
	}
	@GetMapping("/admin/footwear")
	public List<Footwear> footwear(){
		return ecommerceService.getFootwear();
	}
	@GetMapping("/admin/toys")
	public List<Toys> toysdb(){
		return ecommerceService.getToys();
	}
	@GetMapping("/admin/grocery")
	public List<Grocery> grocerydb(){
		return ecommerceService.getGrocery();
	}
	@GetMapping("/admin/electronics")
	public List<Electronics> electronicsdb(){
		return ecommerceService.getElectronics();
	}
	@GetMapping("/admin/cosmetics")
	public List<Cosmetics> cosmeticsdb(){
		return ecommerceService.getCosmetics();
	}
	
    @PutMapping("/admin/update/laptops/{id}")
    public ResponseEntity<?> updateLaptop(@PathVariable Long id, 
                                          @RequestParam String name, 
                                          @RequestParam String description, 
                                          @RequestParam int price, 
                                          @RequestParam int qty, 
                                          @RequestParam(required = false) MultipartFile file) {
        try {
            Laptops updatedLaptop = ecommerceService.updateLaptop(id, name, description, price, qty, file);
            return ResponseEntity.ok(updatedLaptop);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating laptop");
        }
    }

    @PutMapping("/admin/update/mobiles/{id}")
    public ResponseEntity<?> updateMobile(@PathVariable Long id, 
                                          @RequestParam String name, 
                                          @RequestParam String description, 
                                          @RequestParam int price, 
                                          @RequestParam int qty, 
                                          @RequestParam(required = false) MultipartFile file) {
        try {
            Mobiles updatedMobile = ecommerceService.updateMobiles(id, name, description, price, qty, file);
            return ResponseEntity.ok(updatedMobile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating mobile");
        }
    }

    @PutMapping("/admin/update/cosmetics/{id}")
    public ResponseEntity<?> updateCosmetic(@PathVariable Long id, 
                                            @RequestParam String name, 
                                            @RequestParam String description, 
                                            @RequestParam int price, 
                                            @RequestParam int qty, 
                                            @RequestParam(required = false) MultipartFile file) {
        try {
            Cosmetics updatedCosmetic = ecommerceService.updateCosmetics(id, name, description, price, qty, file);
            return ResponseEntity.ok(updatedCosmetic);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating cosmetic");
        }
    }
    
    @PutMapping("/admin/update/toys/{id}")
    public ResponseEntity<?> updateToy(@PathVariable Long id, 
                                       @RequestParam String name, 
                                       @RequestParam String description, 
                                       @RequestParam int price, 
                                       @RequestParam int qty, 
                                       @RequestParam(required = false) MultipartFile file) {
        try {
            Toys updatedToy = ecommerceService.updateToys(id, name, description, price, qty, file);
            return ResponseEntity.ok(updatedToy);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating toy");
        }
    }

    @PutMapping("/admin/update/women/{id}")
    public ResponseEntity<?> updateWomenProduct(@PathVariable Long id, 
                                                @RequestParam String name, 
                                                @RequestParam String description, 
                                                @RequestParam int price, 
                                                @RequestParam int qty, 
                                                @RequestParam(required = false) MultipartFile file) {
        try {
            WomenClothing updatedWomenProduct = ecommerceService.updateWomenClothing(id, name, description, price, qty, file);
            return ResponseEntity.ok(updatedWomenProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating women product");
        }
    }

    @PutMapping("/admin/update/men/{id}")
    public ResponseEntity<?> updateMenProduct(@PathVariable Long id, 
                                               @RequestParam String name, 
                                               @RequestParam String description, 
                                               @RequestParam int price, 
                                               @RequestParam int qty, 
                                               @RequestParam(required = false) MultipartFile file) {
        try {
            MenClothing updatedMenProduct = ecommerceService.updateMenClothing(id, name, description, price, qty, file);
            return ResponseEntity.ok(updatedMenProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating men product");
        }
    }

    @PutMapping("/admin/update/kids/{id}")
    public ResponseEntity<?> updateKidsProduct(@PathVariable Long id, 
                                                @RequestParam String name, 
                                                @RequestParam String description, 
                                                @RequestParam int price, 
                                                @RequestParam int qty, 
                                                @RequestParam(required = false) MultipartFile file) {
        try {
            KidsClothing updatedKidsProduct = ecommerceService.updateKidsClothing(id, name, description, price, qty, file);
            return ResponseEntity.ok(updatedKidsProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating kids product");
        }
    }

    @PutMapping("/admin/update/grocery/{id}")
    public ResponseEntity<?> updateGroceryProduct(@PathVariable Long id, 
                                                  @RequestParam String name, 
                                                  @RequestParam String description, 
                                                  @RequestParam int price, 
                                                  @RequestParam int qty, 
                                                  @RequestParam(required = false) MultipartFile file) {
        try {
            Grocery updatedGroceryProduct = ecommerceService.updateGrocery(id, name, description, price, qty, file);
            return ResponseEntity.ok(updatedGroceryProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating grocery product");
        }
    }

    @PutMapping("/admin/update/footwear/{id}")
    public ResponseEntity<?> updateFootwear(@PathVariable Long id, 
                                            @RequestParam String name, 
                                            @RequestParam String description, 
                                            @RequestParam int price, 
                                            @RequestParam int qty, 
                                            @RequestParam(required = false) MultipartFile file) {
        try {
            Footwear updatedFootwear = ecommerceService.updateFootwear(id, name, description, price, qty, file);
            return ResponseEntity.ok(updatedFootwear);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating footwear");
        }
    }

    @PutMapping("/admin/update/electronics/{id}")
    public ResponseEntity<?> updateElectronics(@PathVariable Long id, 
                                               @RequestParam String name, 
                                               @RequestParam String description, 
                                               @RequestParam int price, 
                                               @RequestParam int qty, 
                                               @RequestParam(required = false) MultipartFile file) {
        try {
            Electronics updatedElectronics = ecommerceService.updateElectronics(id, name, description, price, qty, file);
            return ResponseEntity.ok(updatedElectronics);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating electronics");
        }
    }

    @DeleteMapping("/admin/delete/laptops/{id}")
    public ResponseEntity<?> deleteLaptop(@PathVariable Long id) {
        try {
            ecommerceService.deleteLaptop(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Laptop deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting laptop");
        }
    }

    @DeleteMapping("/admin/delete/mobiles/{id}")
    public ResponseEntity<?> deleteMobile(@PathVariable Long id) {
        try {
            ecommerceService.deleteMobiles(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Mobile deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting mobile");
        }
    }

    @DeleteMapping("/admin/delete/cosmetics/{id}")
    public ResponseEntity<?> deleteCosmetic(@PathVariable Long id) {
        try {
            ecommerceService.deleteCosmetics(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cosmetic deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting cosmetic");
        }
    }
    
    @DeleteMapping("/admin/delete/footwear/{id}")
    public ResponseEntity<?> deleteFootwear(@PathVariable Long id) {
        try {
            ecommerceService.deleteFootwear(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Footwear deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting footwear");
        }
    }

    @DeleteMapping("/admin/delete/toys/{id}")
    public ResponseEntity<?> deleteToy(@PathVariable Long id) {
        try {
            ecommerceService.deleteToys(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Toy deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting toy");
        }
    }

    @DeleteMapping("/admin/delete/electronics/{id}")
    public ResponseEntity<?> deleteElectronic(@PathVariable Long id) {
        try {
            ecommerceService.deleteElectronics(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Electronic item deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting electronic item");
        }
    }

    @DeleteMapping("/admin/delete/women/{id}")
    public ResponseEntity<?> deleteWomenItem(@PathVariable Long id) {
        try {
            ecommerceService.deleteWomen(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Women item deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting women item");
        }
    }

    @DeleteMapping("/admin/delete/men/{id}")
    public ResponseEntity<?> deleteMenItem(@PathVariable Long id) {
        try {
            ecommerceService.deleteMen(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Men item deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting men item");
        }
    }

    @DeleteMapping("/admin/delete/kids/{id}")
    public ResponseEntity<?> deleteKidsItem(@PathVariable Long id) {
        try {
            ecommerceService.deleteKids(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Kids item deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting kids item");
        }
    }

    @DeleteMapping("/admin/delete/grocery/{id}")
    public ResponseEntity<?> deleteGroceryItem(@PathVariable Long id) {
        try {
            ecommerceService.deleteGrocery(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Grocery item deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting grocery item");
        }
    }

	}
