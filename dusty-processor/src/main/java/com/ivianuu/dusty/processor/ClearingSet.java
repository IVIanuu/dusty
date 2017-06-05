/*
 * Copyright 2017 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.dusty.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static com.google.auto.common.MoreElements.getPackage;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * @author Manuel Wrage (IVIanuu)
 */

class ClearingSet {

    private TypeName targetTypeName;
    private ClassName clearingClassName;

    private boolean isFinal;

    private List<FieldClearing> fieldClearings;

    private ClearingSet parentClearingSet;

    private ClearingSet(TypeName targetTypeName, ClassName clearingClassName, boolean isFinal
            ,List<FieldClearing> fieldClearings, ClearingSet parentClearingSet) {
        this.targetTypeName = targetTypeName;
        this.clearingClassName = clearingClassName;
        this.isFinal = isFinal;
        this.fieldClearings = fieldClearings;
        this.parentClearingSet = parentClearingSet;
    }

    JavaFile brewJava() {
        return JavaFile.builder(clearingClassName.packageName(), createType())
                .addFileComment("Generated code from AutoClearedValues. Do not modify!")
                .build();
    }

    private TypeSpec createType() {
        TypeSpec.Builder result = TypeSpec.classBuilder(clearingClassName.simpleName())
                .addModifiers(PUBLIC);

        if (isFinal) {
            result.addModifiers(FINAL);
        }

        if (parentClearingSet != null) {
            result.superclass(parentClearingSet.clearingClassName);
        }

        // build constructor
        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(PUBLIC)
                .addParameter(targetTypeName, "target");

        // if we have a parent we have to add super
        if (parentClearingSet != null) {
            constructorBuilder.addStatement("super(target)");

        }

        // add code to clear all fields
        for (FieldClearing fieldClearing : fieldClearings) {
            constructorBuilder.addStatement("target." + fieldClearing.getName() + " = null");
        }

        result.addMethod(constructorBuilder.build());

        return result.build();
    }

    static Builder newBuilder(TypeElement enclosingElement) {
        TypeMirror typeMirror = enclosingElement.asType();

        TypeName targetType = TypeName.get(typeMirror);
        if (targetType instanceof ParameterizedTypeName) {
            targetType = ((ParameterizedTypeName) targetType).rawType;
        }

        String packageName = getPackage(enclosingElement).getQualifiedName().toString();
        String className = enclosingElement.getQualifiedName().toString().substring(
                packageName.length() + 1).replace('.', '$');
        ClassName bindingClassName = ClassName.get(packageName, className + "_AutoClearing");

        boolean isFinal = enclosingElement.getModifiers().contains(Modifier.FINAL);
        return new Builder(targetType, bindingClassName, isFinal);
    }

    static class Builder {

        private final TypeName targetTypeName;
        private final ClassName bindingClassName;
        private final boolean isFinal;

        private List<FieldClearing> fieldClearings = new ArrayList<>();

        private ClearingSet parentClearingSet;

        private Builder(TypeName targetTypeName, ClassName bindingClassName, boolean isFinal) {
            this.targetTypeName = targetTypeName;
            this.bindingClassName = bindingClassName;
            this.isFinal = isFinal;
        }

        void addFieldClearing(FieldClearing fieldClearing) {
            fieldClearings.add(fieldClearing);
        }

        void setParent(ClearingSet parentClearingSet) {
            this.parentClearingSet = parentClearingSet;
        }

        ClearingSet build() {
            return new ClearingSet(targetTypeName, bindingClassName, isFinal,
                    fieldClearings, parentClearingSet);
        }
    }
}
