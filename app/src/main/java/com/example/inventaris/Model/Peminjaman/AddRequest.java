package com.example.inventaris.Model.Peminjaman;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AddRequest{

	@SerializedName("takeaway_date")
	private String takeawayDate;

	@SerializedName("amount")
	private List<Integer> amount;

	@SerializedName("api_token")
	private String apiToken;

	@SerializedName("inventaris_id")
	private List<String> inventarisId;

	@SerializedName("return_date")
	private String returnDate;

	public void setTakeawayDate(String takeawayDate){
		this.takeawayDate = takeawayDate;
	}

	public String getTakeawayDate(){
		return takeawayDate;
	}

	public void setAmount(List<Integer> amount){
		this.amount = amount;
	}

	public List<Integer> getAmount(){
		return amount;
	}

	public void setApiToken(String apiToken){
		this.apiToken = apiToken;
	}

	public String getApiToken(){
		return apiToken;
	}

	public void setInventarisId(List<String> inventarisId){
		this.inventarisId = inventarisId;
	}

	public List<String> getInventarisId(){
		return inventarisId;
	}

	public void setReturnDate(String returnDate){
		this.returnDate = returnDate;
	}

	public String getReturnDate(){
		return returnDate;
	}

	@Override
 	public String toString(){
		return 
			"AddRequest{" + 
			"takeaway_date = '" + takeawayDate + '\'' + 
			",amount = '" + amount + '\'' + 
			",api_token = '" + apiToken + '\'' + 
			",inventaris_id = '" + inventarisId + '\'' + 
			",return_date = '" + returnDate + '\'' + 
			"}";
		}
}