package com.goorm.okim.service;

import com.goorm.okim.common.Response;
import com.goorm.okim.domain.Item;
import com.goorm.okim.infra.repository.ItemRepository;
import com.goorm.okim.presentation.domain.item.RequestCreateItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public ResponseEntity<?> createItem(RequestCreateItemDto itemDto){
        return Response.createdWithBody(itemRepository.save(itemDto.toEntity()));
    }

    @Transactional
    public ResponseEntity<?> updateItem(long itemId, String title){
        Optional<Item> item = itemRepository.findById(itemId);
        item.get().update(title);
        return Response.success("success");
    }
}
