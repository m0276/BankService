package MJ.bank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBackupLog is a Querydsl query type for BackupLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBackupLog extends EntityPathBase<BackupLog> {

    private static final long serialVersionUID = 1501645208L;

    public static final QBackupLog backupLog = new QBackupLog("backupLog");

    public final EnumPath<BackupStatus> backupStatus = createEnum("backupStatus", BackupStatus.class);

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> fileNumber = createNumber("fileNumber", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final StringPath worker = createString("worker");

    public QBackupLog(String variable) {
        super(BackupLog.class, forVariable(variable));
    }

    public QBackupLog(Path<? extends BackupLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBackupLog(PathMetadata metadata) {
        super(BackupLog.class, metadata);
    }

}

