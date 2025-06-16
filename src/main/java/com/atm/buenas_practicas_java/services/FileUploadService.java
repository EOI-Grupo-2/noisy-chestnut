package com.atm.buenas_practicas_java.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {

    // Carpeta en la raíz del proyecto para desarrollo
    private final String uploadDir = "uploads/concerts/";

    public String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("El archivo está vacío");
        }

        // Validar que sea una imagen
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("El archivo debe ser una imagen");
        }

        // Crear directorio si no existe
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generar nombre único para el archivo
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;

        // Guardar archivo
        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retornar la URL que Spring Boot puede servir
        return "/uploads/concerts/" + newFilename;
    }

    public void deleteImage(String imageUrl) {
        if (imageUrl != null && imageUrl.startsWith("/uploads/concerts/")) {
            try {
                String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                Path filePath = Paths.get(uploadDir + filename);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                System.err.println("Error deleting file: " + e.getMessage());
            }
        }
    }
}
