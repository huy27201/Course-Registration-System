package org.main.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity(name = "Sinhvien")
public class Sinhvien {
    private String id;
    private String ho;
    private String ten;
    private Date ngaySinh;
    private String gioiTinh;
    private String lop;
    private String taiKhoan;

    @Id
    @Column(name = "ID", nullable = false, length = 8)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Ho", nullable = true, length = 50)
    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    @Basic
    @Column(name = "Ten", nullable = true, length = 10)
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Basic
    @Column(name = "NgaySinh", nullable = true)
    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    @Basic
    @Column(name = "GioiTinh", nullable = true, length = 3)
    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    @Basic
    @Column(name = "Lop", nullable = true, length = 6)
    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    @Basic
    @Column(name = "TaiKhoan", nullable = true, length = 8)
    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sinhvien sinhvien = (Sinhvien) o;

        if (id != null ? !id.equals(sinhvien.id) : sinhvien.id != null) return false;
        if (ho != null ? !ho.equals(sinhvien.ho) : sinhvien.ho != null) return false;
        if (ten != null ? !ten.equals(sinhvien.ten) : sinhvien.ten != null) return false;
        if (ngaySinh != null ? !ngaySinh.equals(sinhvien.ngaySinh) : sinhvien.ngaySinh != null) return false;
        if (gioiTinh != null ? !gioiTinh.equals(sinhvien.gioiTinh) : sinhvien.gioiTinh != null) return false;
        if (lop != null ? !lop.equals(sinhvien.lop) : sinhvien.lop != null) return false;
        if (taiKhoan != null ? !taiKhoan.equals(sinhvien.taiKhoan) : sinhvien.taiKhoan != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ho != null ? ho.hashCode() : 0);
        result = 31 * result + (ten != null ? ten.hashCode() : 0);
        result = 31 * result + (ngaySinh != null ? ngaySinh.hashCode() : 0);
        result = 31 * result + (gioiTinh != null ? gioiTinh.hashCode() : 0);
        result = 31 * result + (lop != null ? lop.hashCode() : 0);
        result = 31 * result + (taiKhoan != null ? taiKhoan.hashCode() : 0);
        return result;
    }
}
