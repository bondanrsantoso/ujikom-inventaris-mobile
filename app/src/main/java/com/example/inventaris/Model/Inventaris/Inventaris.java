package com.example.inventaris.Model.Inventaris;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Inventaris{

	@SerializedName("additional_data")
	private AdditionalData additionalData;

	@SerializedName("data")
	private List<DataItem> data;

	public void setAdditionalData(AdditionalData additionalData){
		this.additionalData = additionalData;
	}

	public AdditionalData getAdditionalData(){
		return additionalData;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"Inventaris{" + 
			"additional_data = '" + additionalData + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}