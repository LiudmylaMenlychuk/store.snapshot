package store.snapshot.model;

import java.io.Serializable;


@Entity
@Table(name="Product")
public class Product implements Serializable {
	private int id;
	private String title;
	private String description;

	...

	@Id
	@Column(name="productId")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	....

}
