package MJ.bank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployee is a Querydsl query type for Employee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployee extends EntityPathBase<Employee> {

    private static final long serialVersionUID = 1613831288L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployee employee = new QEmployee("employee");

    public final DatePath<java.time.LocalDate> dateOfJoining = createDate("dateOfJoining", java.time.LocalDate.class);

    public final StringPath email = createString("email");

    public final ComparablePath<java.util.UUID> employeeNumber = createComparable("employeeNumber", java.util.UUID.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QPart part;

    public final QProfile profile;

    public final EnumPath<Rank> rank = createEnum("rank", Rank.class);

    public final EnumPath<EmployeeStatus> status = createEnum("status", EmployeeStatus.class);

    public QEmployee(String variable) {
        this(Employee.class, forVariable(variable), INITS);
    }

    public QEmployee(Path<? extends Employee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployee(PathMetadata metadata, PathInits inits) {
        this(Employee.class, metadata, inits);
    }

    public QEmployee(Class<? extends Employee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.part = inits.isInitialized("part") ? new QPart(forProperty("part")) : null;
        this.profile = inits.isInitialized("profile") ? new QProfile(forProperty("profile")) : null;
    }

}

