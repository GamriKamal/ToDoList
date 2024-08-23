package com.example.final_project;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CRUDOperation {
    OperationType value();
}

enum OperationType{
    CREATE, READ, UPDATE, DELETE
}
