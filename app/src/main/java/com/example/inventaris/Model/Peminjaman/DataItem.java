package com.example.inventaris.Model.Peminjaman;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("no")
	private int no;

	@SerializedName("id_peminjaman")
	private int idPeminjaman;

	@SerializedName("nama_pegawai")
	private String namaPegawai;

	@SerializedName("tanggal_pinjam")
	private int tanggalPinjam;

	@SerializedName("tanggal_kembali")
	private int tanggalKembali;

	@SerializedName("status")
	private String status;

	public void setNo(int no){
		this.no = no;
	}

	public int getNo(){
		return no;
	}

	public void setIdPeminjaman(int idPeminjaman){
		this.idPeminjaman = idPeminjaman;
	}

	public int getIdPeminjaman(){
		return idPeminjaman;
	}

	public void setNamaPegawai(String namaPegawai){
		this.namaPegawai = namaPegawai;
	}

	public String getNamaPegawai(){
		return namaPegawai;
	}

	public void setTanggalPinjam(int tanggalPinjam){
		this.tanggalPinjam = tanggalPinjam;
	}

	public int getTanggalPinjam(){
		return tanggalPinjam;
	}

	public void setTanggalKembali(int tanggalKembali){
		this.tanggalKembali = tanggalKembali;
	}

	public int getTanggalKembali(){
		return tanggalKembali;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"no = '" + no + '\'' + 
			",id_peminjaman = '" + idPeminjaman + '\'' + 
			",nama_pegawai = '" + namaPegawai + '\'' + 
			",tanggal_pinjam = '" + tanggalPinjam + '\'' + 
			",tanggal_kembali = '" + tanggalKembali + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}