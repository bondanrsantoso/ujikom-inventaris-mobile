package com.example.inventaris.Model.DetailPinjam;

import com.google.gson.annotations.SerializedName;

public class PeminjamanDetailsItem{

	@SerializedName("kode_inventaris")
	private String kodeInventaris;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("kondisi")
	private String kondisi;

	@SerializedName("kode_jenis")
	private String kodeJenis;

	@SerializedName("tanggal_register")
	private String tanggalRegister;

	@SerializedName("stok")
	private int stok;

	@SerializedName("nama_ruang")
	private String namaRuang;

	@SerializedName("id_inventaris")
	private int idInventaris;

	@SerializedName("nama")
	private String nama;

	@SerializedName("jumlah")
	private int jumlah;

	@SerializedName("id_jenis")
	private int idJenis;

	@SerializedName("url_gambar")
	private String urlGambar;

	@SerializedName("id_ruang")
	private int idRuang;

	@SerializedName("nama_jenis")
	private String namaJenis;

	@SerializedName("kode_ruang")
	private String kodeRuang;

	@SerializedName("kode_petugas")
	private String kodePetugas;

	public void setKodeInventaris(String kodeInventaris){
		this.kodeInventaris = kodeInventaris;
	}

	public String getKodeInventaris(){
		return kodeInventaris;
	}

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setKondisi(String kondisi){
		this.kondisi = kondisi;
	}

	public String getKondisi(){
		return kondisi;
	}

	public void setKodeJenis(String kodeJenis){
		this.kodeJenis = kodeJenis;
	}

	public String getKodeJenis(){
		return kodeJenis;
	}

	public void setTanggalRegister(String tanggalRegister){
		this.tanggalRegister = tanggalRegister;
	}

	public String getTanggalRegister(){
		return tanggalRegister;
	}

	public void setStok(int stok){
		this.stok = stok;
	}

	public int getStok(){
		return stok;
	}

	public void setNamaRuang(String namaRuang){
		this.namaRuang = namaRuang;
	}

	public String getNamaRuang(){
		return namaRuang;
	}

	public void setIdInventaris(int idInventaris){
		this.idInventaris = idInventaris;
	}

	public int getIdInventaris(){
		return idInventaris;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setJumlah(int jumlah){
		this.jumlah = jumlah;
	}

	public int getJumlah(){
		return jumlah;
	}

	public void setIdJenis(int idJenis){
		this.idJenis = idJenis;
	}

	public int getIdJenis(){
		return idJenis;
	}

	public void setUrlGambar(String urlGambar){
		this.urlGambar = urlGambar;
	}

	public String getUrlGambar(){
		return urlGambar;
	}

	public void setIdRuang(int idRuang){
		this.idRuang = idRuang;
	}

	public int getIdRuang(){
		return idRuang;
	}

	public void setNamaJenis(String namaJenis){
		this.namaJenis = namaJenis;
	}

	public String getNamaJenis(){
		return namaJenis;
	}

	public void setKodeRuang(String kodeRuang){
		this.kodeRuang = kodeRuang;
	}

	public String getKodeRuang(){
		return kodeRuang;
	}

	public void setKodePetugas(String kodePetugas){
		this.kodePetugas = kodePetugas;
	}

	public String getKodePetugas(){
		return kodePetugas;
	}

	@Override
 	public String toString(){
		return 
			"PeminjamanDetailsItem{" + 
			"kode_inventaris = '" + kodeInventaris + '\'' + 
			",keterangan = '" + keterangan + '\'' + 
			",kondisi = '" + kondisi + '\'' + 
			",kode_jenis = '" + kodeJenis + '\'' + 
			",tanggal_register = '" + tanggalRegister + '\'' + 
			",stok = '" + stok + '\'' + 
			",nama_ruang = '" + namaRuang + '\'' + 
			",id_inventaris = '" + idInventaris + '\'' + 
			",nama = '" + nama + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",id_jenis = '" + idJenis + '\'' + 
			",url_gambar = '" + urlGambar + '\'' + 
			",id_ruang = '" + idRuang + '\'' + 
			",nama_jenis = '" + namaJenis + '\'' + 
			",kode_ruang = '" + kodeRuang + '\'' + 
			",kode_petugas = '" + kodePetugas + '\'' + 
			"}";
		}
}