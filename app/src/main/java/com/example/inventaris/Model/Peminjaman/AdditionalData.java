package com.example.inventaris.Model.Peminjaman;

import com.google.gson.annotations.SerializedName;

public class AdditionalData{

	@SerializedName("recordsFiltered")
	private int recordsFiltered;

	@SerializedName("recordsTotal")
	private int recordsTotal;

	public void setRecordsFiltered(int recordsFiltered){
		this.recordsFiltered = recordsFiltered;
	}

	public int getRecordsFiltered(){
		return recordsFiltered;
	}

	public void setRecordsTotal(int recordsTotal){
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsTotal(){
		return recordsTotal;
	}

	@Override
 	public String toString(){
		return 
			"AdditionalData{" + 
			"recordsFiltered = '" + recordsFiltered + '\'' + 
			",recordsTotal = '" + recordsTotal + '\'' + 
			"}";
		}
}