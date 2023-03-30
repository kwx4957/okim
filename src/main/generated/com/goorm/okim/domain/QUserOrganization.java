package com.goorm.okim.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserOrganization is a Querydsl query type for UserOrganization
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserOrganization extends EntityPathBase<UserOrganization> {

    private static final long serialVersionUID = -1230327373L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserOrganization userOrganization = new QUserOrganization("userOrganization");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOrganization organization;

    public final QUser user;

    public QUserOrganization(String variable) {
        this(UserOrganization.class, forVariable(variable), INITS);
    }

    public QUserOrganization(Path<? extends UserOrganization> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserOrganization(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserOrganization(PathMetadata metadata, PathInits inits) {
        this(UserOrganization.class, metadata, inits);
    }

    public QUserOrganization(Class<? extends UserOrganization> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.organization = inits.isInitialized("organization") ? new QOrganization(forProperty("organization")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

