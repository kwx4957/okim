package com.goorm.okim.presentation;


import com.goorm.okim.common.Response;
import com.goorm.okim.jwt.Login;
import com.goorm.okim.presentation.domain.item.RequestCreateItemDto;
import com.goorm.okim.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item")
    public ResponseEntity<?> createItem(@Login long userId,
                                        @RequestBody RequestCreateItemDto itemDto){
        if(itemDto.getTitle().isBlank()){
            return Response.failBadRequest(-1,"내용은 필수입니다");
        }
        return itemService.createItem(itemDto);
    }
//    @PostMapping("/items")
//    public ResponseEntity<?> createItem(){
//    }
//    @PostMapping("/items")
//    public ResponseEntity<?> createItem(){
//    }
//    @DeleteMapping("/items")
//    public ResponseEntity<?> createItem(){
//    }

}
