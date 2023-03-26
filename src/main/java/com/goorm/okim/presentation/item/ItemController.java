package com.goorm.okim.presentation.item;


import com.goorm.okim.common.Response;
import com.goorm.okim.jwt.Login;
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
    @PutMapping("/items/{itemId}")
    public ResponseEntity<?> updateItem(@PathVariable("itemId") long itemId,
                                        @RequestBody String title){
        return itemService.updateItem(itemId,title);
    }
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> deletaItem(@PathVariable("itemId") long itemId){
        return itemService.deleteItem(itemId);
    }

    @PutMapping("/items/{itemId}/done")
    public ResponseEntity<?> revertDone(@PathVariable("itemId") long itemId){
        return itemService.revertDone(itemId);
    }

}
