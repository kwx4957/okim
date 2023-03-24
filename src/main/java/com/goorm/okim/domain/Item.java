package com.goorm.okim.domain;

import com.goorm.okim.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@DynamicInsert
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String title;
    @Column
    @ColumnDefault("0")
    private boolean isDone;
    @Column
    private long taskId;
    @Column
    @CreatedDate
    private LocalDateTime finishedAt;

    public Item update(String title){
        this.title = title;
        return this;
    }

    public Item revertDone(){
        isDone = !isDone;
        return this;
    }

    @Builder
    public Item(String title, long taskId) {
        this.title = title;
        this.taskId = taskId;
    }
}
