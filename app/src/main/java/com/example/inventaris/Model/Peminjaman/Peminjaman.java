package com.example.inventaris.Model.Peminjaman;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Peminjaman{

	@SerializedName("request")
	private Request request;

	@SerializedName("additional_data")
	private AdditionalData additionalData;

	@SerializedName("data")
	private List<DataItem> data;

	public void setRequest(Request request){
		this.request = request;
	}

	public Request getRequest(){
		return request;
	}

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
			"Peminjaman{" + 
			"request = '" + request + '\'' + 
			",additional_data = '" + additionalData + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}