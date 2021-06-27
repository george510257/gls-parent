package com.gls.demo.security.web.controller;

import com.gls.demo.security.web.model.FileModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author george
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    private static final String FILE_FOLDER = "G:\\temp";

    @PostMapping
    public FileModel upload(MultipartFile file) throws Exception {
        FileModel fileModel = new FileModel();
        log.info("file name: {}", file.getName());
        log.info("original file name: {}", file.getOriginalFilename());
        log.info("file size: {}", file.getSize());
        File localFile = new File(FILE_FOLDER, System.currentTimeMillis() + ".txt");
        file.transferTo(localFile);
        fileModel.setPath(localFile.getAbsolutePath());
        return fileModel;
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try (InputStream inputStream = new FileInputStream(new File(FILE_FOLDER, id + ".txt"));
             OutputStream outputStream = response.getOutputStream();) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
    }
}
