package com.goorm.okim.presentation.item;

import com.goorm.okim.exception.BusinessLogicException;
import com.goorm.okim.exception.ErrorCodeMessage;
import com.goorm.okim.infra.query.TaskQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class OnlyOwnerAspect {

    private final TaskQuery taskQuery;
    
    @Before("@annotation(com.goorm.okim.presentation.item.OwnerOnly)")
    public void checkAuthorId(JoinPoint joinPoint) {
        long userId = Long.parseLong(joinPoint.getArgs()[0].toString());
        long itemId = Long.parseLong(joinPoint.getArgs()[1].toString());
        Boolean authorized  = taskQuery.isAuthorizedForItem(userId, itemId);
        if(!authorized){
            throw new BusinessLogicException(ErrorCodeMessage.NOT_ACCESSIBLE);
        }
    }


    @Before("@annotation(com.goorm.okim.presentation.item.OwnerOnlyCreate)")
    public void checkAuthorIdWhenCreateItem(JoinPoint joinPoint) {
        RequestCreateItemDto itemDto = (RequestCreateItemDto) joinPoint.getArgs()[1];
        long userId = (long) joinPoint.getArgs()[0];
        long taskId = itemDto.getTaskId();

        Boolean authorized = taskQuery.isAuthorizedForTask(userId, taskId);
        if (!authorized) {
            throw new BusinessLogicException(ErrorCodeMessage.NOT_ACCESSIBLE);
        }

    }
}





