package org.discord.api.controller;

import org.discord.api.service.FileIOService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileIOService fileIOService;

    @GetMapping("/img")
    public void getImage(HttpServletResponse response,@RequestParam("name") String id) throws IOException {
        System.out.println(id);
        response.getOutputStream().write(fileIOService.getFile(id));
    }
}
