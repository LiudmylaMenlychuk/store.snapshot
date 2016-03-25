package store.snapshot.service;
import store.snapshot.model.Product;

@Service
public interface IProductService {
	List<Product> getAllProducts();
	public boolean addProduct(Product product, MultipartFile img);
	public void removeProduct(Product product);
	public Product getProduct(int prodId);
}
