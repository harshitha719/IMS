package Bean;

public class Product {
	private String Product_ID;
	private String Product;
    private String Model;
    private String Manufacturer;
    private String Type_Code;
    private int Location_Code;
    private float MSRP;
    private float Unit_Cost;
    private float Discount_Rate ;
    private int Stock_Qty;
    
    public Product(String Product_ID, String Product, String Model,String Manufacturer,String Type_Code,int Location_Code,
    		float MSRP,float Unit_Cost,float Discount_Rate,int Stock_Qty){
        this.Product_ID = Product_ID;
        this.Product = Product;
        this.Model = Model;
        this.Manufacturer = Manufacturer;
        this.Type_Code = Type_Code;
        this.Location_Code = Location_Code;
        this.MSRP = MSRP;
        this.Unit_Cost = Unit_Cost;
        this.Discount_Rate = Discount_Rate;
        this.Stock_Qty = Stock_Qty;
    }
    
    public String getProduct_ID() {
		return Product_ID;
	}
	public void setProduct_ID(String product_ID) {
		Product_ID = product_ID;
	}
	public String getProduct() {
		return Product;
	}
	public void setProduct(String product) {
		Product = product;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getManufacturer() {
		return Manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		Manufacturer = manufacturer;
	}
	public String getType_Code() {
		return Type_Code;
	}
	public void setType_Code(String type_Code) {
		Type_Code = type_Code;
	}
	public int getLocation_Code() {
		return Location_Code;
	}
	public void setLocation_Code(int location_Code) {
		Location_Code = location_Code;
	}
	public float getMSRP() {
		return MSRP;
	}
	public void setMSRP(float mSRP) {
		MSRP = mSRP;
	}
	public float getUnit_Cost() {
		return Unit_Cost;
	}
	public void setUnit_Cost(float unit_Cost) {
		Unit_Cost = unit_Cost;
	}
	public float getDiscount_Rate() {
		return Discount_Rate;
	}
	public void setDiscount_Rate(float discount_Rate) {
		Discount_Rate = discount_Rate;
	}
	public int getStock_Qty() {
		return Stock_Qty;
	}
	public void setStock_Qty(int stock_Qty) {
		Stock_Qty = stock_Qty;
	}
	
}
