package com.example.inventaris.Model.Inventaris;

import com.google.gson.annotations.SerializedName;

public class AdditionalData{

	@SerializedName("total_rows_filtered")
	private int totalRowsFiltered;

	@SerializedName("total_rows")
	private int totalRows;

	public void setTotalRowsFiltered(int totalRowsFiltered){
		this.totalRowsFiltered = totalRowsFiltered;
	}

	public int getTotalRowsFiltered(){
		return totalRowsFiltered;
	}

	public void setTotalRows(int totalRows){
		this.totalRows = totalRows;
	}

	public int getTotalRows(){
		return totalRows;
	}

	@Override
 	public String toString(){
		return 
			"AdditionalData{" + 
			"total_rows_filtered = '" + totalRowsFiltered + '\'' + 
			",total_rows = '" + totalRows + '\'' + 
			"}";
		}
}