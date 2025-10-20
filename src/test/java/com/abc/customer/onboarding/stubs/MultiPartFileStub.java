package com.abc.customer.onboarding.stubs;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class MultiPartFileStub implements MultipartFile {

    private String type;
    private String file;
    private String contentType;
    private byte[] imageData;

    public MultiPartFileStub(String type, String file, String contentType, byte[] imageData) {
        this.type = type;
        this.file = file;
        this.contentType = contentType;
        this.imageData = imageData;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getOriginalFilename() {
        return "";
    }

    @Override
    public String getContentType() {
        return "";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return new byte[0];
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.imageData);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }

    private String calculateHash()
            throws IOException, NoSuchAlgorithmException {

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 is altijd beschikbaar in de JVM
            throw new RuntimeException("SHA-256 algoritme niet beschikbaar", e);
        }


        try (InputStream inputStream = this.getInputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = inputStream.read(imageData)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }

        byte[] hashBytes = digest.digest();
        return HexFormat.of().formatHex(hashBytes);
    }

}
