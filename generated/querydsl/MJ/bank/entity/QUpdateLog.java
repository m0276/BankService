package MJ.bank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpdateLog is a Querydsl query type for UpdateLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUpdateLog extends EntityPathBase<UpdateLog> {

    private static final long serialVersionUID = -1802906319L;

    public static final QUpdateLog updateLog = new QUpdateLog("updateLog");

    public final StringPath currContent = createString("currContent");

    public final NumberPath<Long> employeeId = createNumber("employeeId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ip = createString("ip");

    public final StringPath memo = createString("memo");

    public final StringPath prevContent = createString("prevContent");

    public final StringPath propertyName = createString("propertyName");

    public final DateTimePath<java.time.LocalDateTime> updateTime = createDateTime("updateTime", java.time.LocalDateTime.class);

    public final EnumPath<UpdateType> updateType = createEnum("updateType", UpdateType.class);

    public QUpdateLog(String variable) {
        super(UpdateLog.class, forVariable(variable));
    }

    public QUpdateLog(Path<? extends UpdateLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpdateLog(PathMetadata metadata) {
        super(UpdateLog.class, metadata);
    }

}

