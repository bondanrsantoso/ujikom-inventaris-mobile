package com.example.inventaris.Model;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("password")
	private String password;

	@SerializedName("nip")
	private String nip;

	@SerializedName("nama_pegawai")
	private String namaPegawai;

	@SerializedName("api_token")
	private String apiToken;

	@SerializedName("alamat")
	private String alamat;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setNip(String nip){
		this.nip = nip;
	}

	public String getNip(){
		return nip;
	}

	public void setNamaPegawai(String namaPegawai){
		this.namaPegawai = namaPegawai;
	}

	public String getNamaPegawai(){
		return namaPegawai;
	}

	public void setApiToken(String apiToken){
		this.apiToken = apiToken;
	}

	public String getApiToken(){
		return apiToken;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"password = '" + password + '\'' + 
			",nip = '" + nip + '\'' + 
			",nama_pegawai = '" + namaPegawai + '\'' + 
			",api_token = '" + apiToken + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}
}