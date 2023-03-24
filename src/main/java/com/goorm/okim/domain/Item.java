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
@Builder
@AllArgsConstructor
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
    @JoinColumn(name = "task_id")
    private long taskId;
    @Column
    @CreatedDate
    private LocalDateTime finishedAt;
}
