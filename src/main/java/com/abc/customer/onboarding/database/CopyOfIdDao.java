package com.abc.customer.onboarding.database;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;


@Entity
@Table(name = "ID_PROOF")
public class CopyOfIdDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long Id;

    @Column(name = "id_number")
    private String idNumber;
    @Basic
    @Column(name = "photo", columnDefinition = "BYTEA")
    private byte[] photo;
    @OneToMany(mappedBy = "copyOfId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OnboardingDao> onboardingDaos;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public List<OnboardingDao> getOnboardingDaos() {
        return onboardingDaos;
    }

    public void setOnboardingDaos(List<OnboardingDao> onboardingDaos) {
        this.onboardingDaos = onboardingDaos;
    }
}
