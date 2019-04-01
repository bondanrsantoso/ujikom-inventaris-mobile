package com.example.inventaris.Model.Peminjaman;

import com.google.gson.annotations.SerializedName;

public class Request{

	@SerializedName("offset")
	private int offset;

	@SerializedName("api_token")
	private String apiToken;

	@SerializedName("limit")
	private int limit;

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setApiToken(String apiToken){
		this.apiToken = apiToken;
	}

	public String getApiToken(){
		return apiToken;
	}

	public void setLimit(int limit){
		this.limit = limit;
	}

	public int getLimit(){
		return limit;
	}

	@Override
 	public String toString(){
		return 
			"Request{" + 
			"offset = '" + offset + '\'' + 
			",api_token = '" + apiToken + '\'' + 
			",limit = '" + limit + '\'' + 
			"}";
		}
}