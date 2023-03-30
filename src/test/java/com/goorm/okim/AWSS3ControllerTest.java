package com.goorm.okim;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;


import java.io.FileInputStream;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("local")
class AmazonS3ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName("uploadFiles_S3로 파일 업로드")
    @Test
    public void testUploadFiles() throws Exception {

        // Given
        String name = "files";
        String contentType = "text/plain";
        String path = "src/main/resources/static/temp";
        String fileType = "temp";
        String originalFileName01 = "temp01.txt";


        MockMultipartFile multipartFile01 = new MockMultipartFile(
                name,
                originalFileName01,
                contentType,
                new FileInputStream(path + "/" + originalFileName01));



        // When
        ResultActions resultActions = mockMvc.perform(
                multipart("/uploads")
                        .file(multipartFile01)
                        .param("fileType", fileType)
        );

        // Then
        ResultActions resultActions1 = resultActions.andExpect(status().isOk())
                        .andDo(print());

    }
}