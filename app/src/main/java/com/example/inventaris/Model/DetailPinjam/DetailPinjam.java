package com.example.inventaris.Model.DetailPinjam;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailPinjam{

	@SerializedName("id_peminjaman")
	private int idPeminjaman;

	@SerializedName("nama_pegawai")
	private String namaPegawai;

	@SerializedName("tanggal_pinjam")
	private int tanggalPinjam;

	@SerializedName("tanggal_kembali")
	private int tanggalKembali;

	@SerializedName("peminjaman_details")
	private List<PeminjamanDetailsItem> peminjamanDetails;

	@SerializedName("kembali")
	private boolean kembali;

	@SerializedName("status")
	private String status;

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

	public void setPeminjamanDetails(List<PeminjamanDetailsItem> peminjamanDetails){
		this.peminjamanDetails = peminjamanDetails;
	}

	public List<PeminjamanDetailsItem> getPeminjamanDetails(){
		return peminjamanDetails;
	}

	public void setKembali(boolean kembali){
		this.kembali = kembali;
	}

	public boolean isKembali(){
		return kembali;
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
			"DetailPinjam{" + 
			"id_peminjaman = '" + idPeminjaman + '\'' + 
			",nama_pegawai = '" + namaPegawai + '\'' + 
			",tanggal_pinjam = '" + tanggalPinjam + '\'' + 
			",tanggal_kembali = '" + tanggalKembali + '\'' + 
			",peminjaman_details = '" + peminjamanDetails + '\'' + 
			",kembali = '" + kembali + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}