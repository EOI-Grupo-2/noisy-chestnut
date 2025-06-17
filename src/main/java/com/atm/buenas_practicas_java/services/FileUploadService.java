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


    private final String baseUploadDir = "/var/lib/uploads/";
    private final String concertsDir = baseUploadDir + "concerts/";
    private final String placesDir = baseUploadDir + "places/";
    private final String usersDir = baseUploadDir + "users/";
    private final String publicationsDir = baseUploadDir + "publications/";

    private String saveImage(MultipartFile file, String type) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("El archivo está vacío");
        }

        // Validar que sea una imagen
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("El archivo debe ser una imagen");
        }

        // Determinar directorio según el tipo
        String uploadDir;
        switch (type.toLowerCase()) {
            case "concerts":
                uploadDir = concertsDir;
                break;
            case "places":
                uploadDir = placesDir;
                break;
            case "users":
                uploadDir = usersDir;
                break;
            case "publications":
                uploadDir = publicationsDir;
                break;
            default:
                throw new IOException("Tipo de archivo no válido: " + type);
        }

        // Crear directorio si no existe
        Path uploadPath = Paths.get(baseUploadDir);
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
        return "/uploads/" + newFilename;
    }

    // Método para eliminar imágenes (compatible con ambos tipos)
    public void deleteImage(String imageUrl) {
        if (imageUrl != null && imageUrl.startsWith("/uploads/")) {
            try {
                // Extraer el tipo y nombre del archivo de la URL
                String[] parts = imageUrl.split("/");
                if (parts.length >= 4) {
                    String type = parts[2]; // concerts o places
                    String filename = parts[3];
                    String uploadDir;
                    switch (type.toLowerCase()) {
                        case "concerts":
                            uploadDir = concertsDir;
                            break;
                        case "places":
                            uploadDir = placesDir;
                            break;
                        case "users":
                            uploadDir = usersDir;
                            break;
                        case "publications":
                            uploadDir = publicationsDir;
                            break;
                        default:
                            System.err.println("Tipo de archivo no reconocido en URL: " + imageUrl);
                            return;
                    }

                    Path filePath = Paths.get(uploadDir + filename);
                    Files.deleteIfExists(filePath);
                }
            } catch (IOException e) {
                System.err.println("Error deleting file: " + e.getMessage());
            }
        }
    }

    // Métodos específicos para mayor claridad (opcionales)
    public String saveConcertImage(MultipartFile file) throws IOException {
        return saveImage(file, "concerts");
    }

    public String savePlaceImage(MultipartFile file) throws IOException {
        return saveImage(file, "places");
    }

    public String savePublicationImage(MultipartFile file) throws IOException {
        return saveImage(file, "publications");
    }

    public String saveUserImage(MultipartFile file) throws IOException {
        return saveImage(file, "users");
    }
}