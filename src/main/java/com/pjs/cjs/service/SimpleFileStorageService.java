package com.pjs.cjs.service;

import com.pjs.cjs.exception.FileParsingException;
import com.pjs.cjs.exception.FileStorageException;
import com.pjs.cjs.exception.MyFileNotFoundException;
import com.pjs.cjs.model.FileDetails;
import com.pjs.cjs.model.SimpleFile;
import com.pjs.cjs.repository.FileDetailsRepository;
import com.pjs.cjs.repository.SimpleFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class SimpleFileStorageService {

    private final SimpleFileRepository simpleFileRepository;
    private final FileParsingService fileParsingService;
    private final FileDetailsRepository fileDetailsRepository;


    public SimpleFileStorageService(SimpleFileRepository simpleFileRepository, FileParsingService fileParsingService, FileDetailsRepository fileDetailsRepository) {
        this.simpleFileRepository = simpleFileRepository;
        this.fileParsingService = fileParsingService;
        this.fileDetailsRepository = fileDetailsRepository;
    }

    public SimpleFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            SimpleFile dbFile = new SimpleFile(fileName, file.getContentType(), file.getBytes());
            dbFile = simpleFileRepository.save(dbFile);

            FileDetails fileDetails = parseFile(file);
            fileDetails.setFile(dbFile);

            fileDetailsRepository.save(fileDetails);

            return  dbFile;

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private FileDetails parseFile(MultipartFile file) {
        try {
            return fileParsingService.parseFile(file);
        } catch (IOException e) {
            throw new FileParsingException("Could not parse file " + file.getName() + ". Please try again!", e);
        }
    }

    public SimpleFile getFile(String fileId) {
        return simpleFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}
