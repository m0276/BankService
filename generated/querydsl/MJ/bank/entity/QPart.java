package MJ.bank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPart is a Querydsl query type for Part
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPart extends EntityPathBase<Part> {

    private static final long serialVersionUID = -58126659L;

    public static final QPart part = new QPart("part");

    public final ListPath<Employee, QEmployee> employees = this.<Employee, QEmployee>createList("employees", Employee.class, QEmployee.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> establish = createDate("establish", java.time.LocalDate.class);

    public final StringPath explanation = createString("explanation");

    public final StringPath partName = createString("partName");

    public QPart(String variable) {
        super(Part.class, forVariable(variable));
    }

    public QPart(Path<? extends Part> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPart(PathMetadata metadata) {
        super(Part.class, metadata);
    }

}

