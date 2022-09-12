package application;

public class Order {
	private String orderDate;
	private String region;
	private String rep1;
	private String rep2;
	private String item;
	private float units;
	private float unitCost;
	private float total;

	public Order(String orderDate, String region, String rep1, String rep2, String item, float units, float unitCost) {
		this.orderDate = orderDate;
		this.region = region;
		this.rep1 = rep1;
		this.rep2 = rep2;
		this.item = item;
		this.units = units;
		this.unitCost = unitCost;

	}

	public String getOrderDate() {
		return orderDate;
	}

	public String getRegion() {
		return region;
	}

	public String getRep1() {
		return rep1;
	}

	public String getRep2() {
		return rep2;
	}

	public String getItem() {
		return item;
	}

	public float getUnits() {
		return units;
	}

	public float getUnitCost() {
		return unitCost;
	}

	public float getTotal() {

		return total = units * unitCost;
	}
}
