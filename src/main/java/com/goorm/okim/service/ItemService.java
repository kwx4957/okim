package com.goorm.okim.service;

import com.goorm.okim.common.Response;
import com.goorm.okim.infra.repository.ItemRepository;
import com.goorm.okim.presentation.domain.item.RequestCreateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public ResponseEntity<?> createItem(RequestCreateItemDto itemDto){
        return Response.createdWithBody(itemRepository.save(itemDto.toEntity()));
    }
}
